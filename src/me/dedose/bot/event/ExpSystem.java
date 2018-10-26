package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ExpSystem extends ListenerAdapter {
    HashMap<Member, Integer> playerXp = new HashMap<>();
    HashMap<Member, Integer> playerTimer = new HashMap<>();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(info.PREFIX + "xp")){
            event.getChannel().sendMessage("You have " + getPlayerXp(event.getMember()) + " xp").queue();
        }


        if(canGetXp(event.getMember()))
            randXp(event.getMember());
        setPlayerTimer(event.getMember(), 60);
    }

    private int getPlayerXp(Member member){
        return playerXp.get(member);
    }
    private void setPlayerXp(Member member, int num){
        playerXp.put(member, num);
    }
    private int getPlayerTime(Member member){
       return playerTimer.get(member);
    }
    private void setPlayerTimer(Member member, int num){
        playerTimer.put(member, num);
    }

    private void randXp(Member member){
        Random r = new Random();
        if(!playerXp.containsKey(member))
            setPlayerXp(member, 0);
        setPlayerXp(member, getPlayerXp(member) + r.nextInt(25));
    }
    private boolean canGetXp(Member member){
        if(!playerTimer.containsKey(member))
            return true;
        return false;
        }
    public void startTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Member member : playerTimer.keySet()) {
                    setPlayerTimer(member, getPlayerTime(member) - 1);
                }

            }
        };
        timer.schedule(task, 1000, 1000);
        }
}


