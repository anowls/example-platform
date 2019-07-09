package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 */

public enum AutonymAnonymEnum {
    AUTONYM(0, "实名"),
    ANONYM(1, "匿名");
    private Integer key;
    private String value;

    AutonymAnonymEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key){
        for (AutonymAnonymEnum autonymAnonymEnum : AutonymAnonymEnum.values()){
            if (autonymAnonymEnum.getKey() == key){
                return autonymAnonymEnum.getValue();
            }
        }
        return null;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
