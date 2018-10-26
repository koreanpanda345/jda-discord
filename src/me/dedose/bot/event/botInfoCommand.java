package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class botInfoCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(info.PREFIX + "info")){
            infoEmbed(event.getChannel(), event.getMember());
        }
    }
    public void infoEmbed(TextChannel channel, Member member) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Bot Info");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#42ebf4"));
        builder.setDescription("This is a Testing bot.");
        builder.addField("Creator", "Koreanpanda345#2878", false);
        builder.addField("Language", "Java", true);
        builder.addField("Library", "JDA", true);
        builder.addField("Created on", "", false);
        builder.addField("prefix", info.PREFIX, false);
        channel.sendMessage(builder.build()).queue();
    }
}
