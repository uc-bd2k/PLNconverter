package edu.uc.eh.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chojnasm on 11/25/15.
 */
public class StringDoubleStringList {

    private String string;
    private Double aDouble;
    private String description;
    private List<StringDoubleStringList> similar;

    public StringDoubleStringList(String string, Double aDouble, String description, List<StringDoubleStringList> similar) {
        this.string = string;
        this.aDouble = aDouble;
        this.description = description;
        this.similar = similar;
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

    public List<StringDoubleStringList> getSimilar() {
        return similar;
    }
}
