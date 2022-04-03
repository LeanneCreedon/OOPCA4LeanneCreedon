package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DTO.Book;

import java.util.Comparator;

public class PublishDateComparator implements Comparator<Book>
{
    // Comparator to compare two publish dates for findBookUsingFilter() method
    @Override
    public int compare(Book b1, Book b2)
    {
        return b1.getDatePublished().compareTo(b2.getDatePublished());
    }
}
