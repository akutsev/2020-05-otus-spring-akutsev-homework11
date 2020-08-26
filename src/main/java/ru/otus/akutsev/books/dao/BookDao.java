package ru.otus.akutsev.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.model.Book;

@Repository
public interface BookDao extends ReactiveMongoRepository<Book, Long> {
	Mono<Book> save(Book book);

	Mono<Book> save(Mono<Book> book);

	Mono<Book> findAById(String id);

	Flux<Book> findAll();

	Mono<Void> deleteById(String id);
}
