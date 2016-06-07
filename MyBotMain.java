import org.jibble.pircbot.*;

import java.util.Timer;
import java.util.Random;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyBotMain {
    
    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        MyBot bot = new MyBot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.twitch.tv",6667,dbids.twitchLogin);

       ArrayList<String> channels = readFile("channels.txt");
        // Join the #pircbot channel.
		for (int i=0; i < channels.size(); i++) {
			bot.joinChannel(channels.get(i));
		}
    }
	
	public static ArrayList<String> readFile(String fileName) {
		ArrayList<String> newString = new ArrayList<String>();
		int counter = 0;
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
	
		return newString;
	} // end of read file
    
}