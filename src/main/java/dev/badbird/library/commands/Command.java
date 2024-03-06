package dev.badbird.library.commands;

import dev.badbird.library.Main;
import dev.badbird.library.object.AbortCommandException;
import dev.badbird.library.util.SearchUtils;
import dev.badbird.library.util.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Function;

public abstract class Command {
    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute(Scanner scanner);

    public boolean enterToContinue() {
        return true;
    }

    // prob could have abstracted this to a Provider like all my other command frameworks
    // but whatever
    public String readLine(String prompt, Scanner scanner, boolean... disableCancel0) {
        boolean disableCancel = disableCancel0.length > 0 && disableCancel0[0];
        System.out.println(prompt + ":");
        String s = scanner.nextLine();
        if (!disableCancel && s.equalsIgnoreCase("cancel")) {
            throw new AbortCommandException();
        }
        return s.trim();
    }

    public boolean readBoolean(String prompt, Scanner scanner) {
        String s = readLine(prompt + " (Yes/No)", scanner);
        if (s.equalsIgnoreCase("yes")) return true;
        if (s.equalsIgnoreCase("no")) return false;
        try {
            return Boolean.parseBoolean(s);
        } catch (Exception ignored) {}
        System.err.println("Failed to parse boolean \"" + s + "\". Please enter Yes/No, or type \"cancel\" to exit.");
        return readBoolean(prompt, scanner);
    }

    public long readLong(String prompt, Scanner scanner, boolean... enableRandomIsbn) {
        boolean enableRand = enableRandomIsbn.length > 0 && enableRandomIsbn[0];

        String s = readLine(prompt, scanner);
        if (enableRand && s.equalsIgnoreCase("random_isbn")) {
            return Utils.generateRandomISBN();
        }
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            System.err.println("Error parsing number. Please try again. Type \"cancel\" to exit.");
            return readLong(prompt, scanner);
        }
    }
    public int readInt(String prompt, Scanner scanner) {
        String s = readLine(prompt, scanner);
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            System.err.println("Error parsing number. Please try again. Type \"cancel\" to exit.");
            return readInt(prompt, scanner);
        }
    }

    public double readDouble(String prompt, Scanner scanner) {
        String s = readLine(prompt, scanner);
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            System.err.println("Error parsing number. Please try again. Type \"cancel\" to exit.");
            return readDouble(prompt, scanner);
        }
    }

    public long readLong(String prompt, Scanner scanner, Function<Long, Boolean> validator, boolean... randIsbn) {
        while (true) {
            long l = readLong(prompt, scanner, randIsbn);
            if (validator.apply(l)) {
                return l;
            }
            System.out.println(); // newline first
        }
    }

    public long readISBN(Scanner scanner, boolean checkDupe, boolean allowRandomIsbn) {
        return readLong("Book ISBN" + (allowRandomIsbn ? " (use random_isbn to generate a random isbn)" : ""), scanner, (isbn) -> {
            if (!Utils.isValidISBN(isbn)) {
                System.err.println("Invalid ISBN! Please try again, or type \"cancel\" to exit.");
                return false;
            }
            if (Main.getInstance().findBookByISBN(isbn) != null && checkDupe) {
                System.err.println("A book with that ISBN already exists! Please try again, or type \"cancel\" to exit.");
                return false;
            }
            return true;
        }, allowRandomIsbn);
    }

    public Date readDate(String prompt, Scanner scanner) {
        String dateStr = readLine(prompt + " (dd/mm/yyyy)", scanner);
        try {
            return Main.DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage() + ". Please try again. Type \"cancel\" to exit.");
            return readDate(prompt, scanner);
        }
    }

    public <T extends Enum<T>> T readEnumValue(String prompt, Scanner scanner, Class<T> enumClazz, boolean allowPartialMatch) {
        String str = readLine(prompt, scanner);
        SearchUtils.EnumSearchResult<T> result = SearchUtils.findEnumByName(str, enumClazz);
        if (result == null || (!allowPartialMatch && !result.exactMatch())) {
            System.err.println("Invalid value! Please try again. Type \"cancel\" to exit.");
            if (result != null && result.value() != null) {
                System.err.println("Found possible match: " + result.value());
            }
            return readEnumValue(prompt, scanner, enumClazz, allowPartialMatch);
        }
        return result.value();
    }
}
