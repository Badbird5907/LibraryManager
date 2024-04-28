package dev.badbird.library.commands.impl.search.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.impl.search.SearchCommand;
import dev.badbird.library.object.Book;
import dev.badbird.library.object.Genre;
import dev.badbird.library.util.Utils;

import java.util.List;
import java.util.Scanner;

public class SearchByGenreCommand extends SearchCommand {
    @Override
    public String getName() {
        return "Search By Genre";
    }

    @Override
    public String getDescription() {
        return "Search books by genre";
    }

    @Override
    public List<Book> filterAndSort(Scanner scanner) {
        // read genre from user input
        Genre genre = readEnumValue("Enter genre", scanner, Genre.class, false);
        // returns books where genre matches, sorted by title
        return Main.getInstance().getBooks().stream().filter(book -> book.getGenre() == genre)
                .sorted(Utils.sortByTitle())
                .toList();
    }
}
