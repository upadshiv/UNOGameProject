package ca.sheridancollege.project;

public class SkipCard extends UNOCard {

    public SkipCard(Color color) {
        super(color, CardType.SKIP);
    }

    @Override
    public boolean canPlayOn(UNOCard topCard, Color currentColor) {
        return getColor() == currentColor || topCard.getType() == CardType.SKIP;
    }

    @Override
    public int getPointValue() {
        return 20;
    }

    @Override
    public String toString() {
        return getColor() + " SKIP";
    }
}
