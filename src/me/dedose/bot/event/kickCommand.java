package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class kickCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(info.PREFIX + "kick")){
            if(message.isFromType((ChannelType.TEXT))) {
                if (args.length <= 1) {
                    sendErrorMessage(event.getChannel(), event.getMember());
                }
            }else{
                Guild guild = event.getGuild();
                Member selfMember = guild.getSelfMember();
                if (!selfMember.hasPermission(Permission.KICK_MEMBERS)) {
                    sendNoPermissionsEmbed(event.getChannel(), event.getMember());
                }
                guild.getController().kick(member).queue(
                        success -> channel.sendMessage("Kicked"),
                        error ->{
                            if(error instanceof PermissionException){
                                PermissionException pe = (PermissionException) error;


                            }
                        }
                );
            }
        }
    }
    public void sendErrorMessage(TextChannel channel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invaild Usage");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#42ebf4"));
        builder.setDescription("<> = required, {} = optional");
        builder.addField("Proper usage: !kick <@who> {reason}", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }
    public void sendNoPermissionsEmbed(TextChannel channel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Do not have permissions.");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#e50f00"));
        builder.addField("You do not have permissions to do this.", "you must have the permission to kick members to use this command", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }
    public void cantInteractEmbed(TextChannel channel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Cannot Kick Member");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#e50f00"));
        builder.addField("They are above me", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);

    }
}
