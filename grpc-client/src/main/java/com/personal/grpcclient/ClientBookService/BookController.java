package com.personal.grpcclient.ClientBookService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public String getBookNameFromClient(){
        return bookService.getBookNameFromGrpc();
    }
}
