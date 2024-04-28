package dev.badbird.library.commands.impl;

import dev.badbird.library.Main;
import dev.badbird.library.commands.Command;

import java.io.File;
import java.util.Scanner;

public class SaveCommand extends Command {
    @Override
    public String getName() {
        return "Save";
    }

    @Override
    public String getDescription() {
        return "Save books to a .ser file";
    }

    @Override
    public void execute(Scanner scanner) {
        // read file name
        String fileName = readLine("File", scanner);
        File file = new File(fileName);
        if (file.exists()) { // check if file exists
            boolean overwrite = readBoolean("File already exists. Overwrite?", scanner);
            if (!overwrite) {
                return;
            }
        }
        // save books to file
        Main.getInstance().getStorageProvider().saveBooks(
                Main.getInstance().getBooks(),
                file
        );
        System.out.println("Successfully saved books to file!");
    }
}
