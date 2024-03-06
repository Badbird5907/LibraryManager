package dev.badbird.library.util;

import dev.badbird.library.Main;

public class CleanupTask extends Thread {
    @Override
    public void run() {
        Main.getInstance().save();
        System.out.println("Data saved, goodbye!");
    }
}
