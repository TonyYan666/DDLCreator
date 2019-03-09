package org.tonyyan.plugin.ddlcreator;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import org.tonyyan.plugin.ddlcreator.vo.TableField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddColumnDDL extends CreatorSupport {

    @Override
    public String createDDL(AnActionEvent event, PsiClass psiClass) {

        //获得当前编辑器
        final Editor editor = event.getData(CommonDataKeys.EDITOR);

        String selectedText = editor.getSelectionModel().getSelectedText();

        String[] targetFieldNameArray = selectedText.split("\n");

        if (targetFieldNameArray == null || targetFieldNameArray.length <= 0) {
            return "Target fields undefined from your selection.";
        }

        Set<String> fieldName = new HashSet<>();

        for (String name : targetFieldNameArray) {
            if (name.indexOf(";") < 0) {
                continue;
            }
            String[] fieldElements = name.substring(0, name.indexOf(";")).split(" ");
            int indexCounter = 0;
            for (String fieldElementTxt : fieldElements) {
                if (!fieldElementTxt.trim().isEmpty()) {
                    indexCounter++;
                }
                if (indexCounter == 3) {
                    fieldName.add(fieldElementTxt.trim());
                    break;
                }
            }
        }

        //获得当前类的名称
        String tableName = getTableName(psiClass);
        //获得当前类的字段
        PsiField[] psiFields = psiClass.getFields();

        //遍历分析获得字段内容，包括getter和其字段的 annotation标签
        List<TableField> tableFields = new ArrayList<>();
        for (PsiField psiField : psiFields) {
            //解析
            if (fieldName.contains(psiField.getName())) {
                TableField tableField = getTableField(psiField);
                if (tableField != null) {
                    tableFields.add(tableField);
                }
            }
        }

        //组织SQL
        StringBuffer createTableDDL = new StringBuffer();
        for (int i = 0; i < tableFields.size(); i++) {
            //获得字段的列SQL
            createTableDDL.append(getSqlOfColumnPart(tableName, tableFields.get(i)));
            createTableDDL.append("\n");
        }
        return createTableDDL.toString();
    }


    /**
     * 将tableField转换成部分的创建表字段语句
     * alter table t_order add COLUMN youchi_agent_id bigint(20) COMMENT '接收分销ID'
     *
     * @param tableField
     * @return
     */
    private String getSqlOfColumnPart(String tableName, TableField tableField) {
        StringBuffer sql = new StringBuffer();
        sql.append("ALTER TABLE " + tableName + " ADD COLUMN `" + tableField.getName() + "` " + tableField.getType());
        if (tableField.getLength() != null) {
            sql.append("(" + tableField.getLength() + ")");
        }
        if (!tableField.isPrimayKey() && tableField.isNullable()) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        if (tableField.getDesc() != null && tableField.getDesc().length() > 0) {
            sql.append(" COMMENT '" + tableField.getDesc() + "'");
        }
        sql.append(";");
        return sql.toString();
    }


    /**
     * 分析当前编辑类是否存在@Entity 同时选择相关字段信息 如果不存在则不显示功能按钮
     *
     * @param e
     */
    @Override
    public void update(AnActionEvent e) {
        super.update(e);

        // 获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等对象
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        // 获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等对象
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        if (!editor.getSelectionModel().hasSelection()) {
            e.getPresentation().setEnabled(false);
        }

    }
}
