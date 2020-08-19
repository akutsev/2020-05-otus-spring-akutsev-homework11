package ru.otus.akutsev.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.model.Author;

@Repository
public interface AuthorDao extends ReactiveMongoRepository<Author, String> {
	Mono<Author> save(Mono<Author> author);

	Mono<Author> findById(String id);

	Flux<Author> findAll();

	Mono<Void> deleteById(String id);
}