package edu.uc.eh.structures;

/**
 * Created by chojnasm on 11/25/15.
 */
public class CharacterDouble {
    private Character character;
    private Double aDouble;

    public CharacterDouble(Character string, Double aDouble) {
        this.character = string;
        this.aDouble = aDouble;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character string) {
        this.character = string;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterDouble)) return false;

        CharacterDouble that = (CharacterDouble) o;

        if (getCharacter() != null ? !getCharacter().equals(that.getCharacter()) : that.getCharacter() != null) return false;
        return !(getaDouble() != null ? !getaDouble().equals(that.getaDouble()) : that.getaDouble() != null);

    }

    @Override
    public int hashCode() {
        int result = getCharacter() != null ? getCharacter().hashCode() : 0;
        result = 31 * result + (getaDouble() != null ? getaDouble().hashCode() : 0);
        return result;
    }
}
