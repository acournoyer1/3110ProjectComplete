package userInterface;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;


public interface Command{
	public boolean isTrue(String[] words);
    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas);
}

abstract class ParameterCommand implements Command 
{
	protected String[] words;
	
	public abstract boolean isTrue(String[] words);
	public abstract String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas);
}


class helpCommand implements Command {
    public boolean isTrue(String[] words)
    {
    	return words.length == 1 && words[0].equalsIgnoreCase("help");
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    	dialog.open(DialogManagerGUI.HELP);
    	return ("");
    }
}

class avgCommand implements Command {
    
	public boolean isTrue(String[] words){
	
    	return words.length == 1 && words[0].equalsIgnoreCase("average");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    return 	"The average number of jumps messages have taken is " + sim.average() + ".\n";
    	
    }
}


class testCommand implements Command {
    
	public boolean isTrue(String[] words){
        
    	return words.length == 1 && words[0].equalsIgnoreCase("test");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	sim.buildTest();
		
    return "The test network has been built.\n";
    }
}

class clearCommand implements Command {
    
	public boolean isTrue(String[] words){
        
    	return words.length == 1 && words[0].equalsIgnoreCase("clear");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	sim.clear();
		
    return "Simulation cleared.\n";
    }
}

class addNodeCommand extends ParameterCommand 
{	
	public boolean isTrue(String[] w)
	{
		this.words = w;
		
		return words.length >= 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			dialog.open(DialogManagerGUI.ADD_NODE);
			return("");
		}
		else if(words.length == 3)
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
		else
		{
			return ("The add node command must be followed by either no words or 1 word, the name of the node.\n");
		}
	}
}

class addConnectionCommand extends ParameterCommand 
{	
	public boolean isTrue(String[] w)
	{
		this.words = w;
		
		return words.length >= 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			canvas.clearSelection();
			canvas.setState(CanvasState.ADDCONNECTION);
			return ("Select the first node.\n");
		}
		else if(words.length == 4)
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
		else
		{
			return ("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
		}
		
	}
}

class addMessageCommand extends ParameterCommand 
{
	public boolean isTrue(String[] w)
	{
		this.words = w;
		return words.length >= 2 && words[0].equalsIgnoreCase("add") && words[1].equalsIgnoreCase("message");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			dialog.open(DialogManagerGUI.ADD_MESSAGE);
			return "";
		}
		else if(words.length == 4)
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
		else
		{
			return ("The add message command must be followed by either no words or 2 words, the names of the source and destination nodes.\n");
		}
	}
}

class removeNodeCommand extends ParameterCommand
{
	public boolean isTrue(String[] w)
	{
		this.words = w;
		return words.length >= 2 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			dialog.open(DialogManagerGUI.REMOVE_NODE);
			return "";
		}
		else if(words.length != 3)
		{
			return ("The remove node command must be followed by either no words or 1 word, the name of the node.\n");
		}
		else
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
}

class removeConnectionCommand extends ParameterCommand 
{
	public boolean isTrue(String[] w)
	{
		this.words = w;
		return words.length >= 2 && words[0].equalsIgnoreCase("remove") && words[1].equalsIgnoreCase("connection");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			dialog.open(DialogManagerGUI.REMOVE_CONNECTION);
			return "";
		}
		else if(words.length != 4)
		{
			return ("The add connection command must be followed by either no words or 2 words, the names of the nodes the connection connects.\n");
		}
		else
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
}

class viewNodeCommand extends ParameterCommand 
{
	public boolean isTrue(String[] w)
	{
		this.words = w;
		return words.length <= 2 && words[0].equalsIgnoreCase("view") && words[1].equalsIgnoreCase("node");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		if(words.length == 2)
		{
			dialog.open(DialogManagerGUI.VIEW_NODE);
			return "";
		}
		else if(words.length != 3)
		{
			return ("The view node command must be followed by either no words or 1 word, the name of the node.\n");
		}
		else
		{
			Node n = sim.getNodeByName(words[2]);
			if(n == null) return ("The node named does not exist.\n");
			else
			{
				return (n.getDetails() + "\n");
			}
		}
	}
}

class viewAllCommand implements Command 
{
	public boolean isTrue(String[] words)
	{
		return words.length == 2 && words[0].equalsIgnoreCase("view") && words[1].equalsIgnoreCase("all");
	}
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
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
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
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
	
	public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas)
	{
		return "Invalid command, for all commands use 'Help'.\n";
	}
}