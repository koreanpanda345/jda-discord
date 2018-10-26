package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MuteCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(info.PREFIX + "mute")){
          if(args.length <= 1){
              sendErrorMessage(event.getChannel(),event.getMember());
          } else {
              Member target = event.getMessage().getMentionedMembers().get(0);
              Role muted = event.getGuild().getRolesByName("Muted", true).get(0);

              event.getGuild().getController().addSingleRoleToMember(target, muted).queue();

              if(args.length >= 3){
                  String reason = "";
                  for(int i = 2; i < args.length; i ++){
                      reason += args[1] + " ";
                  }
                  log(target, event.getMember(), reason, event.getGuild().getTextChannelById("498625069015236618"));
              }else{
                  log(target, event.getMember(), "", event.getGuild().getTextChannelById("498625069015236618"));
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
        builder.addField("Proper usage: !mute <@user> {reason}", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }
    public void log( Member muter, Member muted, String reason, TextChannel channel){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Mute Report");
        builder.setColor(Color.decode("#fc1414"));
        builder.addField("Muted User", muted.getAsMention(), false);
        builder.addField("Muter", muter.getAsMention(), false);
        builder.addField("Reason", reason, false);
        builder.addField("date", sdf.format(date), false);
        builder.addField("Time", stf.format(date), false);
        channel.sendMessage(builder.build()).queue();
    }
}
