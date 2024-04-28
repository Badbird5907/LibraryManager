package dev.badbird.library.commands.impl.search.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.impl.search.SearchCommand;
import dev.badbird.library.object.Book;
import dev.badbird.library.util.Utils;

import java.util.List;
import java.util.Scanner;

public class ListAllBooksCommand extends SearchCommand {
    @Override
    public String getName() {
        return "List all books";
   }

    @Override
    public String getDescription() {
        return "Show all books";
    }

    @Override
    public List<Book> filterAndSort(Scanner scanner) {
        // sort by title
        return Main.getInstance().getBooks().stream().sorted(Utils.sortByTitle())
                .toList();
    }
}
