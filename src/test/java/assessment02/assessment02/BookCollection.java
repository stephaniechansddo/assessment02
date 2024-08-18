package assessment02.assessment02;

import java.util.*;
import java.util.ArrayList;

public class BookCollection {

    private ArrayList<Book> books = new ArrayList<>();
    private int capacity;

    public BookCollection() {
        books.add(new Book("0001", "The Player Next Door", "BabyInACorner"));
        books.add(new Book("0002", "After", "imaginator1D"));
        books.add(new Book("0003", "The Bad Boy, Cupid & Me", "Slim_Shady"));
        books.add(new Book("0004", "Eyes Open", "HonorInTheRain"));
        books.add(new Book("0005", "Face Your Fears", "HonorInTheRain"));
        books.add(new Book("0006", "Play That Part", "BabyInACorner"));
        books.add(new Book("0007", "68 Days And Counting", "Nicole Nwosu"));
        books.add(new Book("0008", "The Bad Boy And The Tom Boy", "Nicole Nwosu"));
        books.add(new Book("0009", "Play No More", "BabyInACorner"));
        books.add(new Book("0010", "In 27 Days", "HonorInTheRain"));
        books.add(new Book("0011", "Something Inside", "OutOfMyLimit17"));

        this.capacity = 20;
    }

    public BookCollection(int capacity) {
        this.capacity = capacity;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        if (books.size() < capacity) {
            books.add(book);
        }
    }

    public ArrayList<Book> sortBooksByTitle() {
        Collections.sort(books, Book.titleComparator);
        return books;
    }

    public ArrayList<Book> sortBooksByAuthor() {
        Collections.sort(books, Book.authorComparator);
        return books;
    }

    public Book findBookById(String id) {
        for (Book b : books) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equals(title)) return b;
        }
        return null;
    }
}
