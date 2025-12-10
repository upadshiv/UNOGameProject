package ca.sheridancollege.project;

public class ReverseCard extends UNOCard {

    public ReverseCard(Color color) {
        super(color, CardType.REVERSE);
    }

    @Override
    public boolean canPlayOn(UNOCard topCard, Color currentColor) {
        return getColor() == currentColor || topCard.getType() == CardType.REVERSE;
    }

    @Override
    public int getPointValue() {
        return 20;
    }

    @Override
    public String toString() {
        return getColor() + " REVERSE";
    }
}
