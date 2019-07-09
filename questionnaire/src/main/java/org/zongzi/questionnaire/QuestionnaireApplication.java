package org.zongzi.questionnaire;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author haopeisong
 */
@EnableSwagger2Doc
@SpringBootApplication
@MapperScan(basePackages = "org.zongzi.questionnaire.mapper")
public class QuestionnaireApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionnaireApplication.class, args);
    }
}
