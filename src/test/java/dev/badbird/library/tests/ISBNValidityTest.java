package dev.badbird.library.tests;

import dev.badbird.library.util.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ISBNValidityTest {
    @Test
    public void testIsbn() {
        assertTrue(Utils.isValidISBN(9783161484100L));
        assertFalse(Utils.isValidISBN(9784162484100L));
    }

    @Test
    public void testRand() {
        long randomISBN = Utils.generateRandomISBN();
        System.out.println("Rand: " + randomISBN);
        assertTrue(Utils.isValidISBN(randomISBN));
    }
}
