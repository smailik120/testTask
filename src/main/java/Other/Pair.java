package Other;

public class Pair<T,V> {
    T criteria;
    V results;

    public Pair(T criteria, V results) {
        this.criteria = criteria;
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (criteria != null ? !criteria.equals(pair.criteria) : pair.criteria != null) return false;
        return results != null ? results.equals(pair.results) : pair.results == null;
    }

    @Override
    public int hashCode() {
        int result = criteria != null ? criteria.hashCode() : 0;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}
