package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;
import dev.badbird.library.csv.CsvParser;
import dev.badbird.library.csv.Row;
import dev.badbird.library.csv.RowToBookAdapter;
import dev.badbird.library.object.Book;

import java.io.File;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Set;

public class LoadCommand extends Command {
    @Override
    public String getName() {
        return "Load Books";
    }

    @Override
    public String getDescription() {
        return "Load books from a file";
    }

    @Override
    public void execute(Scanner scanner) {
        // TODO csv import
        String fileName = readLine("File", scanner);
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("That file doesn't exist! Please try again, or type \"cancel\" to exit.");
            execute(scanner);
            return;
        }
        if (file.getName().endsWith(".csv")) {
            System.out.println("CSV file found! Importing...");
            CsvParser csvParser = new CsvParser(file);
            csvParser.readFile();
            try {
                Set<Book> books = RowToBookAdapter.fromRows(csvParser.getRows());
                Main.getInstance().setBooks(books);
                System.out.println("Successfully imported " + books.size() + " books!");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to parse CSV file: " + e.getMessage());
                return;
            }
            return;
        }
        try {
            Main.getInstance().getStorageProvider().loadBooks(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while loading that file!");
            return;
        }
        System.out.println("Successfully loaded books!");
    }
}
