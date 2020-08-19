package ru.otus.akutsev.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.model.Genre;

@Repository
public interface GenreDao extends ReactiveMongoRepository<Genre, String> {
	Mono<Genre> save(Mono<Genre> genre);

	Mono<Genre> findById(String id);

	Mono<Void> deleteById(String id);
}
