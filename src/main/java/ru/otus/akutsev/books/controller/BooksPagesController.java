package ru.otus.akutsev.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.server.ServerWebExchange;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.dao.GenreDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.Map;
import java.util.Objects;

@Controller
public class BooksPagesController {

	private final BookDao bookDao;
	private final AuthorDao authorDao;
	private final GenreDao genreDao;

	@Autowired
	public BooksPagesController(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
		this.bookDao = bookDao;
		this.authorDao = authorDao;
		this.genreDao = genreDao;
	}

	@GetMapping("/")
	public String listPage(Model model) {
		return "allBooks";
	}

	@GetMapping("/save")
	public String saveEditedBook(Model model) {
		Flux<Author> authors = authorDao.findAll();
		Flux<Genre> genres = genreDao.findAll();

//		IReactiveDataDriverContextVariable reactiveAuthors =
//				new ReactiveDataDriverContextVariable(authorDao.findAll(), 5);
//		IReactiveDataDriverContextVariable reactiveGenres =
//				new ReactiveDataDriverContextVariable(genreDao.findAll(), 5);
//
    	model.addAttribute("authors", authors);
		model.addAttribute("genres", genres);

		return "save";
	}

	@GetMapping("/edit")
	public String editPage(@RequestParam("id") String id, Model model) {
		Mono<Book> book = bookDao.findAById(id).onErrorResume(e -> Mono.error(new NoSuchBookException()));

		model.addAttribute("book", book);
		model.addAttribute("authors", authorDao.findAll());
		model.addAttribute("genres", genreDao.findAll());

		return "edit";
	}

	@PostMapping("/edit")
	public String saveEditedBook(@ModelAttribute(name = "book") Mono<Book> book) {
		bookDao.save(book);

		return "redirect:/";
	}

	@PostMapping("/delete")
	public String deleteBook(String id) {
		bookDao.deleteById(id).then();

		return "redirect:/";
	}

	@PostMapping("/save")
	public Mono<String> saveStudent(@ModelAttribute(name = "book") Mono<Book> book) {
		return bookDao.save(book).then(Mono.just("redirect:/"));
	}

/*	public String  saveNewBook(@ModelAttribute(name = "book") Book book) {
		bookDao.save(book);

		return "redirect:/";
	}*/

}
