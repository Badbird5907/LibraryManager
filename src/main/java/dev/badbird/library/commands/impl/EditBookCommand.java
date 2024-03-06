package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;
import dev.badbird.library.object.Book;
import dev.badbird.library.object.Category;
import dev.badbird.library.object.Genre;
import dev.badbird.library.util.Utils;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EditBookCommand extends Command {
    @Override
    public String getName() {
        return "Edit Book";
    }

    @Override
    public String getDescription() {
        return "Edit a book";
    }

    @Override
    public void execute(Scanner scanner) {
        long isbn = readISBN(scanner, false, false);
        Book book = Main.getInstance().findBookByISBN(isbn);
        if (book == null) {
            System.err.println("Could not find a book by that ISBN! Please try again, or type \"cancel\" to exit.");
            execute(scanner);
            return;
        }
        List<String> validFields = List.of("isbn", "title", "author", "publisher", "copyright", "published", "price", "genre", "category");
        String validFieldsStr = "Please enter the field you would like to edit:\n" + String.join(", ", validFields);
        while (true) {
            String field = readLine(validFieldsStr, scanner).toLowerCase();
            switch (field) {
                case "isbn" -> {
                    long newIsbn = readISBN(scanner, true, false);
                    book.setId(newIsbn);
                }
                case "title", "author", "publisher" -> {
                    String str = readLine("Enter " + Utils.capitalizeFirst(field), scanner);
                    if (field.equals("title")) {
                        book.setTitle(str);
                    } else if (field.equals("author")) {
                        book.setAuthor(str);
                    } else {
                        book.setPublisher(str);
                    }
                }
                case "copyright", "published" -> {
                    Date date = readDate("Please enter the " + Utils.capitalizeFirst(field) + " date.", scanner);
                    if (field.equals("copyright")) {
                        book.setCopyright(date);
                    } else {
                        book.setPublished(date);
                    }
                }
                case "price" -> {
                    double price = readDouble("Please enter the price (without $)", scanner);
                    book.setPrice(price);
                }
                case "genre" -> {
                    Genre genre = readEnumValue("Please enter the genre", scanner, Genre.class, false);
                    book.setGenre(genre);
                }
                case "category" -> {
                    Category category = readEnumValue("Please enter the category", scanner, Category.class, false);
                    book.setCategory(category);
                }
                default -> {
                    System.err.println("That is not a valid field! Please try again, or type \"cancel\" to exit.");
                    continue;
                }
            }
            System.out.println("Successfully updated field!");
            boolean again = readBoolean("Do you want to update another field?", scanner);
            if (!again) break;
        }
    }

    @Override
    public boolean enterToContinue() {
        return false;
    }
}
