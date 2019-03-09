package org.tonyyan.plugin.ddlcreator.utils;

import org.tonyyan.plugin.ddlcreator.vo.TableField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TypeTranslator {

    public static Set<String> charTypeList;
    public static Set<String> intTypeList;
    public static Set<String> doubleTypeList;
    public static Set<String> bigIntTypeList;
    public static Set<String> dateTypeList;

    public static void initCharTypeList() {
        charTypeList = new HashSet<>();
        charTypeList.add("java.lang.String");
    }

    public static void initDoubleTypeList() {
        doubleTypeList = new HashSet<>();
        doubleTypeList.add("double");
        doubleTypeList.add("float");
        doubleTypeList.add("java.lang.Double");
        doubleTypeList.add("java.lang.Float");
        doubleTypeList.add("java.math.BigDecimal");
    }

    public static void initIntTypeList() {
        intTypeList = new HashSet<>();
        intTypeList.add("int");
        intTypeList.add("short");
        intTypeList.add("boolean");
        intTypeList.add("java.lang.Integer");
        intTypeList.add("java.lang.Boolean");
        intTypeList.add("java.lang.Short");
    }

    public static void initBigIntList() {
        bigIntTypeList = new HashSet<>();
        bigIntTypeList.add("long");
        bigIntTypeList.add("java.lang.Long");
    }

    public static void initDateTypeList() {
        dateTypeList = new HashSet<>();
        dateTypeList.add("java.util.Date");
        dateTypeList.add("java.sql.Timestamp");
        dateTypeList.add("java.sql.Date");
    }

    public static String sqlTypeTranslate(String javaType) {
        if (bigIntTypeList == null) {
            initBigIntList();
        }
        if (bigIntTypeList.contains(javaType)) {
            return "bigint";
        }
        if (charTypeList == null) {
            initCharTypeList();
        }
        if (charTypeList.contains(javaType)) {
            return "varchar";
        }
        if (intTypeList == null) {
            initIntTypeList();
        }
        if (intTypeList.contains(javaType)) {
            return "int";
        }
        if (doubleTypeList == null) {
            initDoubleTypeList();
        }
        if (doubleTypeList.contains(javaType)) {
            return "double";
        }
        if (dateTypeList == null) {
            initDateTypeList();
        }
        if (dateTypeList.contains(javaType)) {
            return "datetime";
        }
        return null;
    }


}
