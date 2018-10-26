package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(info.PREFIX + "clear")){
           if(args.length <= 1){
               sendErrorMessage(event.getChannel(), event.getMember());
           }else{
               TextChannel target = event.getMessage().getMentionedChannels().get(0);
               purgeMessage(target, Integer.parseInt(args[2]));
               if(args.length > 3){
                   String reason = "";
                   for(int i = 3;i > args.length; i++) {
                       reason += args[1] + " ";
                   }
                   log(event.getMember(), args[2],reason, event.getGuild().getTextChannelById("498625069015236618"), target);
               }else{
                   log(event.getMember(), args[2],"", event.getGuild().getTextChannelById("498625069015236618"), target);
               }
           }
        }
    }
    public void sendErrorMessage(TextChannel channel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invaild Usage");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#42ebf4"));
        builder.setDescription("<> = required, {} = optional");
        builder.addField("Proper usage: !clear {#channel} {reason}", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }
    public void log( Member clearer, String num,String reason, TextChannel incident, TextChannel cleared){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Cleared");
        builder.setColor(Color.decode("#fc1414"));
        builder.addField("Cleared Channel", incident.getAsMention(), false);
        builder.addField("Number of Messages Cleared", num, false);
        builder.addField("Clearer", clearer.getAsMention(), false);
        builder.addField("Reason", reason, false);
        builder.addField("date", sdf.format(date), false);
        builder.addField("Time", stf.format(date), false);
        incident.sendMessage(builder.build()).queue();
    }
    private void purgeMessage(TextChannel channel, int num){
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;

        msgs = history.retrievePast(num).complete();
        channel.deleteMessages(msgs).queue();
    }
}
