package com.dkit.gd2.leannecreedon;

import java.util.Comparator;

public class OrderBooksByTitleComparator implements Comparator<Book>
{
    // Compares books for printing in alphabetical order
    @Override
    public int compare(Book b1, Book b2)
    {
        return b1.getTitle().compareTo(b2.getTitle());
    }
}
