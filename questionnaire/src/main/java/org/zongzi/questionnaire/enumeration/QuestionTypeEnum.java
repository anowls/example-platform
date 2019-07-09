package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 */
public enum QuestionTypeEnum {
    DAN_XUAN(0, "单选题"),
    DUO_XUAN(1, "多选题"),
    LIANG_BIAO(2, "量表题"),
    PING_FEN(3, "评分题"),
    WEN_DA(4, "问答题"),
    TIAN_KONG(5, "问答题");

    private Integer key;
    private String value;

    QuestionTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(int key) {
        for (QuestionTypeEnum questionTypeEnum : QuestionTypeEnum.values()) {
            if (questionTypeEnum.getKey() == key) {
                return questionTypeEnum.value;
            }
        }
        return null;
    }
}
