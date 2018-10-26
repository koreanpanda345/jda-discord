package me.dedose.bot.Card;
import java.util.ArrayList;
import java.util.List;

public class cardhand {

    protected ArrayList<cards> cardsInHand;

    public cardhand() {
        reset();
    }

    public void reset() {
        cardsInHand = new ArrayList<>();
    }

    public void add(cards card) {
        cardsInHand.add(card);
    }

    public boolean remove(cards card) {
        return cardsInHand.remove(card);
    }

    public List<cards> getHand() {
        return cardsInHand;
    }
}