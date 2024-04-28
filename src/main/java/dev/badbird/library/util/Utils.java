package dev.badbird.library.util;

import dev.badbird.library.object.Book;

import java.util.Comparator;

public class Utils {
    private static final String NUMBERS = "0123456789"; // constant of all nums

    private static int getCheckDigit(String str) {
        boolean state = true; // we need to multiply by 1 and 3 alternating
        long sum = 0; // using long here as input is long and i dont want to cast things around
        for (int i = 0; i < 12; i++) { // go through first 12 chars
            char c = str.charAt(i); // resolve char
            int n = NUMBERS.indexOf(c); // resolve the actual integer
            sum += n * (state ? 1 : 3); // add n * (1 or 3)
            state = !state; // invert state
        }
        long result = 10 - (sum % 10); // subtract 10 from remainder of sum / 10
        return result == 10 ? 0 : (int) result;
    }

    public static boolean isValidISBN(long isbn) {
        String str = String.valueOf(isbn); // convert isbn to string
        if (str.length() < 13) return false; // sanity check to see if isbn is correct length

        int lastDigit = NUMBERS.indexOf(str.charAt(12)); // get last digit
        // System.out.println("Last Digit: " + lastDigit + " | result: " + result + " | mod: " + (sum % 10));
        return getCheckDigit(str) == lastDigit; // check if result equals check digit
    }

    public static long generateRandomISBN() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append((int) (Math.random() * 10));
        }
        sb.append(getCheckDigit(sb.toString()));
        return Long.parseLong(sb.toString());
    }

    public static String truncateString(String str, int maxLen) {
        if (str.length() > maxLen) { // if string is longer than maxLen
            return str.substring(0, maxLen - 3) + "..."; // truncate and add ...
        }
        return str;
    }

    public static String camelCaseToWords(String in) {
        // convert camelCase to words
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i++) { // loop thru each char
            char c = in.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(" ");
            }
            if (i == 0) {
                sb.append(Character.toUpperCase(c)); // capitalize first letter
            } else {
                sb.append(c);
            }
        }
        return sb.toString().trim(); // return string
    }

    public static String capitalizeFirst(String in) {
        return in.substring(0, 1).toUpperCase() + in.substring(1).toLowerCase(); // capitalize first letter and lowercase the rest
    }

    public static String capitalizeFirstDeep(String in) { // capitalize each word
        StringBuilder sb = new StringBuilder();
        for (String s : in.split(" ")) {
            sb.append(capitalizeFirst(s)).append(" ");
        }
        return sb.toString().trim();
    }

    public static Comparator<Book> sortByTitle() {
        return Comparator.comparing(Book::getTitle); // simple comparator to sort books by title (str)
    }
}
