package ca.sheridancollege.project;

public class WildDrawFourCard extends UNOCard {

    public WildDrawFourCard() {
        super(Color.WILD, CardType.WILD_DRAW_FOUR);
    }

    @Override
    public boolean canPlayOn(UNOCard topCard, Color currentColor) {
        return true;
    }

    @Override
    public int getPointValue() {
        return 50;
    }

    @Override
    public String toString() {
        return "WILD DRAW FOUR";
    }
}
