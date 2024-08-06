package com.MultipleDataSourceConnection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultipleDataSourceConnectionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDataSourceConnectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		int i=127;
//		while(i<200) {
//		System.err.println((char)i+" i="+i);
//		}
		
	}

}
