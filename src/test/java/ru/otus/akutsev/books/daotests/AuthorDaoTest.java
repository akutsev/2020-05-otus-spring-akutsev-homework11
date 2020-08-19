/*
package ru.otus.akutsev.books.daotests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.model.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест ДАО работы с авторами")
@DataMongoTest
public class AuthorDaoTest {

	@Autowired
	private AuthorDao authorDao;

	@Autowired
	private MongoOperations mongoOperations;

	@DisplayName("вставка нового автора и получение его по ИД")
	@Test
	public void addAuthorGetByIdTest() {
		String name = "Aleksandr Pushkin";
		Author author = new Author();
		author.setName(name);

		String id = authorDao.save(author).getId();

		Author authorFromDb = authorDao.findById(id).get();

		assertEquals(name, authorFromDb.getName());
	}

	@DisplayName("удаление автора")
	@Test
	public void deleteAuthorTest() {
		mongoOperations.save(new Author("1", "Lev Tolstoy"));
		mongoOperations.save(new Author("3", "Mark Twen"));
		mongoOperations.save(new Author("5", "Daria Dontsova"));

		String id = "5";

		assertNotEquals(Optional.empty(), authorDao.findById(id));
		authorDao.deleteById(id);
		assertEquals(Optional.empty(), authorDao.findById(id));
	}

}
*/
