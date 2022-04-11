package com.chertov.regionshandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RegionsHandbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegionsHandbookApplication.class, args);
	}

}
