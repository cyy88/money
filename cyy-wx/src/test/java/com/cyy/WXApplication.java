package com.cyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//这里不会用到数据源，所以需要排除掉，不然启动会报错
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WXApplication {
    public static void main(String[] args) {
        SpringApplication.run(WXApplication.class);
    }
}
