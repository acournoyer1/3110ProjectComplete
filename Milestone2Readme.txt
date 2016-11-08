*********************************************************
*	______ _____  ___ ______  ___  ___ _____ 	*
*	| ___ \  ___|/ _ \|  _  \ |  \/  ||  ___|	*
*	| |_/ / |__ / /_\ \ | | | | .  . || |__  	*
*	|    /|  __||  _  | | | | | |\/| ||  __| 	*
*	| |\ \| |___| | | | |/ /  | |  | || |___ 	*
*	\_| \_\____/\_| |_/___/   \_|  |_/\____/ 	*
*												*
*********************************************************

                       


SYSC 3110 MILESTONE 

Ryan Ha				[100975926]
Adam Staples			[100978589]
Alex Cournoyer			[100964534]
Daman Singh			[100965225]


21/10/2016


-----------------------------------------------------------
TEAM LOGISTICS

	Adam Staples
		- 
		
	Alex Cournoyer
		- Implemented new GraphicsCanvas class
		- Split the GUI class from its nested classes in iteration 1 into a few distinct classes 
		  to assure each class has only one responsibility
	
	Daman Singh
		-

	Ryan Ha
		- Implemented the flood algorithm
		- Added JavaDoc
		- UML Diagrams

-----------------------------------------------------------
CONTENTS OF SUBMITTED MILESTONE 2:

	- Runable .jar file of program, including source code
		This .jar file meets the requirements of milestone 1 and has gone further to implement
		a GUI also. The only thing not desirable currently is the fact that a message can be sent
		from itself to itself.
	- UML of program, showing all classes and their interactions.
	- This readme

------------------------------------------------------------
INSTRUCTIONS TO USE PROGRAM:
	
	1.	Run .jar file located in submitted file.
	2. 	Once GUI has appeared, press "Add" on the menu bar and select "Create Test Network". *
	3.	Once a network exists, the run button can be used to run simulations with random messages
		or alternatively, the Randomly Generated Messages checkbox can be unselected in the
		simulation menu. Steps 4 through 6 deal with this alternative.
	4.	New messages can be added to the simulation by pressing "Add" on the menu bar and selecting
		"Add Message". **
	5.	Now that new message has been created, click the "Step" button at the bottom of the GUI
		to move the message from node to node until it reaches it's destination.
	6.	A new message can now be created, or the network reset and a new one created.
	7.	To quit simply X off the program.
	
	*This will create a network with nodes and connections identical to the example given in the 
	project description PDF. You can use the other options in the "Add" menu to create your own
	map with its own connections.*
	
	**A message being sent from a node to the exact same node will be sent from
	the node to a neighbouring node, continuing on until it comes back to itself.**
	
	Note: Commands can also be run from a command prompt, opened by either going into 
		  View -> Toolbars -> Command Prompt, or using the Ctrl-Enter accelerator. All 
		  useable commands can be displayed with the command 'Help' (not case sensitive).
	

------------------------------------------------------------
SOURCE FILE INFORMATION:

Package backEnd

	Message.java

		Message Class that creates a message to be sent from a node to another node.

	Simulation.java

		Simulation class designated to manage the step process of walking through the simulation.
		Manages the ability to manipulate node connections.
	
	Connection.java

		Connection class defines the properties to connect two nodes.

	Node.java

		Node class that holds the properties of every node that will be manipulated. 

	SimulationType.java

		SimulationType enum defines what mode the user has currently selected.
		
	SimulationListener.java
	
Package userInterface
	
	CommandParser.java
		
		Parser that parses commands given to the program's command prompt.
	
	DialogManager.java
	
		Manages all Dialog Menus that can be opened by the program.
	
	GraphicsCanvas.java
	
		Displays the network of nodes on a canvas and shows simulations as they progress.
	
	GUI.java

		GUI class that builds all of the gui's properties.
		
	NodeImage.java
	
		Holds the information necessary to draw a Node on the canvas.
	
	PolarPoint.java
	
		Polar Point representation used in auto generating node placements.
	
------------------------------------------------------------
