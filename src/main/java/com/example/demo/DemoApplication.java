package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null,"Lenovo","Ultimate","Ryzen 5",8,240);
		Laptop laptop2 = new Laptop(null,"HP","Basic","Intel I7",16,512);
		laptopRepository.save(laptop1);
		laptopRepository.save(laptop2);

		System.out.println("Number of laptops on DB: " + laptopRepository.findAll().size());
		System.out.println("Number of laptops on DB: " + laptopRepository.count());
	}
}
