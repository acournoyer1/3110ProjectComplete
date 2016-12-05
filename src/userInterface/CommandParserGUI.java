package userInterface;
import java.util.*;

import javax.swing.JTextArea;

public class CommandParserGUI {
	
	private ArrayList<Command> commands = new ArrayList<>();
	private JTextArea statusWindow;
	
	public CommandParserGUI(GUI g)
	{
		this.statusWindow = g.getStatusWindow();
		commands.addAll(Command.getAllCommands(g));
	}
	
	/**
	 * parse method:
	 * parses a command from the command line
	 * 
	 * @param the string to be parsed
	 */
	public void parse(String s)
	{
		String[] words = s.split(" ");
		if (words.length == 0){}
		else if(words.length >= 1)
		{
			for(Command c : commands){
				if(c.isTrue(words)){
					statusWindow.append(c.runCommand());
					break;
				}	
			}
		}
	}
	
	public void getAllCommands()
	{
		
	}
}