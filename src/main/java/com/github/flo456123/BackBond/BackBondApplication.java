package com.github.flo456123.BackBond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackBondApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackBondApplication.class, args);
	}

}
