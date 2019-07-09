package org.zongzi.platform.operation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author haopeisong
 */
@SpringBootApplication
@MapperScan(basePackages = "org.zongzi.platform.operation.mapper")
public class PlatformApplication {
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
    }

}
