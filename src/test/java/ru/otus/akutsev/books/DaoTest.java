package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.dao.GenreDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;
import ru.otus.akutsev.books.model.dto.BookDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@DisplayName("Тест всех ДАО")
public class DaoTest {

	@Autowired
	private AuthorDao authorDao;
	@Autowired
	private GenreDao genreDao;
	@Autowired
	private BookDao bookDao;

	@DisplayName("Сохранение и чтение автора")
	@Test
	public void insertAuthor() {
		Mono<Author> authorMono = authorDao.save(new Author("Sergey Esenin"));

		StepVerifier
				.create(authorMono)
				.assertNext(author -> assertNotNull(author.getId()))
				.expectComplete()
				.verify();
	}

	@DisplayName("Сохранение и чтение 2 жанров")
	@Test
	public void readGenre() {
		Mono<Genre> genreMono1 = genreDao.save(new Genre("Psychological"));
		Mono<Genre> genreMono2 = genreDao.save(new Genre("Social drama"));

		StepVerifier
				.create(Flux.just(genreMono1, genreMono2))
				.expectNext(genreMono1)
				.expectNext(genreMono2)
				.expectComplete()
				.verify();
	}

	@DisplayName("Сохранение и чтение книги")
	@Test
	public void deleteBook() {
		Mono<Book> bookMono = bookDao.save(new Book("Idiot", new Author("1", "Fedor Dostoevskiy"),
				new Genre("2", "Drama")));

		StepVerifier
				.create(bookMono)
				.expectNextMatches(book -> book.getAuthor().getName().equals("Fedor Dostoevskiy"))
				.expectComplete()
				.verify();
	}
}
