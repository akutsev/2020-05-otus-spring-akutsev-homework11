package ru.otus.akutsev.books.model.dto;

public class BookDto {

	private String bookName;
	private String authorId;
	private String genreId;

	public BookDto(String bookName, String authorId, String genreId) {
		this.bookName = bookName;
		this.authorId = authorId;
		this.genreId = genreId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
}
