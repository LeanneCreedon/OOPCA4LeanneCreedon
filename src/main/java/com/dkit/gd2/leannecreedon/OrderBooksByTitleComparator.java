package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DTO.Book;

import java.util.Comparator;

public class OrderBooksByTitleComparator implements Comparator<Book>
{
    // Compares books title, then their year of publish
    @Override
    public int compare(Book b1, Book b2)
    {
        String s1 = b1.getTitle();
        String s2 = b2.getTitle();
        int titleCompare = s1.compareTo(s2);

        if (titleCompare != 0) {
            return titleCompare;
        }

        Integer int1 = b1.getDatePublished().getYear();
        Integer int2 = b2.getDatePublished().getYear();

        return int1.compareTo(int2);
    }
}
