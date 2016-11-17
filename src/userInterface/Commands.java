package userInterface;

import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;


interface Commands{
	
	
	public boolean isTrue(String[] words);
    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas);
	
	
}


class helpCommand implements Commands {
    public boolean isTrue(String[] words){
        
    	return words[0].equalsIgnoreCase("help");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    	
    	return ("The commands in this program are:\n"
				+ "\tHELP\n"
				+ "\t\tShows all available commands.\n"
				//+ "\tADD NODE\n"
				//+ "\t\tOpens the window to add nodes.\n"
				+ "\tADD NODE name\n"
				+ "\t\tAdds a new node with the provided name.\n"
				+ "\tADD CONNECTION\n"
				+ "\t\tOpens the window to add connections.\n"
				+ "\tADD CONNECTION node1 node2\n"
				+ "\t\tAdds a connection between the two named nodes.\n"
				+ "\tADD MESSAGE\n"
				+ "\t\tOpens the window to add messages.\n"
				+ "\tADD MESSAGE source destination\n"
				+ "\t\tCreates a new message that will travel between the source and destination nodes.\n"
				+ "\tREMOVE NODE\n"
				+ "\t\tOpens the window to remove nodes.\n"
				+ "\tREMOVE NODE node\n"
				+ "\t\tRemoves the named node and all its connections\n"
				+ "\tREMOVE CONNECTION\n"
				+ "\t\tOpens the window to remove connections.\n"
				+ "\tREMOVE CONNECTION node1 node2\n"
				+ "\t\tRemoves the connection between the two named nodes.\n"
				+ "\tVIEW NODE\n"
				+ "\t\tDisplays all information about a node.\n"
				+ "\tVIEW ALL\n"
				+ "\t\tDisplay information about all nodes.\n"
				+ "\tCLEAR\n"
				+ "\t\tClears the simulation."
				+ "\tAVERAGE\n"
				+ "\t\tDisplays the average number of jumps a message had to take before reaching it's destination.\n"
				+ "*** All commands are not case sensitive ***\n");
    }
}

class avgCommand implements Commands {
    
	public boolean isTrue(String[] words){
	
    	return words[0].equalsIgnoreCase("average");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    return 	"The average number of jumps messages have taken is " + sim.average() + ".\n";
    	
    }
}


class testCommand implements Commands {
    
	public boolean isTrue(String[] words){
        
    	return words[0].equalsIgnoreCase("test");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	sim.buildTest();
		
    return "The test network has been built.\n";
    }
}

class clearCommand implements Commands {
    
	public boolean isTrue(String[] words){
        
    	return words[0].equalsIgnoreCase("clear");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	sim.clear();
		
    return "Simulation cleared.\n";
    }
}

class addCommand implements Commands {
    
	private String[] words;
	
	public boolean isTrue(String[] w){
        this.words = w;
		
        return words[0].equalsIgnoreCase("add");
		
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    	if(words[1].equalsIgnoreCase("node"))
		{
			if(words.length == 2)
			{
				dialog.open("Add Node");
			}
			else if(words.length != 3)
			{
				return ("The add node command must be followed by either no words or 1 word, the name of the node.\n");
			}
			else
			{
				Node n = sim.getNodeByName(words[2]);
				if(n == null)
				{
					canvas.clearSelection();
					canvas.setTempNode(new Node(words[2]));
					canvas.setState(CanvasState.ADDNODE);
					sim.addNode(new Node(words[2]));
					return ("Node " + words[2] + " has been added.\n");
				}
				else
				{
					return ("A node with this name already exists.\n");
				}
			}
		
    
    }else if(words[1].equalsIgnoreCase("connection"))
	{
		if(words.length == 2)
		{
			canvas.clearSelection();
			canvas.setState(CanvasState.ADDCONNECTION);
			//dialog.open("Add Connection");
			return ("Select the first node.\n");
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
				if(n1 == null) return ("The first node named does not exist.\n");
				if(n2 == null) return ("The second node named does not exist.\n");
			}
			else
			{
				sim.addConnection(n1, n2);
				return ("Connection " + words[2] + " < - > " + words[3] + " has been added.\n");
			}
		}
	}
	else if(words[1].equalsIgnoreCase("message"))
	{
		if(words.length == 2)
		{
			dialog.open("Add Message");
		}
		else if(words.length != 4)
		{
			return ("The add message command must be followed by either no words or 2 words, the names of the source and destination nodes.\n");
		}
		else
		{
			Node n1 = sim.getNodeByName(words[2]);
			Node n2 = sim.getNodeByName(words[3]);
			if(n1 == null || n2 == null)
			{
				if(n1 == null) return ("The first node named does not exist.\n");
				if(n2 == null) return ("The second node named does not exist.\n");
			}
			else
			{
				Message msg = new Message(n1, n2);
				sim.addMsg(msg);
				return ("Message " + msg.getId() + " : " + words[2] + " - > " + words[3] + " has been added.\n");
				
			}
		}
	}
	
		
    	return ("Invalid comand, for all commands type 'help'.\n");
		
    	
}
}

class removeCommand implements Commands {
    
	private String[] words;
	
	public boolean isTrue(String[] words){
		 
		this.words = words;
		 
    	return  words[0].equalsIgnoreCase("remove");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    	if(words[1].equalsIgnoreCase("node"))
		{
			if(words.length == 2)
			{
				dialog.open("Remove Node");
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
		else if(words[1].equalsIgnoreCase("connection"))
		{
			if(words.length == 2)
			{
				dialog.open("Remove Connection");
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
					if(n1 == null) return ("The first node named does not exist.\n");
					if(n2 == null) return ("The second node named does not exist.\n");
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
		
			return ("Invalid comand, for all commands type 'help'.\n");
		
    }
}



class viewCommand implements Commands {
    
	private String[] words;
	
	public boolean isTrue(String[] words){
       
		this.words = words;
		 
    	return words[0].equalsIgnoreCase("view");
    
    	
    }

    public String runCommand(Simulation sim, DialogManagerGUI dialog, GraphicsCanvasGUI canvas){
    	
    	
    	if(words[1].equalsIgnoreCase("node"))
		{
			if(words.length == 2)
			{
				dialog.open("View Node");
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
		else if(words[1].equalsIgnoreCase("all"))
		{
			if(words.length != 2)
			{
				return ("The view all command should not be followed by more parameters.\n");
			}
			
			String temp = "";
			
				for(Node n: sim.getNodes())
				{
					temp += (n.getDetails() + "\n");
				}
				
				return temp;
			
		}
    	
    	return null;
    }
}