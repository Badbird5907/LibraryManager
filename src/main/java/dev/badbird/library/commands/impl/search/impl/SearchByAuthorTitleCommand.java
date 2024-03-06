package dev.badbird.library.commands.impl.search.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.impl.search.SearchCommand;
import dev.badbird.library.object.Book;
import dev.badbird.library.util.SearchUtils;
import dev.badbird.library.util.Utils;

import java.util.List;
import java.util.Scanner;

public class SearchByAuthorTitleCommand extends SearchCommand {
    @Override
    public String getName() {
        return "Author / Title Search";
    }

    @Override
    public String getDescription() {
        return "Find a book by title or author";
    }

    @Override
    public List<Book> filterAndSort(Scanner scanner) {
        String author = readLine("Author (optional)", scanner);
        String title = readLine("Title (optional)", scanner);
        if (author.isEmpty() && title.isEmpty()) {
            System.err.println("You must supply either an author or title! Please try again, or enter \"cancel\" to cancel.");
            return filterAndSort(scanner);
        }
        return Main.getInstance().getBooks().stream().filter(book -> {
            boolean authorMatches = author.isEmpty() || SearchUtils.similar(book.getAuthor(), author);
            boolean titleMatches = title.isEmpty() || SearchUtils.similar(book.getTitle(), title);
            return authorMatches && titleMatches;
        }).sorted(Utils.sortByTitle()).toList();
    }
}
