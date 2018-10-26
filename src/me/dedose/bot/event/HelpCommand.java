package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class HelpCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(info.PREFIX + "help")){
            helpMessage(event.getChannel(), event.getMember());

        }
    }
    public void helpMessage(TextChannel channel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Help");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#42ebf4"));
        builder.setDescription("<> = required, {} = optional");
        builder.addField("!clear {#channel} <number of messages> {reason}", "", false);
        builder.addField("!mute <@who> {reason}", "", false);
        builder.addField("!tempmute <@who> {time} {reason}", "", false);
        builder.addField("!xp", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }
}
