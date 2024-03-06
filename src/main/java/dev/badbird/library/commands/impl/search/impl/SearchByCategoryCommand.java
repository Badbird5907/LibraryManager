package dev.badbird.library.commands.impl.search.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.impl.search.SearchCommand;
import dev.badbird.library.object.Book;
import dev.badbird.library.object.Category;
import dev.badbird.library.object.Genre;
import dev.badbird.library.util.Utils;

import java.util.List;
import java.util.Scanner;

public class SearchByCategoryCommand extends SearchCommand {
    @Override
    public String getName() {
        return "Search By Category";
    }

    @Override
    public String getDescription() {
        return "Search books by category";
    }

    @Override
    public List<Book> filterAndSort(Scanner scanner) {
        Category category = readEnumValue("Enter category", scanner, Category.class, false);
        return Main.getInstance().getBooks().stream().filter(book -> book.getCategory() == category)
                .sorted(Utils.sortByTitle())
                .toList();
    }
}
