package me.dedose.bot.main;

import me.dedose.bot.event.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class BotMain {
    public static JDA jda;
    public static void main(String[] args) throws LoginException{
    jda = new JDABuilder(AccountType.BOT)
            .setToken("NDg2NjAwMzk0MzQ1MjgzNjA2.Dpkecw.aadskbPsBrDihXgL5aH67Y5JSyw")
            .build();
    jda.addEventListener(new HelloThere());
    jda.addEventListener(new OnlineCommand());
    jda.addEventListener(new MuteCommand());
    jda.addEventListener(new ClearCommand());
    jda.addEventListener(new TempmuteCommand());
    jda.addEventListener(new ExpSystem());
    jda.addEventListener(new HelpCommand());
    jda.addEventListener(new botInfoCommand());
    jda.addEventListener(new rollCommand());
    jda.addEventListener(new kickCommand());
    jda.addEventListener(new welcomer());
    ExpSystem system = new ExpSystem();
    system.startTimer();
    }

}
