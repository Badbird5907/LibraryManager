package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;
import dev.badbird.library.object.Book;
import dev.badbird.library.object.Category;
import dev.badbird.library.object.Genre;
import dev.badbird.library.util.Utils;

import java.util.Date;
import java.util.Scanner;

public class AddBookCommand extends Command {
    @Override
    public String getName() {
        return "Add Book";
    }

    @Override
    public String getDescription() {
        return "Add a new book";
    }

    @Override
    public void execute(Scanner scanner) {
        long id = readISBN(scanner, true, true);
        String title = readLine("Title", scanner, true);
        String author = readLine("Author", scanner, true);
        String publisher = readLine("Publisher", scanner, true);
        Date copyright = readDate("Copyright", scanner);
        Date published = readDate("Published", scanner);
        double price = readDouble("Price", scanner);
        Genre genre = readEnumValue("Genre", scanner, Genre.class, false);
        Category category = readEnumValue("Category", scanner, Category.class, false);

        Book book = new Book(id, title, author, publisher, copyright, published, price, genre, category);
        Main.getInstance().getBooks().add(book);
        Main.getInstance().save();
        System.out.println("Successfully added book!");
    }

    @Override
    public boolean enterToContinue() {
        return false;
    }
}
