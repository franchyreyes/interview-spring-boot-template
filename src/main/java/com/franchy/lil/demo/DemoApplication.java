package com.franchy.lil.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@GetMapping("/")
//	public GreetResponse getHello(){
//		return new GreetResponse(
//				"Franchy",
//				List.of("Java","Spring"),
//				new Person(10, "Franchy the best")
//		);
//	}
//

//	record Person(int id, String name){}
//	record  GreetResponse(
//			String greet,
//			List<String> favLanguages,
//			Person person
//	){}
}
