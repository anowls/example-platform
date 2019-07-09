package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 */
public enum JsUndefinedEnum {
    UNDEFINED("undefined");

    private String value;

    JsUndefinedEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
