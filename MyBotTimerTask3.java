import java.util.TimerTask;


public class MyBotTimerTask3 extends TimerTask {

    

    private MyBot bot;

    

    public MyBotTimerTask3(MyBot bot) {

	this.bot = bot;

    }

    

    public void run( ) {

        bot.update();

    }

    

}