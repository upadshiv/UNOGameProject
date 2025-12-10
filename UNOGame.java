package ca.sheridancollege.project;

import java.util.Scanner;

public class UNOGame extends Game {
    private UNODeck deck;
    private DiscardPile discardPile;
    private int currentPlayerIndex;
    private boolean clockwise;
    private Color currentColor;
    private Scanner scanner;

    public UNOGame(String name) {
        super(name);
        this.deck = new UNODeck();
        this.discardPile = new DiscardPile();
        this.currentPlayerIndex = 0;
        this.clockwise = true;
        this.scanner = new Scanner(System.in);
    }


    public void registerPlayers() {
        System.out.println("=================================");
        System.out.println("       WELCOME TO UNO!        ");
        System.out.println("=================================\n");
        
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 10) {
            System.out.print("Enter number of players (2-10): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numPlayers < 2 || numPlayers > 10) {
                    System.out.println("Please enter a number between 2 and 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        java.util.ArrayList<Player> players = new java.util.ArrayList<>();
        java.util.HashSet<String> usedNames = new java.util.HashSet<>();

        for (int i = 0; i < numPlayers; i++) {
            String name = "";
            boolean validName = false;
            
            while (!validName) {
                System.out.print("Enter name for Player " + (i + 1) + ": ");
                name = scanner.nextLine().trim();
                
                if (name.isEmpty()) {
                    System.out.println("Name cannot be blank.");
                } else if (usedNames.contains(name.toLowerCase())) {
                    System.out.println("Name already taken. Choose a different name.");
                } else {
                    validName = true;
                    usedNames.add(name.toLowerCase());
                }
            }
            
            players.add(new UNOPlayer(name));
        }

        setPlayers(players);
        System.out.println("\nAll players registered!\n");
    }


    public void startRound() {
        deck = new UNODeck();
        discardPile = new DiscardPile();
        
        System.out.println("Dealing cards...");
        for (Player p : getPlayers()) {
            UNOPlayer player = (UNOPlayer) p;
            player.getHand().getCards().clear();
            player.setCalledUNO(false);
            
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck);
            }
        }

        UNOCard firstCard = deck.drawCard();
        
        while (firstCard.getType() == CardType.WILD_DRAW_FOUR) {
            deck.addCard(firstCard);
            deck.shuffle();
            firstCard = deck.drawCard();
        }
        
        discardPile.discard(firstCard);
        currentColor = firstCard.getColor();

        if (firstCard.getType() == CardType.SKIP) {
            System.out.println("First card is SKIP! First player skipped.");
            nextPlayer();
        } else if (firstCard.getType() == CardType.REVERSE) {
            System.out.println("First card is REVERSE! Order reversed.");
            clockwise = !clockwise;
        } else if (firstCard.getType() == CardType.DRAW_TWO) {
            System.out.println("First card is DRAW TWO! First player draws 2.");
            UNOPlayer firstPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
            firstPlayer.drawCard(deck);
            firstPlayer.drawCard(deck);
            nextPlayer();
        }

        System.out.println("\nStarting card: " + firstCard);
        System.out.println("Active color: " + currentColor + "\n");
    }


    @Override
    public void play() {
        registerPlayers();
        startRound();
        playRound();
        scanner.close();
    }

    private void playRound() {
        boolean gameOver = false;
        
        while (!gameOver) {
            UNOPlayer currentPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
            
            if (deck.isEmpty()) {
                reshuffleDeck();
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println(currentPlayer.getName() + "'s turn");
            System.out.println("=".repeat(50));
            System.out.println("Top card: " + discardPile.getTopCard());
            System.out.println("Active color: " + currentColor);
            
            currentPlayer.displayHand();
            
            boolean hasPlayable = currentPlayer.hasPlayableCard(discardPile.getTopCard(), currentColor);
            
            if (!hasPlayable) {
                System.out.println("\nNo playable cards! Drawing a card...");
                currentPlayer.drawCard(deck);
                System.out.println("Drew: " + currentPlayer.getHand().getCard(currentPlayer.getHand().getHandSize() - 1));
                
                UNOCard drawnCard = currentPlayer.getHand().getCard(currentPlayer.getHand().getHandSize() - 1);
                if (drawnCard.canPlayOn(discardPile.getTopCard(), currentColor)) {
                    System.out.print("You can play this card! Play it? (Y/N): ");
                    String response = scanner.nextLine().trim().toUpperCase();
                    if (response.equals("Y")) {
                        UNOCard cardToPlay = currentPlayer.playCard(currentPlayer.getHand().getHandSize() - 1);
                        playCardAction(cardToPlay, currentPlayer);
                        
                        if (currentPlayer.getHand().isEmpty()) {
                            gameOver = true;
                            declareWinner();
                            continue;
                        }
                    }
                }
                
                nextPlayer();
                continue;
            }
            
            boolean validPlay = false;
            while (!validPlay) {
                System.out.print("\nEnter card number to play (or 0 to view game state, -1 to draw): ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine().trim());
                    
                    if (choice == 0) {
                        viewGameState();
                        currentPlayer.displayHand();
                        continue;
                    } else if (choice == -1) {
                        currentPlayer.drawCard(deck);
                        System.out.println("Drew a card.");
                        nextPlayer();
                        validPlay = true;
                    } else if (choice > 0 && choice <= currentPlayer.getHand().getHandSize()) {
                        UNOCard selectedCard = currentPlayer.getHand().getCard(choice - 1);
                        
                        if (selectedCard.canPlayOn(discardPile.getTopCard(), currentColor)) {
                            UNOCard cardToPlay = currentPlayer.playCard(choice - 1);
                            playCardAction(cardToPlay, currentPlayer);
                            validPlay = true;
                            
                            if (currentPlayer.getHand().isEmpty()) {
                                gameOver = true;
                                declareWinner();
                                continue;
                            }
                            
                            nextPlayer();
                        } else {
                            System.out.println("Invalid play! That card cannot be played on " + 
                                             discardPile.getTopCard() + " with color " + currentColor);
                        }
                    } else {
                        System.out.println("Invalid choice! Please enter a number between 1 and " + 
                                         currentPlayer.getHand().getHandSize());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
        }
    }


    private void playCardAction(UNOCard card, UNOPlayer player) {
        discardPile.discard(card);
        System.out.println(player.getName() + " played: " + card);
        
        if (player.getHand().getHandSize() == 1) {
            System.out.print("Say UNO! (Y/N): ");
            String response = scanner.nextLine().trim().toUpperCase();
            if (response.equals("Y")) {
                player.setCalledUNO(true);
                System.out.println(player.getName() + " called UNO!");
            } else {
                System.out.println(player.getName() + " forgot to call UNO!");
                challengeUNOCall(player);
            }
        } else {
            player.setCalledUNO(false);
        }
        
        switch (card.getType()) {
            case SKIP:
                System.out.println("Next player is skipped!");
                nextPlayer();
                break;
                
            case REVERSE:
                clockwise = !clockwise;
                System.out.println("Direction reversed!");
                if (getPlayers().size() == 2) {
                    nextPlayer();
                }
                break;
                
            case DRAW_TWO:
                nextPlayer();
                UNOPlayer nextPlayer = (UNOPlayer) getPlayers().get(currentPlayerIndex);
                System.out.println(nextPlayer.getName() + " draws 2 cards and loses their turn!");
                nextPlayer.drawCard(deck);
                nextPlayer.drawCard(deck);
                break;
                
            case WILD:
                currentColor = chooseColor(player);
                System.out.println("Color changed to " + currentColor);
                break;
                
            case WILD_DRAW_FOUR:
                currentColor = chooseColor(player);
                System.out.println("Color changed to " + currentColor);
                nextPlayer();
                UNOPlayer nextP = (UNOPlayer) getPlayers().get(currentPlayerIndex);
                System.out.println(nextP.getName() + " draws 4 cards and loses their turn!");
                for (int i = 0; i < 4; i++) {
                    nextP.drawCard(deck);
                }
                break;
                
            default:
                currentColor = card.getColor();
                break;
        }
    }

    private void challengeUNOCall(UNOPlayer targetPlayer) {
        System.out.println("Penalty: " + targetPlayer.getName() + " must draw 2 cards!");
        targetPlayer.drawCard(deck);
        targetPlayer.drawCard(deck);
    }

    private Color chooseColor(UNOPlayer player) {
        System.out.println("\nChoose a color:");
        System.out.println("1. RED");
        System.out.println("2. BLUE");
        System.out.println("3. GREEN");
        System.out.println("4. YELLOW");
        
        int choice = 0;
        while (choice < 1 || choice > 4) {
            System.out.print("Enter choice (1-4): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice! Please enter 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        
        switch (choice) {
            case 1: return Color.RED;
            case 2: return Color.BLUE;
            case 3: return Color.GREEN;
            case 4: return Color.YELLOW;
            default: return Color.RED;
        }
    }

    private void nextPlayer() {
        if (clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + getPlayers().size()) % getPlayers().size();
        }
    }

    private void reshuffleDeck() {
        System.out.println("\nDeck is empty! Reshuffling discard pile...");
        java.util.ArrayList<Card> cardsToReshuffle = discardPile.removeAllButTop();
        for (Card card : cardsToReshuffle) {
            deck.addCard((UNOCard) card);
        }
        deck.shuffle();
        System.out.println("Deck reshuffled!\n");
    }

    private void viewGameState() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("GAME STATE");
        System.out.println("=".repeat(50));
        System.out.println("Top card: " + discardPile.getTopCard());
        System.out.println("Active color: " + currentColor);
        System.out.println("Direction: " + (clockwise ? "Clockwise" : "Counter-clockwise"));
        System.out.println("\nPlayers:");
        for (Player p : getPlayers()) {
            UNOPlayer player = (UNOPlayer) p;
            System.out.println("  " + player.getName() + ": " + player.getHand().getHandSize() + " cards" +
                             (player.hasCalledUNO() ? " [UNO!]" : ""));
        }
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public void declareWinner() {
        UNOPlayer winner = null;
        for (Player p : getPlayers()) {
            UNOPlayer player = (UNOPlayer) p;
            if (player.getHand().isEmpty()) {
                winner = player;
                break;
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎉 " + winner.getName() + " WINS! 🎉");
        System.out.println("=".repeat(50));
        System.out.println("\nThanks for playing UNO!");
        System.out.println("=".repeat(50));
    }
}
