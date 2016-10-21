*********************************************************
*	______ _____  ___ ______  ___  ___ _____ 	*
*	| ___ \  ___|/ _ \|  _  \ |  \/  ||  ___|	*
*	| |_/ / |__ / /_\ \ | | | | .  . || |__  	*
*	|    /|  __||  _  | | | | | |\/| ||  __| 	*
*	| |\ \| |___| | | | |/ /  | |  | || |___ 	*
*	\_| \_\____/\_| |_/___/   \_|  |_/\____/ 	*
*							*
*********************************************************

                       


SYSC 3110 MILESTONE 1

Adam Staples			[100978589]
Alex Cournoyer			[100964534]
Daman Singh			[100965225]
Ryan Ha				[100975926]

21/10/2016


-----------------------------------------------------------
TEAM LOGISTICS

	Adam Staples
		- UML
		- Readme
		
	Alex Cournoyer
		- Created GUI
	
	Daman Singh
		- Created Node class
		- Created Message class

	Ryan Ha
		- Created Simulation
		- Implemented Simulation

-----------------------------------------------------------
CONTENTS OF SUBMITTED MILESTONE 1:

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
	3.	Once again select the "Add" menu and create a new message. **
	4.	Now that new message has been created, click the "Step" button at the bottom of the GUI
		to move the message from node to node until it reaches it's destination.
	5.	A new message can now be created, or the network reset and a new one created.
	6.	To quit simply X off the program.
	
	*This will create a network with nodes and connections identical to the example given in the 
	project description PDF. You can use the other options in the "Add" menu to create your own
	map with its own connections.*
	
	**A message being sent from a node to the exact same node will be sent from
	the node to a neighbouring node, continuing on until it comes back to itself.**
	

------------------------------------------------------------
SOURCE FILE INFORMATION:

Message.java

	Message Class that creates a message to be sent from a node to another node.

Simulation.java

	Simulation class designated to manage the step process of walking through the simulation.
	Manages the ability to manipulate node connections.
	
Connection.java

	Connection class defines the properties to connect two nodes.

Node.java

	Node class that holds the properties of every node that will be manipulated. 
	
GUI.java

	GUI class that builds all of the gui's properties.

SimulationType.java

	SimulationType enum defines what mode the user has currently selected.
	
------------------------------------------------------------
