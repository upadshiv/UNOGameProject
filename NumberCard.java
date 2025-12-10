package ca.sheridancollege.project;

public class NumberCard extends UNOCard {
    private int number;

    public NumberCard(Color color, int number) {
        super(color, CardType.NUMBER);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean canPlayOn(UNOCard topCard, Color currentColor) {
        if (getColor() == currentColor) {
            return true;
        }
        if (topCard instanceof NumberCard) {
            return ((NumberCard) topCard).getNumber() == this.number;
        }
        return false;
    }

    @Override
    public int getPointValue() {
        return number;
    }

    @Override
    public String toString() {
        return getColor() + " " + number;
    }
}
