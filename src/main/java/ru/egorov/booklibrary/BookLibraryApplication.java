package ru.egorov.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BookLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookLibraryApplication.class, args);
    }

}
