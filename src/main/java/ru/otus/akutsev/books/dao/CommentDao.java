package ru.otus.akutsev.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.akutsev.books.model.Comment;

@Repository
public interface CommentDao extends ReactiveMongoRepository<Comment, Long> {
	Mono<Comment> save(Mono<Comment> comment);

	Mono<Comment> findById(String id);

	Mono<Void> deleteById(String id);
}
