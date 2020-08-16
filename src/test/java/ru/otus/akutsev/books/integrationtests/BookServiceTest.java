package ru.otus.akutsev.books.integrationtests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;
import ru.otus.akutsev.books.service.BookService;
import ru.otus.akutsev.books.datapreparation.PrepareData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Тест приложения с поднятием контекста")
@SpringBootTest
public class BookServiceTest {

	@Autowired
	private BookService bookService;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private PrepareData prepareData;

	@BeforeEach
	private void prepareData() {
		prepareData.fillData(mongoOperations);
	}

	@DisplayName("на примере добавления+извлечения книги")
	@Test
	public void addBookGetByIdTest() {
		String authorName = "Lev Tolstoy";
		Author author = new Author();
		author.setName(authorName);

		String genreName = "Drama";
		Genre genre = new Genre();
		genre.setGenreName(genreName);

		String bookName = "Anna Karenina";
		Book book = new Book();
		book.setName(bookName);
		book.setAuthor(author);
		book.setGenre(genre);

		assertFalse(bookService.getAll().contains(book));

		String id = bookService.save(book).getId();

		Book bookFromDb = bookService.getAById(id).get();
		assertEquals(bookName, bookFromDb.getName());
		assertEquals(author.getName(), bookFromDb.getAuthor().getName());
		assertEquals(genre.getGenreName(), bookFromDb.getGenre().getGenreName());
	}

	@DisplayName("Изменение полей книги")
	@Test
	public void updateBookTest() {
		String id = "1";
		Book book = bookService.getAById(id).get();

		String authorName = "Fedor Dostoevskiy";
		Author newAuthor = new Author();
		newAuthor.setName(authorName);


		String genreName = "Psychological criminal drama";
		Genre newGenre = new Genre();
		newGenre.setGenreName(genreName);

		String bookName = "Crime and punishment";

		bookService.updateBook(book, bookName, newAuthor, newGenre);

		assertEquals(bookName, book.getName());
		assertEquals(newAuthor, book.getAuthor());
		assertEquals(newGenre, book.getGenre());
	}

	@AfterEach
	private void clearData() {
		prepareData.clearData(mongoOperations);
	}
}
