package dev.badbird.library.commands.impl;

import dev.badbird.library.commands.Command;

import java.util.Scanner;

public class ExitCommand extends Command {
    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "Quit the program";
    }

    @Override
    public void execute(Scanner scanner) {
        System.exit(0);
    }
}
