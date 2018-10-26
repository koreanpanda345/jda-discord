package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class rollCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].equalsIgnoreCase(info.PREFIX + "roll")) {
            Random rand = new Random();
            int roll = rand.nextInt(6) + 1;
            event.getChannel().sendMessage("You rolled a " + roll).queue();
        }
    }
}
