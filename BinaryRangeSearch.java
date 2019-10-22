package autocomplete;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Terms is null");
        }
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("Terms contains null");
            }
        }
        Arrays.sort(terms);
        this.terms = terms;
}


    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Term prefixTerm = new Term(prefix, 0);
        int prefixLen = prefix.length();
        // find lowest index with prefix
        int lower = lowerBound(prefixTerm, 0, terms.length - 1);
        // find highest index with prefix
        int upper = upperBound(prefixTerm, 0, terms.length - 1);
        // Goes through those bounds and adds it to arraylist
        // change to array directly
        ArrayList<Term> matchingTerms = new ArrayList<Term>();
        if (lower != upper) {
            for (int i = lower; i <= upper; i++) {
                matchingTerms.add(terms[i]);
            }
        } else if (terms[lower].compareToByPrefixOrder(prefixTerm, prefixLen) == 0) {
                matchingTerms.add(terms[lower]);
        }
        //Arrays.sort(matches, Term::compareToByReverseWeightOrder);
        matchingTerms.sort(TermComparators.byReverseWeightOrder());
        Term[] matchingTermsArray = matchingTerms.toArray(new Term[matchingTerms.size()]);
        return matchingTermsArray;
    }

   private int lowerBound(Term prefix, int low, int high) {
       int prefixLen = prefix.query().length();
        if (high - low == 1) {
            if (ifHasPrefix(terms[low], prefix, prefixLen) && ifHasPrefix(terms[high], prefix, prefixLen)) {
              return low;
            } else if (ifHasPrefix(terms[low], prefix, prefixLen)) {
                return low;
            } else {
                return high;
            }
        }
       int mid = low + (int) Math.ceil(high - low >> 1);
       if (terms[mid].compareToByPrefixOrder(prefix, prefixLen) >= 0) {
           return lowerBound(prefix, low, mid);
       } else {
           return lowerBound(prefix, mid, high);
       }
    }

    private int upperBound(Term prefix, int low, int high) {
        int prefixLen = prefix.query().length();
        if (high - low == 1) {
            if (ifHasPrefix(terms[low], prefix, prefixLen) && ifHasPrefix(terms[high], prefix, prefixLen)) {
                return high;
            } else if (ifHasPrefix(terms[low], prefix, prefixLen)) {
                return low;
            } else {
                return high;
            }
        }
        int mid = low + (int) Math.ceil(high - low >> 1);
        if (terms[mid].compareToByPrefixOrder(prefix, prefixLen) >= 0 && !ifHasPrefix(terms[mid], prefix, prefixLen)) {
            return upperBound(prefix, low, mid);
        } else {
            return upperBound(prefix, mid, high);
        }
    }
    private boolean ifHasPrefix(Term term, Term prefix, int prefixLen) {
        return(term.compareToByPrefixOrder(prefix, prefixLen) == 0);
    }
}
