package autocomplete;

import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryRangeSearchTest {

    private static Autocomplete linearAuto;
    private static Autocomplete binaryAuto;

    private static int N = 0;
    private static Term[] terms = null;

    private static final String INPUT_FILENAME = "data/cities.txt";

    /**
     * Creates LinearRangeSearch and BinaryRangeSearch instances based on the data from cities.txt
     * so that they can easily be used in tests.
     */
    @Before
    public void setUp() {
        if (terms != null) {
            return;
        }

        In in = new In(INPUT_FILENAME);
        N = in.readInt();
        terms = new Term[N];
        for (int i = 0; i < N; i += 1) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query, weight);
        }

        linearAuto = new LinearRangeSearch(terms);
        binaryAuto = new BinaryRangeSearch(terms);
    }

    /**
     * Checks that the terms in the expected array are equivalent to the ones in the actual array.
     */
    private void assertTermsEqual(Term[] expected, Term[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Term e = expected[i];
            Term a = actual[i];
            assertEquals(e.query(), a.query());
            assertEquals(e.weight(), a.weight());
        }
    }

    @Test
    public void testSimpleExample() {
        Term[] moreTerms = new Term[] {
            new Term("hello", 0),
            new Term("world", 0),
            new Term("welcome", 0),
            new Term("to", 0),
            new Term("autocomplete", 0),
            new Term("me", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("autocomplete", 0)};
        assertTermsEqual(expected, brs.allMatches("auto"));
    }

    // Write more unit tests below.
    @Test
    public void testBasicMany() {
        Term[] moreTerms = new Term[] {
                new Term("autoc", 0),
                new Term("autoco", 0),
                new Term("au", 0),
                new Term("a", 0),
                new Term("autocomplete", 0),
                new Term("autocompl", 0),
                new Term("zebra", 0),
                new Term("zoo", 0),
                new Term("aaaaa", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[] {
                new Term("autoc", 0),
                new Term("autoco", 0),
                new Term("autocompl", 0),
                new Term("autocomplete", 0)
        };
        assertTermsEqual(expected, brs.allMatches("auto"));
    }
    @Test
    public void testBasicCities() {
        Term[] moreTerms = new Term[] {
                new Term("San whatever", 800),
                new Term("Delhi", 0),
                new Term("Shanghai", 0),
                new Term("Mysore", 0),
                new Term("Seattle", 0),
                new Term("San Francisco", 1000),
                new Term("San Jose", 900),
                new Term("San Palo Alto", 500),
                new Term("Kolkata", 0),
                new Term("Zimbabwe", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[] {
                new Term("San Francisco", 1000),
                new Term("San Jose", 900),
                new Term("San whatever", 800),
                new Term("San Palo Alto", 500),
        };
        assertTermsEqual(expected, brs.allMatches("San"));
    }

    @Test
    public void testRandomPrefix() {
        Term[] moreTerms = new Term[] {
                new Term("San whatever", 800),
                new Term("Delhi", 0),
                new Term("Shanghai", 0),
                new Term("Mysore", 0),
                new Term("Seattle", 0),
                new Term("San Francisco", 1000),
                new Term("San Jose", 900),
                new Term("San Palo Alto", 500),
                new Term("Kolkata", 0),
                new Term("Zimbabwe", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{};
        assertTermsEqual(expected, brs.allMatches("!!!"));
    }
@SuppressWarnings("checkstyle:WhitespaceAfter")
@Test
    public void testAnother() {
        Term[] moreTerms = new Term[] {
                new Term("Fremont, Indiana, United States", 2138),
                new Term("Fremdingen, Germany", 2192),
                new Term("Fremont, North Carolina, United States", 1255),
                new Term("Fremont, New Hampshire, United States", 3738),
                new Term("Fremington, United Kingdom", 4010),
                new Term("Fremont, Michigan, United States", 4081),
                new Term("Fremantle, Western Australia, Australia", 7459)
        };
    BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
    Term[] expected = new Term[]{
            new Term("Fremantle, Western Australia, Australia", 7459),
            new Term("Fremont, Michigan, United States", 4081),
            new Term("Fremington, United Kingdom", 4010),
            new Term("Fremont, New Hampshire, United States", 3738),
            new Term("Fremdingen, Germany", 2192),
            new Term("Fremont, Indiana, United States", 2138),
            new Term("Fremont, North Carolina, United States", 1255)
    };
    assertTermsEqual(expected, brs.allMatches("Frem"));
}
}
