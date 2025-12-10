package ca.sheridancollege.project;

public class DiscardPile extends GroupOfCards {

    public DiscardPile() {
        super(108);
    }

    public void discard(UNOCard card) {
        getCards().add(card);
    }

    public UNOCard getTopCard() {
        if (getCards().isEmpty()) {
            return null;
        }
        return (UNOCard) getCards().get(getCards().size() - 1);
    }

    public java.util.ArrayList<Card> removeAllButTop() {
        java.util.ArrayList<Card> cardsToReturn = new java.util.ArrayList<>();
        if (getCards().size() <= 1) {
            return cardsToReturn;
        }
        
        for (int i = 0; i < getCards().size() - 1; i++) {
            cardsToReturn.add(getCards().get(i));
        }
        
        Card topCard = getCards().get(getCards().size() - 1);
        getCards().clear();
        getCards().add(topCard);
        
        return cardsToReturn;
    }

    public boolean isEmpty() {
        return getCards().isEmpty();
    }
}
