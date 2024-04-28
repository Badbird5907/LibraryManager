package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;

import java.io.File;
import java.util.Scanner;

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
        String fileName = readLine("File", scanner);
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("That file doesn't exist! Please try again, or type \"cancel\" to exit.");
            execute(scanner);
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
