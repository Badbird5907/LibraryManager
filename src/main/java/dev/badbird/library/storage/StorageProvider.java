package dev.badbird.library.storage;

import dev.badbird.library.object.Book;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StorageProvider {
    void saveBooks(Collection<Book> books, File ser);
    Set<Book> loadBooks(File ser);

    default Set<Book> loadBooks() {
        return loadBooks(new File("books.ser"));
    }

    default void saveBooks(Collection<Book> books) {
        saveBooks(books, new File("books.ser"));
    }
}
