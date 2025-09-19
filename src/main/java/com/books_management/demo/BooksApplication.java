package com.books_management.demo;

import com.books_management.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApplication {

	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
		System.out.println("Hello");
	}

}
