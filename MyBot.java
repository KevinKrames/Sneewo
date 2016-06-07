
import org.jibble.pircbot.*;
import org.jibble.jmegahal.*;
import java.util.Timer;
import java.util.Random;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

//package com.javapapers.java;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;




public class MyBot extends PircBot {


//VARIABLES//
	
    public static final long DURATION = 1 * 15 * 1000;
	private static String BRAIN = "C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\brain.ser";
	private static String BRAIN2 = "C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\brain2.ser";
	//final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public JMegaHal hal = new JMegaHal();
	public JMegaHal hal2 = new JMegaHal();
	
		Twitter twitter;
		public String consumerKey = dbids.consumerKey;
		public String consumerSecret = dbids.consumerSecret;
		public String accessToken = dbids.accessToken;
		public String accessSecret = dbids.accessSecret;


    private Timer timer = new Timer(true);

	public String TempString1;

    public Boolean delay = false;

	private Boolean talkedTo = false;
	private Boolean talk = false;
	private int talkCounter = 0;
	private String talker;
	private String talkString;
	public String talkingTo = "";
	public String talkingTo2 = "";
	public String talkingTo3 = "";
	public String lastTalkingTo1 = "null";
	public String lastTalkingTo2 = "null";
	public String lastTalkingTo3 = "null";
	public boolean nameMentioned = false;

    private Random random = new Random( );

    private int WordTrigger;
    private int WordGroup;
    private int WordType;
    private int mood = random.nextInt(6)+1;
    private int moodchange = 0;
    private int TempNum1;
    private int TempNum2;
    private int TempNum3;
    private int TempNum4;
    private int TempNum5;
    private double[] speakChance = new double[100];

    public String lastSpoke = "";
    public String lastTalked1 = "";
    public String lastTalked2 = "";
    public String lastTalked3 = "";
    public String lastTalked4 = "";
    public String lastTalked5 = "";


    public String lastSpokenSentence = "";
    public String lastSpokenTrigger = "";

    //variables for sneewo game
	public ArrayList<String> channels = readFile("channels.txt");
    public ArrayList<String> timers = initTimers();
    public ArrayList<String> answers = initTimers();
    public ArrayList<String> lastWords = new ArrayList<String>();;


    //variables for sneewos brain
    public ArrayList<String> memory = new ArrayList<String>();

    public ArrayList<String> lastSaid = new ArrayList<String>();
	public ArrayList<Integer> lastSaidIndexs = new ArrayList<Integer>();
	

	public ArrayList<String> priorityWords = new ArrayList<String>();
	public ArrayList<String> priorityWordsAmount = new ArrayList<String>();

	public Boolean isMute = false;

	public int gameTimer = 0;

    private String TempString2;
    private String TempString3;
    private String TempString4;
    private String TempString5;
	private int trigger;
	private String name;
    private String TempWord1;
    private String TempWord2;
    private int lastmood;
	private boolean isMod;
	
	public int brainCounter = 0;
	public String lastSaidSentence = "";
	
	BufferedReader br = null;
	
	List<String> timeoutList = new ArrayList<String>();
	//List<int> timeoutListTime = new ArrayList<int>();
	List<String> banList = new ArrayList<String>();
	List<String> commandList = readCommands();
	
	ArrayList<String> angryCommands = readFile("angryCommands.txt");
	ArrayList<String> hypedCommands = readFile("hypedCommands.txt");
	ArrayList<String> sadCommands = readFile("sadCommands.txt");
	ArrayList<String> sarcasticCommands = readFile("sarcasticCommands.txt");
	ArrayList<String> chillCommands = readFile("chillCommands.txt");
	ArrayList<String> twitchyCommands = readFile("twitchyCommands.txt");
	
	ArrayList<String> mods = readFile("mods.txt");
	ArrayList<String> muteNames = readFile("channels.txt");
	ArrayList<String> muteBooleans = readFile("muteBooleans.txt");
	ArrayList<String> frequency = readFile("frequency.txt");
	ArrayList<String> badWords = readFile("badWords.txt");
	
	public String thisFrequency = "";
	

    public MyBot() {
		//INITIALIZE TWITTER
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret).setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecret);
    		TwitterFactory factory = new TwitterFactory(cb.build());
    		twitter = factory.getInstance();
    		/*
    		twitter.setOAuthConsumer(consumerKey, consumerSecret);
    		AccessToken accessToken = new AccessToken(accessToken, accessSecret);
    		twitter.setOAuthAccessToken(accessToken);*/

    	} catch (Exception te) {
    		te.printStackTrace();
    	}
		//
		//TWITTER DONE
		//
	
		ObjectInputStream in = null;
		try {
			File file = new File(BRAIN);
		    in = new ObjectInputStream(new FileInputStream(file));
			hal = (JMegaHal) in.readObject();
		} catch (FileNotFoundException e) {
			//firstRun();
		} catch (IOException e) {
			//firstRun();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		try {
			File file = new File(BRAIN2);
		    in = new ObjectInputStream(new FileInputStream(file));
			hal2 = (JMegaHal) in.readObject();
		} catch (FileNotFoundException e) {
			//firstRun();
		} catch (IOException e) {
			//firstRun();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	
        this.setName("Sneewo");
        //initialize update function
        MyBotTimerTask3 timerTask3 = new MyBotTimerTask3(this);
		timer.schedule(timerTask3, 0, 1000);

    }

public void onMessage(String channel, String sender, String login, String hostname, String message) {
	if (message.toLowerCase().contains("@sneeps")) {

		ArrayList<String> local = new ArrayList<String>();
		ArrayList<String> global = new ArrayList<String>();

		ArrayList<String> tempArray = readFile("sneeps" + channel + ".txt");
		ArrayList<String> tempNames = readFile("sneeps" + channel + "Names.txt");
		local = getValues(tempNames, tempArray, sender);

		tempArray = readFile("sneeps.txt");
		tempNames = readFile("sneepsNames.txt");
		global = getValues(tempNames, tempArray, sender);

		sendMessage(channel, sender + ", Global Sneeps: (#" + global.get(0) + ") " + global.get(1) + " | Sneeps in " + channel + ": (#" + local.get(0) + ") " + local.get(1));
	}
	isMod = false;
	if (mods.contains(sender)) {
		isMod = true;
	}
			//System.out.println(timers.get(channels.indexOf(channel)));
	if (!timers.get(channels.indexOf(channel)).equalsIgnoreCase("0")) {
		//We are playing the snee game here
		int channelIndex = channels.indexOf(channel);
		if (message.toLowerCase().contains(answers.get(channelIndex).toLowerCase())) {
			
			lastWords.add(answers.get(channelIndex).toLowerCase());
			if (lastWords.size() > 5) {
				lastWords.remove(lastWords.get(0));
			}

			timers.set(channelIndex, "0");
			ArrayList<String> local = new ArrayList<String>();
			ArrayList<String> global = new ArrayList<String>();
			//System.out.print("1");
			ArrayList<String> tempArray = readFile("sneeps" + channel + ".txt");
			ArrayList<String> tempNames = readFile("sneeps" + channel + "Names.txt");
			//System.out.print("2");
			addOne(tempNames, tempArray, sender, 1);
			//System.out.print("3");
			writeFile(tempArray, "sneeps" + channel + ".txt");
			writeFile(tempNames, "sneeps" + channel + "Names.txt");
			//System.out.print("4");
			local = getValues(tempNames, tempArray, sender);
			//System.out.print("5");

			tempArray = readFile("sneeps.txt");
			tempNames = readFile("sneepsNames.txt");
			addOne(tempNames, tempArray, sender, 1);
			writeFile(tempArray, "sneeps.txt");
			writeFile(tempNames, "sneepsNames.txt");
			global = getValues(tempNames, tempArray, sender);

			sendMessage(channel, "Congrats " + sender + "! You won! Sneeps: " + local.get(0));
		}
	} else
	//if (true == true) {
	if (!lastSpoke.equalsIgnoreCase(sender) || isMod == true) {
	//initial variables
	nameMentioned = false;
	if (sender != "sneewo") {
		isMute = false;
	//Check to see if we are muted
	for (int i = 0; i < muteBooleans.size(); i++) {
		if (muteNames.get(i).equalsIgnoreCase(channel) && muteBooleans.get(i).equalsIgnoreCase("true")) {
			isMute = true;
		}
	}
	
	//Check to see frequency
	for (int i = 0; i < frequency.size(); i++) {
		if (muteNames.get(i).equalsIgnoreCase(channel)) {
			thisFrequency = frequency.get(i);
		}
	}
	
	

	if (isMod == true && (message.toLowerCase().contains("@fillinthesneewo") || message.toLowerCase().contains("@fits"))) {
		
			int channelIndex = channels.indexOf(channel);
			if (timers.get(channelIndex).equalsIgnoreCase("0")) {
				//START SNEEWO MINI GAME
				sendMessage(channel, "Fill in the sneewo started! Sneewo will talk about a random word for 2 minutes, type the word in chat to guess what she is talking about and win sneeps!");
				timers.set(channelIndex,"60");
				String answer = getAnswer();
				answers.set(channelIndex, answer);
			}
	}

	if (isMod == true && message.contains("@tweet ")) {
		if (message.substring(0, 7).equalsIgnoreCase("@tweet ") && message.length() > 7) {
			//Send the tweet
			try {
				//Send the update
				twitter.updateStatus(message.substring(7));
				System.out.println("Posted: " + message.substring(7));
			} catch (TwitterException te) {
				te.printStackTrace();
			}
		}
	}

	//test command
	if (message.contains("@test") && sender.equalsIgnoreCase(channel.substring(1))) {
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("first");
		temp.add("second");
		temp.add("third");
		temp.add("fourth");
		temp.add("fifth");
		String tempStringTest = "";
		for (int i = 0; i < temp.size(); i++) {
			tempStringTest = tempStringTest + temp.get(i) + " ";
			if (i == 1) {
				temp.remove(i);
			}
		}
		sendMessage(channel, "Testing: " + tempStringTest);
	}
	//set frequency command
	if (message.contains("@frequency") && (isMod == true || sender.equalsIgnoreCase(channel.substring(1)))) {
	if (message.substring(0, 11).contains("@frequency ")) {
		for (int i = 0; i < frequency.size(); i++) {
			if (muteNames.get(i).equalsIgnoreCase(channel) && (message.substring(11, message.length()).equalsIgnoreCase("low") || message.substring(11, message.length()).equalsIgnoreCase("medium") || message.substring(11, message.length()).equalsIgnoreCase("high"))) {
				frequency.set(i, message.substring(11, message.length()));
				writeFile(frequency, "frequency.txt");
				sendMessage(channel, "Set frequency to " + message.substring(11, message.length()) + ".");
				thisFrequency = message.substring(11, message.length());
			}
		}	
	}
	}// end of check for frequency
	
	//mute sneetoswoman command
	if (message.contains("@muteSneetosWoman") && (isMod == true || sender.equalsIgnoreCase(channel.substring(1)))) {
	if (message.substring(0, 17).contains("@muteSneetosWoman")) {
		for (int i = 0; i < muteBooleans.size(); i++) {
			if (muteNames.get(i).equalsIgnoreCase(channel) && (muteBooleans.get(i).equalsIgnoreCase("false"))) {
				muteBooleans.set(i, "true");
				writeFile(muteBooleans, "muteBooleans.txt");
				sendMessage(channel, "Muted.");
			}
		}	
		
	}
	}// end of check for mute
	
	//unmute sneetoswoman command
	if (message.contains("@unmuteSneetosWoman") && (isMod == true || sender.equalsIgnoreCase(channel.substring(1)))) {
	if (message.substring(0, 19).contains("@unmuteSneetosWoman")) {
		for (int i = 0; i < muteBooleans.size(); i++) {
			if (muteNames.get(i).equalsIgnoreCase(channel) && muteBooleans.get(i).equalsIgnoreCase("true")) {
				muteBooleans.set(i, "false");
				writeFile(muteBooleans, "muteBooleans.txt");
				sendMessage(channel, "Unmuted.");
			}
		}	
		
	}
	}// end of check for unmute
	
		
	if (isMute == false) {


	//test code to read arrays
	if (message.contains("@setMood ") && isMod == true) {
		mood = Character.getNumericValue(message.charAt(9));
		sendMessage(channel, "Mood set to: " + mood);
		for (int i=0; i < sarcasticCommands.size(); i++) {
			//sendMessage(channel, sarcasticCommands.get(i));
		}
	}

	if (message.contains("@speak ") && isMod == true) {
		speak1(channel, sender, login, hostname, hal2.getSentence(message.substring(7,message.length())));
	}
	

//MOD COMMANDS END//

	if ((message.toLowerCase().contains("sneetoswoman") || message.toLowerCase().contains("sneewo") || message.toLowerCase().contains("snee-chin") || message.toLowerCase().contains("snee-chan") || message.toLowerCase().contains("snee-chan") || message.toLowerCase().contains("sneetos woman") || message.toLowerCase().contains("sw, "))) {
		if (talkingTo.isEmpty()) {
			talkingTo = sender;
		}
			nameMentioned = true;
		if (!talkingTo.equalsIgnoreCase(lastTalkingTo1)) {
		//sendMessage("#moltov", "triggered" + talkingTo + talkingTo.equalsIgnoreCase(sender));
			
		} else {
		talkingTo = "";
		}
	}
	if (random.nextInt(8) == 0 || talkingTo.equalsIgnoreCase(sender) || (nameMentioned == true && random.nextInt(8) <= 5)) {

	String data = getMessage(channel, message);
	/*data = "";
	String newMessage = removeSymbols(message);
	newMessage = removeStrings(newMessage, "smallWords.txt", true);
	String aTrigger = getTrigger(newMessage);
	lastSpokenTrigger = aTrigger;
	data = hal2.getSentence(aTrigger);
	lastSpokenTrigger = aTrigger;
	lastSpokenSentence = message;
	if (!data.toLowerCase().contains(aTrigger.toLowerCase())) {
		aTrigger = getTrigger(newMessage);
		lastSpokenTrigger = aTrigger;
		data = hal2.getSentence(aTrigger);
		
		if (!data.toLowerCase().contains(aTrigger.toLowerCase())) {
			aTrigger = getTrigger(newMessage);
			lastSpokenTrigger = aTrigger;
			data = hal2.getSentence(aTrigger);
			if (!data.toLowerCase().contains(aTrigger.toLowerCase())) {
				aTrigger = getTrigger(newMessage);
				lastSpokenTrigger = aTrigger;
				data = hal2.getSentence(aTrigger);
				if (!data.toLowerCase().contains(aTrigger.toLowerCase())) {
					aTrigger = getTrigger(newMessage);
					lastSpokenTrigger = aTrigger;
					data = hal2.getSentence(aTrigger);
				}
			}
		}
	}*/
	
	if (!data.equalsIgnoreCase("null") && !sender.contains("sneewo")) {
	//remove sneewos names before sending:
	if (data.toLowerCase().contains("sneewo ")) {
	if (data.toLowerCase().substring(0, 7).equalsIgnoreCase("sneewo ")) {
		data = data.substring(7,data.length());
	}
	}

	if (data.toLowerCase().contains("sneewo, ")) {
	if (data.toLowerCase().substring(0, 8).equalsIgnoreCase("sneewo, ")) {
		data = data.substring(8,data.length());
	}
	}

	//remove sneewos names before sending:
	if (data.toLowerCase().contains("sneetoswoman")) {
	if (data.toLowerCase().substring(0, 12).equalsIgnoreCase("sneetoswoman")) {
		data = data.substring(12,data.length());
	}
	}
	//remove sneewos names before sending:
	if (data.toLowerCase().contains("sneetos")) {
	if (data.toLowerCase().substring(0, 7).equalsIgnoreCase("sneetos")) {
		data = data.substring(6,data.length());
	}
	}
	//remove sneewos names before sending:
	if (data.toLowerCase().contains("snee-chin")) {
	if (data.toLowerCase().substring(0, 9).equalsIgnoreCase("snee-chin")) {
		data = data.substring(9,data.length());
	}
	}
	
	//TESTING
	if (data.toLowerCase().contains(" sneewo")) {
	data = data.replaceAll(" sneewo", "");
	data = data.replaceAll(" Sneewo", "");
	}
	
	if (data.toLowerCase().contains(" sneewo?")) {
	data = data.replaceAll(" sneewo?", "?");
	data = data.replaceAll(" Sneewo?", "?");
	}
	if (data.toLowerCase().contains(" sneetoswoman?")) {
	data = data.replaceAll(" sneetoswoman?", "?");
	data = data.replaceAll(" SneetosWoman?", "?");
	data = data.replaceAll(" Sneetoswoman?", "?");
	}
	if (data.toLowerCase().contains(" snee-chin?")) {
	data = data.replaceAll(" snee-chin?", "?");
	data = data.replaceAll(" Snee-chin?", "?");
	}
	
	//remove space before sending:
	if (data.toLowerCase().charAt(0) == ' ') {
		data = data.substring(1);
	}
	//remove space before sending:
	if (data.toLowerCase().charAt(0) == ',') {
		data = data.substring(1);
	}
	//remove space before sending:
	if (data.toLowerCase().charAt(0) == ':') {
		data = data.substring(1);
	}
	
	
	//actually send it
	if (!data.equalsIgnoreCase("null")) {
		speak1(channel, sender, login, hostname, data);
	}

	}


	}//end of delay*/
	} // end of isMute check
	//record the message:
	if (!sender.contains("besson") && !sender.contains("sneetoswoman") && !sender.contains("sneewo") && !sender.contains("sneeto") && !sender.contains("snee-chin") && !sender.contains("sneetosman")&& !sender.equalsIgnoreCase("zeldobot") && message.length() < 100) {
		boolean badWordsCheck = false;
		for (int i = 0; i < badWords.size(); i++) {
			if (message.toLowerCase().contains(badWords.get(i))) {
				badWordsCheck = true;
			}
		}
		if (badWordsCheck == false) {
			//Add the message to the memory
			addMemory(channel,message);
			//Update the brain
			hal.add(message);
			hal2.add(message);
			updateWordPriority(message, "generalSentence");
		}
	 }
		trigger = 0;
		name = channel.substring(1);
		
		if (brainCounter == 2000) {
			//save the brain!
			double startTime = System.currentTimeMillis();
			try {
				// save the new data
				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
				out.writeObject(hal);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//save the brain!
			try {
				// save the new data
				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN2));
				out.writeObject(hal2);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			brainCounter = 0;

			double endTime = System.currentTimeMillis();
			System.out.println("Time to start save:" + startTime);
			System.out.println("Time to end save:" + endTime);
			System.out.println("Time to get save:" + (endTime - startTime));
			
		} else {
		brainCounter++;
		}
	updatePriorityDatabase();
	}
	}
	lastSpoke = sender;
}//end of on message
	public ArrayList<String> getValues(ArrayList<String> names, ArrayList<String> array, String player) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("" + names.size());
		values.add("0");
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(player)) {
				values.set(0, "" + (i+1));
				values.set(1, array.get(i));
			}
		}
		return values;
	}
	public void addOne(ArrayList<String> names, ArrayList<String> array, String player, int amount) {
		boolean exists = false;
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(player)) {
				exists = true;
				int temp = Integer.parseInt(array.get(i)) + amount;
				array.set(i, ""+temp);
				i = names.size();
			}
		}
		if (exists == false) {
			names.add(player);
			array.add("1");
		}
		sortAL(names,array);
	}

	public void sortAL(ArrayList<String> names, ArrayList<String> array) {
		boolean done = true;
		while (done != true) {
			done = true;
		for (int i = 0; i < names.size()-1; i++) {
			if (Integer.parseInt(array.get(i)) < Integer.parseInt(array.get(i+1))) {
				done = false;
				String temp = names.get(i);
				String temp2 = array.get(i);
				names.set(i, names.get(i+1));
				array.set(i, array.get(i+1));

				names.set(i+1, temp);
				array.set(i+1, temp2);
			}
		}

		}
	}
	//update function that is called once every second
	public void update() {
		//update timers
		for (int i = 0; i < timers.size(); i++) {

			if (Integer.parseInt(timers.get(i)) % 15 == 10) {
				String temp = hal2.getSentence(answers.get(i));
				sendMessage(channels.get(i), addUnderscores(temp, answers.get(i)));
			}
			if (Integer.parseInt(timers.get(i)) == 1) {
				sendMessage(channels.get(i), "Times up no one guessed the answer: " + answers.get(i));
			}
			if (Integer.parseInt(timers.get(i)) > 0) {
				timers.set(i, "" + (Integer.parseInt(timers.get(i))-1));
				System.out.println("" + (Integer.parseInt(timers.get(i))-1));
			}
		}
	}

	public String addUnderscores(String a, String b) {
		String u = "_";
		for (int i = 1; i < b.length(); i++) {
			u = u + " _";
		}
		a = a.replaceAll(b, u);
		return a;
	}

	public String getAnswer() {
		String answer = "";
		boolean done = false;

		while (done == false) {
			done = true;
			String tempSentence = hal2.getSentence();
			tempSentence = removeSymbols(tempSentence);
			tempSentence = removeStrings(tempSentence, "smallWords.txt", true);
			ArrayList<String> newTriggers = getTriggers(tempSentence);
			int randomNumber = random.nextInt(newTriggers.size()-1);
			answer = newTriggers.get(randomNumber);

			for (int i = 0; i < lastWords.size(); i++) {
				if (answer.equalsIgnoreCase(lastWords.get(i))) {
					done = false;
				}
			}
			if (answer.length() < 3) {
				done = false;
			}
		}
		return answer;
	}
	public String getMessage(String channel, String message) {
		double startTime = System.currentTimeMillis();

		//initialize variables
		String data = "";
		String highestSentence = "";
		int highestScore = 0;
		int maxTests = 50;
		int index = 0;
		boolean exists = false;
		String newMessage = removeSymbols(message);
		newMessage = removeStrings(newMessage, "smallWords.txt", true);
		ArrayList<String> newTriggers = getTriggers(newMessage);
		ArrayList<String> memoryTriggers = new ArrayList<String>();

		for (int i = 0; i < memory.size(); i++) {
			if (memory.get(i).equalsIgnoreCase("#" + channel)) {
				exists = true;
				index = i+1;
			}
		}

		if (exists == true) {
			//we found the channel loop through the memory and find the most suitable message
			while (index < memory.size()) {
				if (memory.get(index).charAt(0) == '#') {
					//we hit a channel, set index to the end
					index = memory.size();
				} else {
					String tempMessage = removeSymbols(memory.get(index).substring(1));
					tempMessage = removeStrings(tempMessage, "smallWords.txt", true);
					ArrayList<String> tempTriggers = getTriggers(tempMessage);
					for (int i = 0; i < tempTriggers.size(); i++) {
						memoryTriggers.add(tempTriggers.get(i));
					}

				}

				index++;
			}
			//we have the triggers of the memory sentences
			//loop through the main triggers and make our sentences based on the them
			for (int i = 0; i < newTriggers.size(); i++) {
				for (int j = 0; j < maxTests; j++) {
					int value = 0;
					//Make a sentence and check if it's better than the current sentence
					String tempSentence = hal2.getSentence(newTriggers.get(i));
					String tempSentence2 = removeSymbols(tempSentence);
					tempSentence2 = removeStrings(tempSentence2, "smallWords.txt", true);
					ArrayList<String> tempTriggers = getTriggers(tempSentence2);
					ArrayList<String> tempWords = new ArrayList<String>();
					//Compare all of the constructed sentences triggers against the main sentence and the memory senetences
					for (int k = 0; k < newTriggers.size(); k++) {
						for(int k2 = 0; k2 < tempTriggers.size(); k2++) {
							if (newTriggers.get(k).equalsIgnoreCase(tempTriggers.get(k2))) {
								tempWords.add(newTriggers.get(k));
								int temp = checkInArray(tempWords, newTriggers.get(k));
								if (temp == 1) {
									value += 8;
								} else if (temp == 2) {
									value += 4;
								} else if (temp > 5) {
									value -= 6;
								} else {
									value += 0;
								}
							}
						}
					}
					for (int k = 0; k < memoryTriggers.size(); k++) {
						for(int k2 = 0; k2 < tempTriggers.size(); k2++) {
							if (memoryTriggers.get(k).equalsIgnoreCase(tempTriggers.get(k2))) {
								tempWords.add(memoryTriggers.get(k));
								int temp = checkInArray(tempWords, memoryTriggers.get(k));
								if (temp == 1) {
									value += 4;
								} else if (temp == 2) {
									value += 2;
								} else if (temp > 5) {
									value -= 6;
								} else {
									value += 0;
								}
							}
						}
					}
					if (value > highestScore) {
						if (tempSentence.length() < 6) {
							value += 20;
						} else
						if (tempSentence.length() < 10) {
							value += 10;
						} else if (tempSentence.length() < 20) {
							value += 6;
						} else if (tempSentence.length() < 30) {
							value += 2;
						}
						System.out.println(value);
						highestScore = value;
						highestSentence = tempSentence;
					}

				}
			}

		} else {
			//theres no memory of the channel
			//Strip the message for triggers
			
			String aTrigger = getTrigger(newMessage);
			lastSpokenTrigger = aTrigger;
			highestSentence = hal2.getSentence(aTrigger);
		}

		

		//Update the last spoken trigger and sentences
		lastSpokenSentence = message;

		//Debug time
		double endTime = System.currentTimeMillis();
		System.out.println("Time to start message:" + startTime);
		System.out.println("Time to end message:" + endTime);
		System.out.println("Time to get message:" + (endTime - startTime));
		return highestSentence;
	}
	//checks how many times an item is found in an array
	public int checkInArray(ArrayList <String> array, String keyWord) {
		int checkHits = 0;

		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).equalsIgnoreCase(keyWord)) {
				checkHits++;
			}
		}

		return checkHits;
	}

	public void addMemory(String channel, String message) {
		//Store the message in short term memory
			int maxSentences = 10;
			boolean exists = false;
			int index = 0;
			for (int i = 0; i < memory.size(); i++) {
				if (memory.get(0).equalsIgnoreCase("#" + channel)) {
					exists = true;
					index = i+1;
					break;
				}
			}
			if (exists == true) {
				//channel exists:
				//add the sentence
				memory.add(index, "$" + message);
				//find if the amount of sentences has exceeded the max.
				boolean maxed = true;
				for (int i = 0; i < maxSentences; i++) {
					if ((index+i) >= memory.size()) {
						//we reached the end
						maxed = false;
						break;
					}
					if (memory.get(index + i).charAt(0) == '#') {
						//no increase in size
						maxed = false;
						break;
					}
				}
				//were at the maximum sentences, remove the end sentence
				if (maxed == true) {
					if (index + maxSentences < memory.size()) {
						if (memory.get(index+maxSentences).charAt(0) != '#') {
							memory.remove(index+maxSentences);
						}
					}
				}

			} else {
				//channel does not exist, add it!
				memory.add("#" + channel);
				memory.add("$" + message);
			}
			writeFile(memory, "memory.txt");
	}

	public void checkTriggers(ArrayList <String> checkCommands, String channel, String sender, String login, String hostname, String message) {
	
		String sendMessage = "";
		ArrayList<String> triggeredWords = new ArrayList<String>();
		lastSaidIndexs.clear();
		
		//get the triggered commands
		for (int i=0; i < checkCommands.size()-1; i+=2) {
		//check for period for case sensitive else just check without it.
			if (checkCommands.get(i).charAt(0) == '.') {
				if (message.contains(checkCommands.get(i).substring(1))) {
					triggeredWords.add(checkCommands.get(i+1));
					lastSaidIndexs.add(i);
				}
			
			} else {
				if (message.toLowerCase().contains(checkCommands.get(i))) {
					triggeredWords.add(checkCommands.get(i+1));
					lastSaidIndexs.add(i);
					
				}
			}
		}
		//fix the triggered array to make sure it isn't using the same trigger multiple times
		if (lastSaidIndexs.size() > 0) {
		for (int i = 0; i < lastSaidIndexs.size(); i++) { 
			boolean test = false;
			
			while (test == false && i < lastSaidIndexs.size()) {
				if (lastSaid.contains(checkCommands.get(lastSaidIndexs.get(i)))) {
					//sendMessage(channel, "removed " + checkCommands.get(lastSaidIndexs.get(i)+1));
					triggeredWords.remove(checkCommands.get(lastSaidIndexs.get(i)+1));
					lastSaidIndexs.remove(i);
					
				} else {
					test = true;
				}
			}
			
		}
		}
		
		
		//decide if we're going to says something, if so decide what to say
		if (triggeredWords.size() > 0) {
		
			if (random.nextInt(4) <= 2) {
				String data;
				if (triggeredWords.size() == 1) {
					sendMessage = triggeredWords.get(0);
					
					
					if (random.nextInt(4) <= 2) {
					data = hal2.getSentence(checkCommands.get(lastSaidIndexs.get(0)));
					} else {
					data = hal2.getSentence();
					}
					
				//Add this message to the lastSaid array and fix size if necessary.
				lastSaid.add(checkCommands.get(lastSaidIndexs.get(0)));
				if (lastSaid.size() > 10) {
					lastSaid.remove(0);
				}
					
				} else {
				//Find what to say
				int randomNumber = random.nextInt(triggeredWords.size()-1);
				sendMessage = triggeredWords.get(randomNumber);
				
				//Add this message to the lastSaid array and fix size if necessary.
				lastSaid.add(checkCommands.get(lastSaidIndexs.get(randomNumber)));
				if (lastSaid.size() > 10) {
					lastSaid.remove(0);
				}
				if (random.nextInt(4) <= 2) {
					data = hal2.getSentence(checkCommands.get(lastSaidIndexs.get(randomNumber)));
					} else {
					data = hal2.getSentence();
					}
				
				}
				for (int i = 0; i < sendMessage.length(); i++) { 
					if (sendMessage.charAt(i) == '$') {
						String oldMessage = sendMessage;
						sendMessage = oldMessage.substring(0, i) + sender + oldMessage.substring(i+1);
					}
					if (sendMessage.charAt(i) == '&') {
						String oldMessage = sendMessage;
						sendMessage = oldMessage.substring(0, i) + channel.substring(1) + oldMessage.substring(i+1);
					}
				}
				speak1(channel, sender, login, hostname, data);
			}
			
		
		}
		
		if (random.nextInt(20) == 0) {
				talkingTo = "";
				lastTalkingTo1 = sender;
				//lastTalkingTo2 = lastTalkingTo1;
				//lastTalkingTo3 = lastTalkingTo2;
			}
	
	}
	
//Updates word priorities based on a sentence given
public void updateWordPriority(String theSentence, String typeOfSentence) {
	theSentence = removeStrings(theSentence, "smallWords.txt", true);
	theSentence = removeSymbols(theSentence);
	boolean done = false;
	int amount = 0;

	//Update the words priority in the sentence that she is responding to
	if (typeOfSentence.equalsIgnoreCase("responding")) {
		while (done != true) {
			String nextWord = getNextWord(theSentence);
			theSentence = shortenByOneWord(theSentence);
			if (nextWord.equalsIgnoreCase(lastSpokenTrigger)) {
				amount = 50;
			} else {
				amount = 20;
			}


			updatePriorityDatabase(nextWord, amount);
			if (theSentence.equalsIgnoreCase("")) {
				done = true;
			}
		}
	}//End of updating responding sentence

	//Update the words priority in the sentence that she is speaking
	if (typeOfSentence.equalsIgnoreCase("spoken")) {
		while (done != true) {
			String nextWord = getNextWord(theSentence);
			theSentence = shortenByOneWord(theSentence);
			if (nextWord.equalsIgnoreCase(lastSpokenTrigger)) {
				amount = 50;
			} else {
				amount = 20;
			}


			updatePriorityDatabase(nextWord, amount);
			if (theSentence.equalsIgnoreCase("")) {
				done = true;
			}
		}
	}//End of updating spoken sentence

	//Update the words priority in the general sentences in the chat
	if (typeOfSentence.equalsIgnoreCase("generalSentence")) {
		while (done != true) {
			String nextWord = getNextWord(theSentence);
			theSentence = shortenByOneWord(theSentence);
			amount = 5;


			updatePriorityDatabase(nextWord, amount);
			if (theSentence.equalsIgnoreCase("")) {
				done = true;
			}
		}
	}//End of updating general sentences

}

//This method affects the priorityWords & priorityWordsAmount array lists and adds the amount if found
//Else it adds them to the database.
public void updatePriorityDatabase(String aWord, int amount) {
	boolean wordExists = false;
	for (int i = 0; i < priorityWords.size(); i++) {
		if (priorityWords.get(i).equalsIgnoreCase(aWord)) {
			wordExists = true;
			int newAmount = Integer.parseInt(priorityWordsAmount.get(i)) + amount;
			priorityWordsAmount.set(i, "" + newAmount);
		}
	}
	if (wordExists == false) {
		priorityWords.add(aWord);
		priorityWordsAmount.add("" + amount);
	}
}

//Decreases all of the priority words by 1 (removes from database if amount is zero)
public void updatePriorityDatabase() {
	for (int i = 0; i < priorityWords.size(); i++) {
		if (Integer.parseInt(priorityWordsAmount.get(i)) <= 0) {
			priorityWords.remove(i);
			priorityWordsAmount.remove(i);
			i--;
		} else {
			int number = Integer.parseInt(priorityWordsAmount.get(i))-1-(Integer.parseInt(priorityWordsAmount.get(i))/32);
			priorityWordsAmount.set(i, "" + number);
		}
	}
	sortDoubleArrays(priorityWords, priorityWordsAmount);
	writeFile(priorityWords, "priorityWords.txt");
	writeFile(priorityWordsAmount, "priorityWordsAmount.txt");
}

//Gets the next word in a string
public String getNextWord(String aSentence) {
	String aWord = "";
	for (int i = 0; i < aSentence.length(); i++) {
		if (aSentence.substring(i, i+1).equalsIgnoreCase(" ")) {
			//if we find a space
			if (aWord.equalsIgnoreCase("")) {
				//If we haven't found word, ignore the space
			} else {
				//Else we have finished finding the word
				i = aSentence.length();
			}
		} else {
			//We want to record the character
			aWord = aWord + aSentence.substring(i,i+1);
		}
	}
	return aWord;
}

//Removes the first word in a string and returns it
public String shortenByOneWord(String aSentence) {
	String newSentence = "";
	boolean foundLetter = false;
	for (int i = 0; i < aSentence.length(); i++) {
		if (aSentence.substring(i, i+1).equalsIgnoreCase(" ")) {
			//if we find a space
			if (foundLetter == false) {
				//If we haven't found a letter
			} else {
				//Else we have finished the rest of the sentence
				newSentence = aSentence.substring(i+1,aSentence.length());
				i = aSentence.length();
			}
		} else {
			//Else we found a letter
			foundLetter = true;
		}
	}
	return newSentence;
}

public void speak1(String channel, String sender, String login, String hostname, String message) {

	boolean speak = true;
	String temporary = message;
	if ((lastTalked1.equalsIgnoreCase(sender)) ||
		(lastTalked2.equalsIgnoreCase(sender)) ||
		(lastTalked3.equalsIgnoreCase(sender)) ||
		(lastTalked4.equalsIgnoreCase(sender)) ||
		(lastTalked5.equalsIgnoreCase(sender)) || sender.contains("sneetosman")) {
			speak = false;
	}
		
		if (message.contains("tweet ")) {
		if (message.substring(0, 6).equalsIgnoreCase("@tweet ") && message.length() > 6) {
			message = "@" + message;
		}
		}
		if (talkingTo.contains(sender) && !message.contains("@tweet ")) {
			if (random.nextInt(2) == 0) {
				message = sender + ", " + message;

			} else {
			
			}
		}

		if (speak == true || sender.contains("moltov")) {
			//keep track of the sentence without the name inside of it
			lastSaidSentence = temporary;
			//This is updating the priority of the sentence that she is responding to
			updateWordPriority(lastSpokenSentence, "responding");
			//This is updating the priority of the sentence that she is speaking.
			updateWordPriority(lastSaidSentence, "spoken");

			MyBotTimerTask timerTask = new MyBotTimerTask(this, sender, channel);
			timer.schedule(timerTask, DURATION);
			MyBotTimerTask2 timerTask2 = new MyBotTimerTask2(this, sender, channel, message);
			timer.schedule(timerTask2, 1000 + (random.nextInt(8))*1000);
			//Add the message to the memory
			addMemory(channel,message);
			delay = true;
			
			if (random.nextInt(6) == 0) {
				talkingTo = "";
				lastTalkingTo1 = sender;
			}
			
		}


		if (message.contains("@tweet ")) {
		if (message.substring(0, 7).equalsIgnoreCase("@tweet ") && message.length() > 7) {
			//Send the tweet
			try {
				//Send the update
				twitter.updateStatus(message.substring(7));
				System.out.println("Posted: " + message.substring(7));
			} catch (TwitterException te) {
				te.printStackTrace();
			}
		}
	}
}//End of speak1

public String removeStrings(String data, String file, boolean spaces) {
	
	ArrayList<String> tempArray = readFile(file);
	if (spaces) {
	for (int i = 0; i < tempArray.size()-1; i++) {
		
		for (int j = 0; j < (data.length() - tempArray.get(i).length() - 2); j++) {
			if ((" " + tempArray.get(i) + " ").equalsIgnoreCase(data.substring(j, j+tempArray.get(i).length()+2))) {
				data = data.substring(0, j+1) + data.substring(j+2+tempArray.get(i).length());
			}
		}
		if (data.length() >= tempArray.get(i).length()+1) {
		if ((tempArray.get(i) + " ").equalsIgnoreCase(data.substring(0, tempArray.get(i).length()+1))) {
			data = data.substring(tempArray.get(i).length()+1);
		}
		}
		if (data.length() >= tempArray.get(i).length()+1) {
		if ((" " + tempArray.get(i)).equalsIgnoreCase(data.substring(data.length() - (tempArray.get(i).length()+1), data.length()))) {
			data = data.substring(data.length() - (tempArray.get(i).length()+1));
		}
	}
	}
	}

	return data;
}
//finds a trigger in a sentence to talk about
public ArrayList<String> getTriggers(String data) {
	String aTrigger = "";
	int max = 0;
	int current = 0;
	
	ArrayList<String> temp = new ArrayList<String>();
	for (int i = 0; i < data.length(); i++) {
		if (data.charAt(i) == ' ') {
			//check to see if we found a word
			if (current > 0) {
				temp.add(data.substring(i-current,i));
				current = 0;
			}
			
		} else {
			//increase current words letter count
			current++;
			if (current > max) {
				max = current;
			}
		}
		if (i == data.length()-1) {
			temp.add(data.substring(i+1-current,i+1));
		}
	}
	return temp;
}

//finds a trigger in a sentence to talk about
public String getTrigger(String data) {
	String aTrigger = "";
	int max = 0;
	int current = 0;
	
	ArrayList<String> temp = new ArrayList<String>();
	for (int i = 0; i < data.length(); i++) {
		if (data.charAt(i) == ' ') {
			//check to see if we found a word
			if (current > 0) {
				temp.add(data.substring(i-current,i));
				current = 0;
			}
			
		} else {
			//increase current words letter count
			current++;
			if (current > max) {
				max = current;
			}
		}
		if (i == data.length()-1) {
			temp.add(data.substring(i+1-current,i+1));
		}
	}
	for (int i = 0; i < temp.size(); i++) {
		System.out.println(temp.get(i));
	}

	String tempString = "";
	int min = max/2 + 2;
	if (min > max)
	min = max;

	///TESTING adding this to remember more words
	min = 2;
	//loop through the array to figure out which word to use (this does nothing???)
	for (int i = 0; i < temp.size(); i++) {
		tempString = tempString + temp.get(i) + ", ";
		if (temp.get(i).length() < min) {
		temp.remove(i);
		}
	}
	
	//choose the trigger
	if (temp.size() > 1) {
		boolean foundWord = false;
		for (int i = 0; i < priorityWords.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
			if (foundWord == false) {
				System.out.println(priorityWords.get(i) + " == " + temp.get(j) + "?");
				if (priorityWords.get(i).equalsIgnoreCase(temp.get(j))) {
					aTrigger = priorityWords.get(i);
					System.out.println("Found " + priorityWords.get(i));
					foundWord = true;
					i = priorityWords.size();
					j = temp.size();
				}
			}
		}
	}
		if (foundWord == false) {
			aTrigger = temp.get(random.nextInt(temp.size()-1));
		}
	}  else if (temp.size() == 1) {
		aTrigger = temp.get(0);
	}
	
	return aTrigger;
}

public String removeSymbols(String data) {
	for (int i = 0; i < data.length(); i++) {
		switch (data.charAt(i)) {
		case ',':
		case '.':
		case ';':
		case '/':
		case '?':
		case ':':
		case '\"':
		case '\'':
		case '\\':
		case '{':
		case '}':
		case '[':
		case ']':
		case '+':
		case '=':
		case '-':
		case '_':
		case '~':
		case '`':
		case '#':
		case '$':
		case '%':
		case '^':
		case '&':
		case '*':
		case '(':
		case ')':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '0':
		data = data.substring(0, i) + data.substring(i+1);
		
		break;
		default:
		break;
		}
	}
	return data;
}

public List<String> readCommands() {
	List<String> newString = new ArrayList<String>();
	int counter = 0;
	try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\commands.txt")))
		{
 
			String sCurrentLine;
 
			while ((sCurrentLine = br.readLine()) != null) {
			newString.add(counter, sCurrentLine);
			counter++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
	return newString;
}

public List<String> writeCommand(List<String> s, String newCommand, String channel) {
	
	//check for error
	
    String input = "";
	
	//add to array list
	s.add(newCommand);
	
	for (int i=0; i < s.size(); i++) {
		input = input + s.get(i) + System.getProperty("line.separator");
		}
	
        try {
          File file = new File("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\commands.txt");
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          output.write(input);
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
		
		sendMessage(channel, "All right fine I'll remember that.");
	return s;
	}// end of writeCommand
	
public List<String> removeCommand(List<String> s, String oldCommand, String channel) {
	
	//check for error
	
    String input = "";
	
	//add to array list
	s.remove(oldCommand);
	
	for (int i=0; i < s.size(); i++) {
		input = input + s.get(i) + System.getProperty("line.separator");
		}
	
        try {
          File file = new File("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\commands.txt");
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          output.write(input);
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
		
		sendMessage(channel, "Forget that? Sheesh so demanding...");
	return s;
	}// end of writeCommand
	
public ArrayList<String> readFile(String fileName) {
	ArrayList<String> newString = new ArrayList<String>();
	int counter = 0;
	if (new File("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\" + fileName).exists()){
	try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\" + fileName)))
		{
 
			String sCurrentLine;
 
			while ((sCurrentLine = br.readLine()) != null) {
			//System.out.println(sCurrentLine);
			newString.add(counter, sCurrentLine);
			counter++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	return newString;
} // end of readDoubleCommand

//init timers
public ArrayList<String> initTimers() {
	
	ArrayList<String> s = new ArrayList<String>();
	for (int i=0; i < channels.size(); i++) {
		s.add("0");
	}
		
	return s;
	}// 

public ArrayList<String> writeFile(ArrayList<String> s, String newCommand, String channel, String fileName) {
	
	//check for error
	
	String input = "";
	
	//add to array list
	s.add(newCommand);
	
	for (int i=0; i < s.size(); i++) {
		input = input + s.get(i) + System.getProperty("line.separator");
		}
	
		try {
			File file = new File("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\" + fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(input);
			output.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
	return s;
	}// end of writeDoubleCommand
	
public void writeFile(ArrayList<String> s, String fileName) {

	//check for error
	
	String input = "";
	
	
	for (int i=0; i < s.size(); i++) {
		input = input + s.get(i) + System.getProperty("line.separator");
		}
	
		try {
			File file = new File("C:\\Users\\Kevin\\Desktop\\bot\\sneetoswoman\\" + fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(input);
			output.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}// end of writefile
	//Sorts two arrays from largest to smallest based apon the amount which is stored in the second string
public void sortDoubleArrays(ArrayList<String> names, ArrayList<String> amount) {
	boolean changed = true;
	while (changed == true) {
		changed = false;
		for (int i = 0; i < names.size()-1; i++) {
			if (Integer.parseInt(amount.get(i)) < Integer.parseInt(amount.get(i+1))) {
				//If the element at i is less than i+1 then swap them and repeat until sorted
				String swap1 = names.get(i+1);
				String swap2 = amount.get(i+1);
				names.set(i+1, names.get(i));
				amount.set(i+1, amount.get(i));
				names.set(i, swap1);
				amount.set(i, swap2);
				changed = true;
			}
		}
	}
}
}//end of pircbot