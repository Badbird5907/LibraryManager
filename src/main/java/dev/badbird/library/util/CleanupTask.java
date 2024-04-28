package dev.badbird.library.util;

import dev.badbird.library.Main;

public class CleanupTask extends Thread {
    @Override
    public void run() {
        Main.getInstance().save(); // save data on exit
        System.out.println("Data saved, goodbye!");
    }
}
