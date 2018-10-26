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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TempmuteCommand extends ListenerAdapter {
    int counter = 0;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(info.PREFIX + "tempmute")) {
            if (args.length <= 2) {
                sendErrorMessage(event.getChannel(), event.getMember());
            } else {
                Member target = event.getMessage().getMentionedMembers().get(0);
                tempmute(target, parseTimeAmount(args[2]), parseTimeUnit(args[2]));
                if(args.length >= 4){
                    String reason = "";
                    for(int i = 3; i < args.length; i++){
                        reason += args[i] + " ";
                        log(target, event.getMember(), reason, event.getGuild().getTextChannelById("498625069015236618"));
                    }
                }
                log(target, event.getMember(), "", event.getGuild().getTextChannelById("498625069015236618"));
            }
        }
    }

    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invaild Usage");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#42ebf4"));
        builder.setDescription("<> = required, {} = optional");
        builder.addField("Proper usage: !tempmute <@user> {time(e.x. 48h)} {reason}", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

    public void log(Member muter, Member muted, String reason, TextChannel channel) {
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

    private int parseTimeAmount(String time) {
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toCharArray();
        int breakPoint = 0;
        String amount = "";
        int parseAmount = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] == 's' || t[i] == 'S') {
                unit = TimeUnit.SECONDS;
                breakPoint = i;
                break;
            } else if (t[i] == 'm' || t[i] == 'M') {
                unit = TimeUnit.MINUTES;
                breakPoint = i;
                break;
            }
        }
        for (int i = 0; i < breakPoint; i++) {
            amount += t[i];
        }
        parseAmount = Integer.parseInt(amount);
        return parseAmount;
    }

    private TimeUnit parseTimeUnit(String time) {
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toCharArray();
        int breakPoint = 0;
        String amount = "";
        int parseAmount = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] == 's' || t[i] == 'S') {
                unit = TimeUnit.SECONDS;
                breakPoint = i;
                break;
            } else if (t[i] == 'm' || t[i] == 'M') {
                unit = TimeUnit.MINUTES;
                breakPoint = i;
                break;
            }
        }
        return unit;
    }
    private void tempmute(Member target, int time, TimeUnit unit){
        Role muted = target.getGuild().getRolesByName("Muted", true).get(0);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            counter++;
                target.getGuild().getController().addSingleRoleToMember(target, muted).queue();
            if(counter == 2){
                target.getGuild().getController().removeRolesFromMember(target, muted);
                this.cancel();
            }
            }
        };
        switch(unit){
            case SECONDS:
                timer.schedule(task, 0, time * 1000);
                break;
            case MINUTES:
                timer.schedule(task, 0, (time * 1000) * 60);
                break;
        }
    }
}
