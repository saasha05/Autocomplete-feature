package autocomplete;
import java.util.ArrayList;
import java.util.Arrays;

public class LinearRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Terms contains null");
        }
        this.terms = terms;
    }


    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix.equals(null) || prefix.isEmpty()) {
            throw new IllegalArgumentException();
        }
        ArrayList<Term> matchingTerms = new ArrayList<>();
        //Term[] matchingTerms= new Term[100];
        int i = 0;
        int r = prefix.length();
        for (Term curr : terms) {
            String currPrefix = curr.queryPrefix(r);
            if (currPrefix.equals(prefix)) {
                matchingTerms.add(curr);
                //matchingTerms[i] = curr;
                //i++;
            }
        }
        // then sort with the comparators
        matchingTerms.sort(TermComparators.byReverseWeightOrder());
        Term[] matchingTermsArray = matchingTerms.toArray(new Term[matchingTerms.size()]);
        //Arrays.sort(matchingTerms, TermComparators.byReverseWeightOrder());
        return matchingTermsArray;
    }
}

