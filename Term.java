package autocomplete;
import java.util.Objects;

public class Term implements Comparable<Term> {
    private String query;
    private long weight;

    /**
     * Initializes a term with the given query string and weight.
     *
     * @throws IllegalArgumentException if query is null or weight is negative
     */
    public Term(String query, long weight) {
        if (query == null || query.isEmpty() || weight < 0) {
            throw new IllegalArgumentException("Invalid query or weight value");
        }
        this.query = query;
        this.weight = weight;
    }

    /**
     * Compares the two terms in lexicographic order by query.
     *
     * @throws NullPointerException if the specified object is null
     */
    public int compareTo(Term that) {
        if (that == null) {
            throw new NullPointerException();
        }
        return query.compareTo(that.query);
    }

    /**
     * Compares to another term, in descending order by weight.
     */
    public int compareToByReverseWeightOrder(Term that) {
        //return weight.compareTo(that.weight);
        return Long.compare(that.weight, weight);
    }

    /**
     * Compares to another term in lexicographic order, but using only the first r characters
     * of each query. If r is greater than the length of any term's query, compares using the
     * term's full query.
     *
     * @throws IllegalArgumentException if r < 0
     */
    public int compareToByPrefixOrder(Term that, int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Not a valid r");
        }
        return this.queryPrefix(r).compareTo(that.queryPrefix(r));
        }

    /**
     * Returns this term's query.
     */
    public String query() {
        return this.query;
    }

    /**
     * Returns the first r characters of this query.
     * If r is greater than the length of the query, returns the entire query.
     *
     * @throws IllegalArgumentException if r < 0
     */
    public String queryPrefix(int r) {
        if (r > this.query.length()) {
            return this.query;
        } else {
            return this.query.substring(0, r);
        }
    }


    /**
     * Returns this term's weight.
     */
    public long weight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Term{" +
                "query='" + query + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Term term = (Term) o;
        return weight == term.weight &&
                Objects.equals(query, term.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, weight);
    }
}