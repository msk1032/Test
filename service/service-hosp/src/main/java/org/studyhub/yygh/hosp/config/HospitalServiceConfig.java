package org.studyhub.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author haoren
 * @create 2022-01-05 15:14
 */
@Configuration
@MapperScan("org.studyhub.yygh.hosp.mapper")
public class HospitalServiceConfig {
}
