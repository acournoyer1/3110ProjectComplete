package userInterface;
import java.util.*;

import javax.swing.JTextArea;

import backEnd.Simulation;

public class CommandParserGUI {
	
	private JTextArea statusWindow;
	private DialogManagerGUI dialog;
	private Simulation sim;
	private GraphicsCanvasGUI canvas;
	
	private ArrayList<Command> commands = new ArrayList<>();
	
	public CommandParserGUI(JTextArea statusWindow, DialogManagerGUI dialog, Simulation sim, GraphicsCanvasGUI canvas)
	{
		this.canvas = canvas;
		this.statusWindow = statusWindow;
		this.dialog = dialog;
		this.sim = sim;
		
		commands.add(new helpCommand());
		commands.add(new avgCommand());
		commands.add(new testCommand());
		commands.add(new clearCommand());
		commands.add(new addNodeCommand());
		commands.add(new addConnectionCommand());
		commands.add(new addMessageCommand());
		commands.add(new removeNodeCommand());
		commands.add(new removeConnectionCommand());
		commands.add(new viewNodeCommand());
		commands.add(new viewAllCommand());
		commands.add(new EmptyCommand());
		commands.add(new InvalidCommand());
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
					statusWindow.append(c.runCommand(sim, dialog, canvas));
					break;
				}	
			}
		}
	}
}