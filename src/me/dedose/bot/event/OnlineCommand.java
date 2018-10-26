package me.dedose.bot.event;

import me.dedose.bot.main.info;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnlineCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(info.PREFIX + "online")) {
            int online = 0;
            for (int i = 0; i < event.getGuild().getMembers().size(); i++) {
                if(event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.ONLINE || event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB){
                    online++;
                }
            }
            event.getChannel().sendMessage("There are " + online + " Users online " + event.getGuild().getMembers().size() + " members in this Discord Server").queue();
        }
    }
}
