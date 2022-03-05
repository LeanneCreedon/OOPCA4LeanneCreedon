package com.dkit.gd2.leannecreedon;

import java.time.LocalDate;

public class Book
{
    /* Attributes */
    private final int id;   // Will be auto increment when database system is set up
    private final String title;
    private final String author;
    private final String status;
    private final String genre;
    private final LocalDate datePublished;
    private final String publisher;
    private final double rating;

    /* Constructors */

    public Book(int id, String title, String author, String status, String genre, int year,
                int month, int day, String publisher, double rating)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.genre = genre;
        this.datePublished = LocalDate.of(year, month, day);
        this.publisher = publisher;
        this.rating = rating;
    }

    /* Getters */

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getRating() {
        return rating;
    }

    /* ToString */

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", genre='" + genre + '\'' +
                ", datePublished=" + datePublished +
                ", publisher='" + publisher + '\'' +
                ", rating=" + rating +
                '}';
    }
}
