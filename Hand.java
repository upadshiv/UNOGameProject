package ca.sheridancollege.project;

public class Hand extends GroupOfCards {

    public Hand() {
        super(50);
    }

    public void addCard(UNOCard card) {
        getCards().add(card);
    }

    public boolean removeCard(UNOCard card) {
        return getCards().remove(card);
    }

    public UNOCard removeCard(int index) {
        if (index >= 0 && index < getCards().size()) {
            return (UNOCard) getCards().remove(index);
        }
        return null;
    }

    public UNOCard getCard(int index) {
        if (index >= 0 && index < getCards().size()) {
            return (UNOCard) getCards().get(index);
        }
        return null;
    }

    public int getHandSize() {
        return getCards().size();
    }

    public boolean isEmpty() {
        return getCards().isEmpty();
    }

    public void displayHand() {
        for (int i = 0; i < getCards().size(); i++) {
            System.out.println((i + 1) + ". " + getCards().get(i));
        }
    }
}
