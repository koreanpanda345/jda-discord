package me.dedose.bot.Card;

public enum cardsuits {
    CLUBS("clubs", ":clubs:"),
    DIAMONDS("diamonds", ":diamonds:"),
    HEARTS("hearts", ":hearts:"),
    SPADES("spades", ":spades:");
    private final String displayName;
    private final String emoticon;

    cardsuits(String displayName, String emoticon) {

        this.displayName = displayName;
        this.emoticon = emoticon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmoticon() {
        return emoticon;
    }
}