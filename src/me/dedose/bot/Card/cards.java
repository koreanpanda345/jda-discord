package me.dedose.bot.Card;
import java.util.ArrayList;
import java.util.List;

public class cards {
    private static final List<cards> protoDeck = new ArrayList<>();

    static {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                protoDeck.add(new Card(rank, suit));
            }
        }
    }

    private final cardrank rank;
    private final cardsuit suit;

    private cards(cardrank rank, cardsuits suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static ArrayList<cards> newDeck() {
        return new ArrayList<>(protoDeck);
    }

    public cardrank getRank() {
        return rank;
    }

    public cardsuit getSuit() {
        return suit;
    }

    public String toString() {
        return rank.getDisplayName() + " of " + suit.getDisplayName();
    }

    public String toEmote() {
        return "[" + suit.getEmoticon() + rank.getEmoticon() + "]";
    }
}
