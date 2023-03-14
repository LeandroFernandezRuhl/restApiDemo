package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class DemoApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);

		Laptop laptop1 = new Laptop(null,encoder.encode("admin"),"Lenovo","Ultimate","Ryzen 5",8,240);
		Laptop laptop2 = new Laptop(null,encoder.encode("admin"),"HP","Basic","Intel I7",16,512);
		laptopRepository.save(laptop1);
		laptopRepository.save(laptop2);

		System.out.println("Number of laptops on DB: " + laptopRepository.findAll().size());
		System.out.println("Number of laptops on DB: " + laptopRepository.count());
	}
}
