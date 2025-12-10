package ca.sheridancollege.project;

public class UNOPlayer extends Player {
    private Hand hand;
    private boolean hasCalledUNO;

    public UNOPlayer(String name) {
        super(name);
        this.hand = new Hand();
        this.hasCalledUNO = false;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean hasCalledUNO() {
        return hasCalledUNO;
    }

    public void setCalledUNO(boolean called) {
        this.hasCalledUNO = called;
    }

    public void drawCard(UNODeck deck) {
        UNOCard card = deck.drawCard();
        if (card != null) {
            hand.addCard(card);
            if (hand.getHandSize() > 1) {
                hasCalledUNO = false;
            }
        }
    }

    public UNOCard playCard(int cardIndex) {
        return hand.removeCard(cardIndex);
    }

    public boolean hasPlayableCard(UNOCard topCard, Color currentColor) {
        for (int i = 0; i < hand.getHandSize(); i++) {
            UNOCard card = hand.getCard(i);
            if (card.canPlayOn(topCard, currentColor)) {
                return true;
            }
        }
        return false;
    }

    public void displayHand() {
        System.out.println("\n" + getName() + "'s hand:");
        hand.displayHand();
        System.out.println("Cards in hand: " + hand.getHandSize());
    }

    @Override
    public void play() {
    }
}
