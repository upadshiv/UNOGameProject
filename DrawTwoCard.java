package ca.sheridancollege.project;

public class DrawTwoCard extends UNOCard {

    public DrawTwoCard(Color color) {
        super(color, CardType.DRAW_TWO);
    }

    @Override
    public boolean canPlayOn(UNOCard topCard, Color currentColor) {
        return getColor() == currentColor || topCard.getType() == CardType.DRAW_TWO;
    }

    @Override
    public int getPointValue() {
        return 20;
    }

    @Override
    public String toString() {
        return getColor() + " DRAW TWO";
    }
}
