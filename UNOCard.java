package ca.sheridancollege.project;

public abstract class UNOCard extends Card {
    private Color color;
    private CardType type;

    public UNOCard(Color color, CardType type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CardType getType() {
        return type;
    }

    public abstract boolean canPlayOn(UNOCard topCard, Color currentColor);

    public abstract int getPointValue();
}
