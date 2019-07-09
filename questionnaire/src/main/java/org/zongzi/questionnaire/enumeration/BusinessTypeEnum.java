package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 * 注意：
 * 不用于
 * @see ResearchBusiness
 * 中的businessType字段
 */
public enum BusinessTypeEnum {
    QUESTIONNAIRE(0, "问卷调查"),
    TRAINING(1, "培训学习");
    private Integer key;
    private String value;

    BusinessTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
