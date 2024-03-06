package dev.badbird.library.commands.impl.search;

import dev.badbird.library.commands.Command;
import dev.badbird.library.object.Book;
import dev.badbird.library.util.Utils;

import java.util.List;
import java.util.Scanner;

public abstract class SearchCommand extends Command {
    @Override
    public void execute(Scanner scanner) {
        List<Book> list = filterAndSort(scanner);
        if (list.isEmpty()) {
            System.out.println("Search returned no results.");
            return;
        }
        if (list.size() > 1) {
            System.out.println("Found " + list.size() + " results.");

            String seperator = "-".repeat(141) + "%n";
            String table = "| %-2s | %-30s | %-30s | %-30s | %-15s | %-15s |%n";
            System.out.printf(seperator);
            System.out.printf(table, "#", "Name", "Author", "Description", "Genre", "Category");
            System.out.printf(seperator);
            for (int i = 0; i < list.size(); i++) {
                Book book = list.get(i);
                System.out.printf(
                        table,
                        (i + 1),
                        Utils.truncateString(book.getTitle(), 30),
                        Utils.truncateString(book.getAuthor(), 30),
                        Utils.truncateString(book.getPublisher(), 30),
                        book.getGenre().toString(),
                        book.getCategory().toString()
                );
            }
            System.out.printf(seperator);
            System.out.println("Enter a book # to get it's full info.");
            //noinspection InfiniteLoopStatement
            while (true) {
                long num = readLong("Book #/isbn (cancel to exit)", scanner);
                if (Utils.isValidISBN(num)) {
                    list.stream().filter(book -> book.getId() == num)
                            .findFirst().ifPresentOrElse(Book::printInfo,
                                    () -> System.err.println("Invalid book! Please try again, or enter \"cancel\" to exit.")
                            );
                    return;
                }
                if (num > list.size()) {
                    System.err.println("Invalid book! Please try again, or enter \"cancel\" to exit.");
                    continue;
                }
                list.get((int) (num - 1)).printInfo();
            }
        } else {
            System.out.println("Found 1 result.");
            list.get(0).printInfo();
        }
    }

    public abstract List<Book> filterAndSort(Scanner scanner);
}
