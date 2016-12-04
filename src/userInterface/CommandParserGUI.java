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
		
		commands.add(new HelpCommand());
		commands.add(new AvgCommand());
		//commands.add(new TestCommand());
		commands.add(new ClearCommand());
		commands.add(new AddNodeCommand());
		commands.add(new AddConnectionCommand());
		commands.add(new AddMessageCommand());
		commands.add(new RemoveNodeCommand());
		commands.add(new RemoveConnectionCommand());
		commands.add(new ViewNodeCommand());
		commands.add(new ViewAllCommand());
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