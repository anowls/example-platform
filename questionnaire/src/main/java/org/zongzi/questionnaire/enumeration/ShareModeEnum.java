package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 */
public enum ShareModeEnum {
    PUBLISH(1, "共享"),
    OWN(0, "私有");
    private Integer key;
    private String value;

    ShareModeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key){
        for (ShareModeEnum shareModeEnum : ShareModeEnum.values()){
            if (shareModeEnum.getKey() == key){
                return shareModeEnum.getValue();
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
