package me.dedose.bot.Blackjack;
import me.dedose.bot.Card.cards;
import me.dedose.bot.Card.cardhand;
import me.dedose.bot.Card.cardrank;
import me.dedose.bot.Card.cardsuits;
public class blackjackhand extends cardhand{

        /**
         * calculates the value of the hand
         *
         * @return points
         */
        public int getValue() {
            int value = 0;
            int aces = 0;
            for (cards card : cardsInHand) {
                if (card.getRank().equals(cardrank.ACE)) {
                    aces++;
                }
                value += card.getRank().getValue();
            }
            while (aces > 0 && value > 21) {
                aces--;
                value -= 10;
            }
            return value;
        }

        public String printHand() {
            StringBuilder hand = new StringBuilder();
            for (cards card : cardsInHand) {
                hand.append(card.toEmote()).append(" ");
            }
            return hand.toString();
        }
}
