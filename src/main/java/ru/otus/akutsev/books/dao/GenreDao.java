package ru.otus.akutsev.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.akutsev.books.model.Genre;

@Repository
public interface GenreDao extends ReactiveMongoRepository<Genre, String> {

}
