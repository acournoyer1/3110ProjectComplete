
package userInterface;
import java.util.*;

import javax.swing.JTextArea;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;

public class CommandParserGUI {
	
	private JTextArea statusWindow;
	private DialogManagerGUI dialog;
	private Simulation sim;
	private GraphicsCanvasGUI canvas;
	
	private ArrayList<Commands> commands = new ArrayList<>();
	
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
		commands.add(new addCommand());
		commands.add(new removeCommand());
		commands.add(new viewCommand());
	}
	
	public void parse(String s)
	{
		String[] words = s.split(" ");
		
		
		
		if (words.length == 0){}
		else if(words.length >= 1)
		{
			for(Commands c : commands){
				
				if(c.isTrue(words)){
					statusWindow.append(c.runCommand(sim, dialog, canvas));
					break;
				}
				
					
				
		}
			
		}
			
			
			
		
		
		
	}
}
