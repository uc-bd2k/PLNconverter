package edu.uc.eh.structures;

/**
 * Created by chojnasm on 11/25/15.
 */
public class StringDoubleString {

    private String string;
    private Double aDouble;
    private String description;

    public StringDoubleString(String string, Double aDouble, String description) {
        this.string = string;
        this.aDouble = aDouble;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
