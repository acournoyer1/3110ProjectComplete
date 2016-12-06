*********************************************************
*	______ _____  ___ ______  ___  ___ _____ 	*
*	| ___ \  ___|/ _ \|  _  \ |  \/  ||  ___|	*
*	| |_/ / |__ / /_\ \ | | | | .  . || |__  	*
*	|    /|  __||  _  | | | | | |\/| ||  __| 	*
*	| |\ \| |___| | | | |/ /  | |  | || |___ 	*
*	\_| \_\____/\_| |_/___/   \_|  |_/\____/ 	*
*						        *
*********************************************************

                       


SYSC 3110 MILESTONE 

Ryan Ha				[100975926]
Adam Staples			[100978589]
Alex Cournoyer			[100964534]
Daman Singh			[100965225]


12/05/2016


-----------------------------------------------------------
TEAM LOGISTICS

	Adam Staples
		- Implemented Fourth algorithm, Dijkstras Algorithm
		- Readme updates
		- Added undo to Dijkstra's and Random algorithms, undo for Flood and Shortest Path algorithm partially implemented 
		
	Alex Cournoyer
		- XML importing and exporting, opening and saving
		- Refactored the Command portion of the code further
		- UML Diagrams
		- Changed the GUI to a Tabbed pane with a simulation per tab 
	
	Daman Singh
		-Updated Javadoc for the GUI
		-Refactored the parser and separated out commands

	Ryan Ha
		- Fixed Bread-First Search algorithm
		- Implemented export as image
		- Updated section of UML

-----------------------------------------------------------
CONTENTS OF SUBMITTED MILESTONE 4:

	- Runable .jar file of program, including source code
	- UML of program, showing all classes and their interactions.
	- This readme

------------------------------------------------------------
INSTRUCTIONS TO USE PROGRAM:
	
	1.	Run .jar file located in submitted file.
	2. 	Once GUI has appeared, press "Add" on the menu bar and select "Create Test Network". *
	3. 	To change the method of routing through the network select "Simulation" and go to "Set Type"
		and then select one of the options.
	4.	Once a network exists, the run button can be used to run simulations with random messages
		or alternatively, the Randomly Generated Messages checkbox can be unselected in the
		simulation menu. Steps 4 through 6 deal with this alternative.
	5.	New messages can be added to the simulation by pressing "Add" on the menu bar and selecting
		"Add Message". **
	6.	Now that new message has been created, click the "Step" button at the bottom of the GUI
		to move the message from node to node until it reaches it's destination.
	7.	A new message can now be created, or the network reset and a new one created.
	8.	To quit simply X off the program.
	
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
		
	SimulationListener.java
		
		Interface to update the GUI  
		
	SimulationType.java
		
		Strategy enums
	
Package algorithms

	DijkstrasAlgorithm.java
	
		Method to go through a the table using Dijkstra's algorithm
		
	FloodAlgorithm.java
	
		Method to go through a the table using flood algorithm
		
	RandomAlgorithm.java
	
		Method to go through a the table randomly going to an adjacent node
		
	ShortestPathAlgorithm.java
	
		Method to go through a the table using breadth search finding the shortest pattern.
		
	SimulationAlgorithm.java
	
		Interface for all strategies
		
	
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
	
	Command.java
	
		Interface of and all of the command methods for command line.
		
	CanvasState.java
	
		ENUMS
		
	ExportImageGUI.java
		Prompts user for an image location/file type to save.

	GuiTab.java
		One tab of the GUI.

	XMLFileChooser.java
		Allows importing and exporting of specific XML files.

	ExportImageGUI.java
		Allows exporting of the canvas as an image.	

Package tests
	
	ConnectionTest.java
	
		Unit test class designed to test the Connection class methods
		
	messageTest.java
	
		Unit test class designed to test the sMessage class methods
	
	NodeTest.java
	
		Unit test class designed to test the Node class methods
	
	SimulationTest.java
		
		Unit test class designed to test the Simulation class methods
	
------------------------------------------------------------
