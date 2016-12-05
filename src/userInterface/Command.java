package userInterface;

import java.util.LinkedList;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;


public interface Command{
	public boolean isTrue(String[] words);
    public String runCommand();
    
    public static LinkedList<Command> getAllCommands(GuiTab g)
    {
    	LinkedList<Command> commands = new LinkedList<Command>();
    	
    	GraphicsCanvasGUI canvas = g.getCanvas();
		DialogManagerGUI dialog = g.getDialogManager();
		Simulation sim = g.getSimulation();
		
		commands.add(new HelpCommand(dialog));
		commands.add(new AvgCommand(sim));
		commands.add(new TestCommand(g));
		commands.add(new ClearCommand(sim));
		commands.add(new AddNodeCommand(dialog));
		commands.add(new AddNodePCommand(canvas, sim));
		commands.add(new AddConnectionCommand(canvas));
		commands.add(new AddConnectionPCommand(sim));
		commands.add(new AddMessageCommand(dialog));
		commands.add(new AddMessagePCommand(sim));
		commands.add(new RemoveNodeCommand(dialog));
		commands.add(new RemoveConnectionCommand(dialog));
		commands.add(new ViewNodeCommand(dialog));
		commands.add(new ViewAllCommand(sim));
		commands.add(new EmptyCommand());
		commands.add(new InvalidCommand());
    	return commands;
    }
}

abstract class ParameterCommand implements Command 
{
	protected String[] words;
	
	public abstract boolean isTrue(String[] words);
	public abstract String runCommand();
}

class HelpCommand implements Command {
	private DialogManagerGUI dialog;
	
	public HelpCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
    public boolean isTrue(String[] words)
    {
    	return words.length == 1 && words[0].equalsIgnoreCase("help");
    }

    public String runCommand(){
    	
    	dialog.open(DialogManagerGUI.HELP);
    	return ("");
    }
}

class AvgCommand implements Command {
    private Simulation sim;
	
	public AvgCommand(Simulation sim)
    {
    	this.sim = sim;
    }
    
	public boolean isTrue(String[] words){
	
    	return words.length == 1 && words[0].equalsIgnoreCase("average");
    
    	
    }

    public String runCommand(){
    	
    return 	"The average number of jumps messages have taken is " + sim.average() + ".\n";
    	
    }
}


class TestCommand implements Command {
    private GuiTab gui;
    
    public TestCommand(GuiTab gui)
    {
    	this.gui = gui;
    }
	
	public boolean isTrue(String[] words)
	{    
    	return words.length == 1 && words[0].equalsIgnoreCase("test");
    }

    public String runCommand()
    {
    	gui.buildTest();
    	return "The test network has been built.\n";
    }
}

class ClearCommand implements Command {
	private Simulation sim;
	
	public ClearCommand(Simulation sim)
	{
		this.sim = sim;
	}
  
	public boolean isTrue(String[] words)
	{
    	return words.length == 1 && words[0].equalsIgnoreCase("clear");
    }

    public String runCommand()
    {
    	sim.clear();
		return "Simulation cleared.\n";
    }
}

class AddNodeCommand implements Command 
{	
	private DialogManagerGUI dialog;
	
	public AddNodeCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		dialog.open(DialogManagerGUI.ADD_NODE);
		return("");
	}
}

class AddNodePCommand extends ParameterCommand
{
	private GraphicsCanvasGUI canvas;
	private Simulation sim;
	
	public AddNodePCommand(GraphicsCanvasGUI canvas, Simulation sim)
	{
		this.canvas = canvas;
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 3 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		Node n = sim.getNodeByName(words[2]);
		if(n == null)
		{
			canvas.clearSelection();
			canvas.setTempNode(new Node(words[2]));
			canvas.setState(CanvasState.ADDNODE);
			return("Use the canvas to place the node.\n");
		}
		else
		{
			return ("A node with this name already exists.\n");
		}
	}
}

class AddConnectionCommand implements Command 
{	
	private GraphicsCanvasGUI canvas;
	
	public AddConnectionCommand(GraphicsCanvasGUI canvas)
	{
		this.canvas = canvas;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand()
	{
		canvas.clearSelection();
		canvas.setState(CanvasState.ADDCONNECTION);
		return ("Select the first node.\n");
	}
}

class AddConnectionPCommand extends ParameterCommand
{
	private Simulation sim;
	
	public AddConnectionPCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 4 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand()
	{
		Node n1 = sim.getNodeByName(words[2]);
		Node n2 = sim.getNodeByName(words[3]);
		if(n1 == null || n2 == null)
		{
			 return "One of the two nodes does not exist.";
		}
		else
		{
			sim.addConnection(n1, n2);
			return ("Connection " + words[2] + " < - > " + words[3] + " has been added.\n");
		}
	}
	
}

class AddMessageCommand implements Command
{
	private DialogManagerGUI dialog;
	
	public AddMessageCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("message");
	}
	
	public String runCommand()
	{
		dialog.open(DialogManagerGUI.ADD_MESSAGE);
		return "";
	}
}

class AddMessagePCommand extends ParameterCommand
{
	private Simulation sim;
	
	public AddMessagePCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 4 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("message");
	}
	
	public String runCommand()
	{
		Node n1 = sim.getNodeByName(words[2]);
		Node n2 = sim.getNodeByName(words[3]);
		if(n1 == null || n2 == null)
		{
			return "One of the two nodes does not exist.";
		}
		else
		{
			Message msg = new Message(n1, n2);
			sim.addMsg(msg);
			return ("Message " + msg.getId() + " : " + words[2] + " - > " + words[3] + " has been added.\n");
			
		}
	}
}

class RemoveNodeCommand implements Command
{
	private DialogManagerGUI dialog;
	
	public RemoveNodeCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		dialog.open(DialogManagerGUI.REMOVE_NODE);
		return "";
	}
}

class RemoveNodePCommand extends ParameterCommand
{
	private Simulation sim;
	
	public RemoveNodePCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 2 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		Node n = sim.getNodeByName(words[2]);
		if(n == null) return ("The node named does not exist.\n");
		else
		{
			sim.removeNode(n);
			return ("Node " + words[2] + " has been removed.\n");
		}
	}
}

class RemoveConnectionCommand implements Command 
{
	private DialogManagerGUI dialog;
	
	public RemoveConnectionCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand()
	{
		dialog.open(DialogManagerGUI.REMOVE_CONNECTION);
		return "";
	}
}

class RemoveConnectionPCommand extends ParameterCommand
{
	private Simulation sim;
	
	public RemoveConnectionPCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 4 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand()
	{
		Node n1 = sim.getNodeByName(words[2]);
		Node n2 = sim.getNodeByName(words[3]);
		if(n1 == null || n2 == null)
		{
			return "One of the two nodes does not exist.";
		}
		else
		{
			if(sim.removeConnection(n1, n2))
			{
				return ("Connection " + words[2] + " < - > " + words[3] + " has been removed.\n");
			}
			else
			{
				return ("The specified connection does not exist.\n");
			}
		}
	}
}

class ViewNodeCommand implements Command 
{
	private DialogManagerGUI dialog;
	
	public ViewNodeCommand(DialogManagerGUI dialog)
	{
		this.dialog = dialog;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("view") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		dialog.open(DialogManagerGUI.VIEW_NODE);
		return "";
	}
}

class ViewNodePCommand extends ParameterCommand
{
	private Simulation sim;
	
	public ViewNodePCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] w)
	{
		words = w;
		return words.length == 3 && words[0].equalsIgnoreCase("view") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand()
	{
		Node n = sim.getNodeByName(words[2]);
		if(n == null) return ("The node named does not exist.\n");
		else
		{
			return (n.getDetails() + "\n");
		}
	}
}

class ViewAllCommand implements Command 
{
	private Simulation sim;
	
	public ViewAllCommand(Simulation sim)
	{
		this.sim = sim;
	}
	
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("view") && words[1].equalsIgnoreCase("all");
	}
	
	public String runCommand()
	{
		String temp = "";
		for(Node n: sim.getNodes())
		{
			temp += (n.getDetails() + "\n");
		}
		return temp;
	}
}

class EmptyCommand implements Command 
{
	public boolean isTrue(String[] words)
	{
		return words.length == 1 && words[0].equalsIgnoreCase("");
	}
	
	public String runCommand()
	{
		return "";
	}
}

class InvalidCommand implements Command 
{
	public boolean isTrue(String[] words)
	{
		return true;
	}
	
	public String runCommand()
	{
		return "Invalid command, for all commands use 'Help'.\n";
	}
}