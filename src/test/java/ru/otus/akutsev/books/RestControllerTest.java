package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.akutsev.books.controller.BooksRestController;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@DisplayName("Тест рест контроллера")
@WebFluxTest(BooksRestController.class)
public class RestControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookDao bookDao;

	@DisplayName("вывод всех книг")
	@Test
	public void showAllBooksTest() {
		Book book1 = new Book("Idiot", new Author("1", "Fedor Dostoevskiy"), new Genre("2", "Drama"));
		Book book2 = new Book("Eugeniy Onegin", new Author("55", "Alex Pushkin"), new Genre("23", "Love story"));

		when(bookDao.findAll()).thenReturn(Flux.just(book1, book2));

		webTestClient.get()
				.uri("/api/allBooks")
				.exchange()
				.expectStatus().isOk()
				.expectBody(new ParameterizedTypeReference<List<Book>>() {})
				.value(name -> book1.getName(), equalTo("Idiot"))
				.value(authorName -> book2.getAuthor().getName(), equalTo("Alex Pushkin"));
	}

}
