package edu.uc.eh.structures;

/**
 * Created by chojnasm on 11/25/15.
 */
public class StringDouble {

    private String string;
    private Double aDouble;

    public StringDouble(String string, Double aDouble) {
        this.string = string;
        this.aDouble = aDouble;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }
}
