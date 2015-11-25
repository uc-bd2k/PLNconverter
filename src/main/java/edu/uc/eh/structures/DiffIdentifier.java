package edu.uc.eh.structures;

/**
 * Utility class to store Mass difference - ontology identifier pairs
 * Created by chojnasm on 11/25/15.
 */
public class DiffIdentifier implements Comparable<DiffIdentifier>{
    private Double diff;
    private String identifier;

    public DiffIdentifier(Double diff, String identifier) {
        this.diff = diff;
        this.identifier = identifier;
    }

    public Double getDiff() {
        return diff;
    }

    public String getIdentifier() {
        return identifier;
    }


    @Override
    public int compareTo(DiffIdentifier that) {
        return this.diff.compareTo(that.getDiff());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiffIdentifier)) return false;

        DiffIdentifier that = (DiffIdentifier) o;

        return !(getDiff() != null ? !getDiff().equals(that.getDiff()) : that.getDiff() != null);

    }

    @Override
    public int hashCode() {
        return getDiff() != null ? getDiff().hashCode() : 0;
    }
}
