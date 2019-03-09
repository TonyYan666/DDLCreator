package org.tonyyan.plugin.ddlcreator.vo;

public class TableField {

    private boolean isPrimayKey;

    private boolean isGeneratedValue;

    private String name;

    private String type;

    private Integer length;

    private boolean nullable = true;

    private String desc;

    private boolean hasTypeTranslate = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        if (type == null) {
            return "unknown";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        if (length == null) {
            if ("varchar".equals(this.getType())) {
                return 255;
            } else if ("int".equals(this.getType())) {
                return 8;
            } else if ("bigint".equals(this.getType())) {
                return 20;
            }
            return null;
        }
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimayKey() {
        return isPrimayKey;
    }

    public void setPrimayKey(boolean primayKey) {
        isPrimayKey = primayKey;
    }

    public boolean isGeneratedValue() {
        return isGeneratedValue;
    }

    public void setGeneratedValue(boolean generatedValue) {
        isGeneratedValue = generatedValue;
    }

    public boolean isHasTypeTranslate() {
        return hasTypeTranslate;
    }

    public void setHasTypeTranslate(boolean hasTypeTranslate) {
        this.hasTypeTranslate = hasTypeTranslate;
    }
}
