package dev.badbird.library;

import dev.badbird.library.commands.Command;
import dev.badbird.library.commands.impl.*;
import dev.badbird.library.commands.impl.search.impl.ListAllBooksCommand;
import dev.badbird.library.commands.impl.search.impl.SearchByAuthorTitleCommand;
import dev.badbird.library.commands.impl.search.impl.SearchByCategoryCommand;
import dev.badbird.library.commands.impl.search.impl.SearchByGenreCommand;
import dev.badbird.library.object.AbortCommandException;
import dev.badbird.library.object.Book;
import dev.badbird.library.storage.StorageProvider;
import dev.badbird.library.storage.impl.FlatFileStorageProvider;
import dev.badbird.library.util.CleanupTask;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final Main instance = new Main();

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance.run();
    }

    private List<Command> commands; // registry of commands
    private Scanner scanner = new Scanner(System.in);
    private StorageProvider storageProvider = new FlatFileStorageProvider();

    private Set<Book> books;

    public Main() {
        // initialize our list of commands
        commands = List.of(
                new ListAllBooksCommand(),
                new SearchByAuthorTitleCommand(),
                new SearchByCategoryCommand(),
                new SearchByGenreCommand(),
                new AddBookCommand(),
                new EditBookCommand(),
                new RemoveBookCommand(),
                new LoadCommand(),
                new SaveCommand(),
                new ExitCommand()
        );
        // load books from storage
        books = storageProvider.loadBooks();
        Runtime.getRuntime().addShutdownHook(new CleanupTask()); // save books on shutdown
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            printHelp();
            execNextCommand();
        }
    }

    public void execNextCommand() { // reads user input and executes command
        System.out.println("Command:");
        String line = scanner.nextLine().trim();
        Command command;
        int i;
        try {
            // read index
            i = Integer.parseInt(line);
            if (i > commands.size()) {
                System.err.println("Invalid Command!");
                execNextCommand();
                return;
            }
            command = commands.get(i-1);
        } catch (NumberFormatException e) {
            // try to find command by name
            command = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(line))
                    .findFirst().orElse(null);
            if (command == null) {
                System.err.println("Invalid Command!");
                execNextCommand();
                return;
            }
        }
        try {
            // execute command
            command.execute(scanner);
            if (command.enterToContinue()) {
                command.readLine("Press enter to continue", scanner);
            }
        } catch (AbortCommandException e) { // catch our custom abort exception
            System.out.println("Aborted command execution!");
        }
    }

    public void printHelp() {
        // prints a table of commands
        String table = "| %-2s | %-25s | %-35s |%n";
        String separator = "------------------------------------------------------------------------%n";
        System.out.printf(separator);
        System.out.printf(table, "ID", "Name", "Description");
        System.out.printf(separator);
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            System.out.printf(table, (i + 1), command.getName(), command.getDescription());
        }
        System.out.printf(separator);
    }

    public Book findBookByISBN(long isbn) {
        return books.stream().filter(book -> book.getId() == isbn).findFirst().orElse(null); // return the first book with the given ISBN or null
    }

    public void save() {
        getStorageProvider().saveBooks(books);
    }
    
    // Getters & Setters

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public StorageProvider getStorageProvider() {
        return storageProvider;
    }

}