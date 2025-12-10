package ca.sheridancollege.project;

public class UNODeck extends GroupOfCards {

    public UNODeck() {
        super(108);
        initializeDeck();
    }

    private void initializeDeck() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        
        for (Color color : colors) {
            getCards().add(new NumberCard(color, 0));
            
            for (int i = 1; i <= 9; i++) {
                getCards().add(new NumberCard(color, i));
                getCards().add(new NumberCard(color, i));
            }
            
            getCards().add(new SkipCard(color));
            getCards().add(new SkipCard(color));
            
            getCards().add(new ReverseCard(color));
            getCards().add(new ReverseCard(color));
            
            getCards().add(new DrawTwoCard(color));
            getCards().add(new DrawTwoCard(color));
        }
        
        for (int i = 0; i < 4; i++) {
            getCards().add(new WildCard());
        }
        
        for (int i = 0; i < 4; i++) {
            getCards().add(new WildDrawFourCard());
        }
        
        shuffle();
    }

    public UNOCard drawCard() {
        if (getCards().isEmpty()) {
            return null;
        }
        return (UNOCard) getCards().remove(0);
    }

    public boolean isEmpty() {
        return getCards().isEmpty();
    }

    public void addCard(UNOCard card) {
        getCards().add(card);
    }
}
