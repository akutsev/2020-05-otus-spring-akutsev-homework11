package ru.otus.akutsev.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.dao.GenreDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;
import ru.otus.akutsev.books.model.dto.BookDto;

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

	@PostMapping("/save")
	public Mono<String> saveNewBook(@ModelAttribute BookDto bookDto) {
		Mono<Author> newAuthorMono = authorDao.findById(bookDto.getAuthorId());
		Mono<Genre> newGenreMono = genreDao.findById(bookDto.getGenreId());

		Book book = new Book();

		Mono<Book> newBookMono = Mono.zip(newAuthorMono, newGenreMono)
				.flatMap(data -> {
					book.setAuthor(data.getT1());
					book.setGenre(data.getT2());
					book.setName(bookDto.getBookName());
					return Mono.just(book);
				});

		newBookMono.flatMap(bookDao::save).subscribe();

		return Mono.just("redirect:/");
	}

	@PostMapping("/edit")
	public Mono<String> saveEditedBook(@ModelAttribute BookDto bookDto, @RequestParam("id") String id) {
		Mono<Author> newAuthorMono = authorDao.findById(bookDto.getAuthorId());
		Mono<Genre> newGenreMono = genreDao.findById(bookDto.getGenreId());
		Mono<Book> oldBookMono = bookDao.findAById(id).switchIfEmpty(Mono.error(new NoSuchBookException()));

		Mono<Book> newBookMono = Mono.zip(oldBookMono, newAuthorMono, newGenreMono)
				.flatMap(data -> {
					data.getT1().setAuthor(data.getT2());
					data.getT1().setGenre(data.getT3());
					data.getT1().setName(bookDto.getBookName());
					return Mono.just(data.getT1());
				});

		newBookMono.flatMap(bookDao::save).subscribe();

		return Mono.just("redirect:/");
	}

	@PostMapping("/delete")
	public Mono<String> deleteBook(@RequestParam("id") String id) {
		bookDao.deleteById(id).subscribe();

		return Mono.just("redirect:/");
	}

}
