import java.util.TimerTask;



public class MyBotTimerTask extends TimerTask {

    

    private MyBot bot;

    private String nick;

    private String channel;

    

    public MyBotTimerTask(MyBot bot, String nick, String channel) {

        this.bot = bot;

        this.nick = nick;

        this.channel = channel;

    }

    

    public void run( ) {

        bot.delay = false;

    }

    

}