package com.websystique.springboot.controller;

import com.websystique.springboot.model.Book;
import com.websystique.springboot.model.LibraryBook;
import com.websystique.springboot.service.BookManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class BookManagementController {

    public static final Logger logger = LoggerFactory.getLogger(BookManagementController.class);

    @Autowired
    BookManagementService bookManagementService; //Service which will do all data retrieval/manipulation work

    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public ResponseEntity addBooks(@RequestBody List<Book> books) {
        List<Long> bookId = bookManagementService.addBooks(books);
        System.out.println("Books saved in DB : " + bookId.size());
        return new ResponseEntity(bookId, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity searchBooks(@RequestParam("data") String data, @RequestParam("value") String value) {
        logger.info("data : "+data+"  value : "+value);
        List<LibraryBook> books = bookManagementService.searchBooks(data, value);
        if (books != null && books.size() != 0) {
            return new ResponseEntity(books, HttpStatus.OK);
        } else {
            return new ResponseEntity("No books find with search parameter given", HttpStatus.OK);
        }
    }
}