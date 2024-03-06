package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;

import java.util.Scanner;

public class RemoveBookCommand extends Command {
    @Override
    public String getName() {
        return "Remove Book";
    }

    @Override
    public String getDescription() {
        return "Remove a book by ISBN";
    }

    @Override
    public void execute(Scanner scanner) {
        long id = readISBN(scanner, false, false);
        if (Main.getInstance().getBooks().removeIf(
                book -> book.getId() == id
        )) {
            System.out.println("Removed the book!");
        } else {
            System.err.println("Could not find a book with that ISBN! Please try again, or type \"cancel\" to exit.");
            execute(scanner);
        }
    }
}
