package com.crud.dula;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author crud
 *
 */
@MapperScan("com.crud.**.mapper")
@SpringBootApplication
public class DulaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DulaApplication.class, args);
	}

}
