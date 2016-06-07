import java.util.TimerTask;


public class MyBotTimerTask2 extends TimerTask {

    

    private MyBot bot;

    private String nick;

    private String channel;

    private String string1;

    

    public MyBotTimerTask2(MyBot bot, String nick, String channel, String string1) {

	this.bot = bot;

	this.nick = nick;

	this.channel = channel;

	this.string1 = string1;

    }

    

    public void run( ) {

        bot.sendMessage(channel, string1);

    }

    

}