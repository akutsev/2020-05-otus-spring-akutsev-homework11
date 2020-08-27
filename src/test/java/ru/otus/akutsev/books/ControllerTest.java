package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.controller.BooksPagesController;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.dao.GenreDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import static org.mockito.Mockito.when;

@DisplayName("Тест контроллера")
@WebFluxTest(BooksPagesController.class)
public class ControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookDao bookDao;
	@MockBean
	private AuthorDao authorDao;
	@MockBean
	private GenreDao genreDao;


	@Test
	@DisplayName("get запрос редактирование книги")
	public void editBookTest() {
		Author author = new Author("25", "Fedor Dostoevskiy");
		Genre genre = new Genre("63", "Drama");
		Book book = new Book("Idiot", author, genre);

		when(authorDao.findAll()).thenReturn(Flux.just(author));
		when(genreDao.findAll()).thenReturn(Flux.just(genre));
		when(bookDao.findById("5")).thenReturn(Mono.just(book));

		webTestClient.get()
				.uri("/edit?id=5")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	@DisplayName("post запрос удаление книги")
	public void deleteBookTest() {
		when(bookDao.deleteById("99")).thenReturn(Mono.empty());

		webTestClient.post()
				.uri("/delete?id=99")
				.exchange()
				.expectStatus().is3xxRedirection();
	}
}
