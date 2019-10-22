package autocomplete;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new Term("autocomplete", 0);
        Term b = new Term("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
    }
    // Write more unit tests below.
    @Test
    public void testCheckCompareTo() {
        Term a = new Term("Fremont, Indiana, United States", 2138);
        Term b = new Term("Fremantle, Western Australia, Australia", 7459);
        String prefix = "Frem";
        assertTrue(a.compareToByPrefixOrder(b, prefix.length()) == 0); //a = b

        Term c = new Term("Fremont, Indiana, United States", 2138);
        Term d = new Term("Seattle, Western Australia, Australia", 7459);
        String prefix2 = "Frem";
        assertTrue(c.compareToByPrefixOrder(d, prefix2.length()) < 0); //a > b
    }
}
