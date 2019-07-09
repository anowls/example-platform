package org.zongzi.questionnaire.enumeration;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 */
public enum QuestionnaireTypeEnum{
    COURSE_USE(0, "课程"),
    CLASS_USE(1, "班级"),
    OTHER_USE(2, "其他");

    private Integer name;
    private String value;

    QuestionnaireTypeEnum(Integer name, String value) {
        this.name = name;
        this.value = value;
    }

    public Integer getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
