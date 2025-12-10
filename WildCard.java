package ca.sheridancollege.project;

public class WildCard extends UNOCard {

    public WildCard() {
        super(Color.WILD, CardType.WILD);
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
        return "WILD";
    }
}
