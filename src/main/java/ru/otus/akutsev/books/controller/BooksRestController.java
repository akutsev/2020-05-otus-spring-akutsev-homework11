package ru.otus.akutsev.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.model.Book;

@RestController
public class BooksRestController {

	private final BookDao bookDao;

	@Autowired
	public BooksRestController(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@GetMapping("/api/allBooks")
	public Flux<Book> getAllBooks() {
		return bookDao.findAll();
	}

}
