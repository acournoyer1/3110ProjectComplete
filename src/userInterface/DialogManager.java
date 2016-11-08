package userInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import backEnd.Connection;
import backEnd.Message;
import backEnd.Node;
import backEnd.Simulation;

public class DialogManager {
	private Simulation sim;
	private JTextArea statusWindow;
	
	public DialogManager(Simulation sim, JTextArea statusWindow)
	{
		this.sim = sim;
		this.statusWindow = statusWindow;
	}
	
	public void open(String s)
	{
		if(s.equals("Add Node")) new AddNode();
		else if(s.equals("Add Connection")) new AddConnection();
		else if(s.equals("Add Message")) new AddMessage();
		else if(s.equals("Remove Node")) new RemoveNode();
		else if(s.equals("Remove Connection")) new RemoveConnection();
		else if(s.equals("View Node")) new ViewNode();
		else if(s.equals("Set Rate")) new SetRate();
		else if(s.equals("Set Length")) new SetLength();
	}
	
	/*
	 *  Nested class that builds the GUI for the addNode button
	 *  and sets up listeners for addNode GUI layout
	 */
	@SuppressWarnings("serial")
	private class AddNode extends JDialog
	{
		private JTextField nameField;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddNode()
		{
			JLabel label = new JLabel("Name: ");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			JSplitPane split = new JSplitPane();
			JSplitPane top = new JSplitPane();
			JPanel bottom = new JPanel();
			nameField = new JTextField();
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			bottom.add(addButton);
			bottom.add(cancelButton);
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setEnabled(false);
			split.setDividerSize(1);
			top.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			top.setLeftComponent(label);
			top.setRightComponent(nameField);
			top.setEnabled(false);
			top.setDividerSize(1);
			this.add(split);
			this.setSize(200,100);
			this.setResizable(false);
			this.setTitle("Add Node");
			split.setDividerLocation((int)(this.getHeight()*0.35));
			top.setDividerLocation((int)(this.getWidth()*0.35));
			this.setLocationRelativeTo(null);
			setUpListeners();
			addButton.setEnabled(false);
			this.setVisible(true);
		}
		
		private void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dispose();	
				}			
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					Node n = sim.getNodeByName(nameField.getText());
					if(n == null)
					{
						sim.addNode(new Node(nameField.getText()));
						statusWindow.append("Node " + nameField.getText() + " has been added.\n");
					}
					else
					{
						statusWindow.append("A node with this name already exists.\n");
					}
					dispose();
				}		
			});
			nameField.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(!(e.getKeyCode() == KeyEvent.VK_ENTER))
					{
						addButton.setEnabled(true);
					}
					else if(e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						addButton.doClick();
					}
				}
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the addConnection button
	 *  and sets up listeners for addConnection GUI layout
	 */
	@SuppressWarnings("serial")
	private class AddConnection extends JDialog
	{
		private JComboBox<Node> firstNode;
		private JComboBox<Node> secondNode;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddConnection()
		{
			firstNode = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			secondNode = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Add Connection");
			JPanel top = new JPanel();
			top.add(firstNode);
			top.add(secondNode);
			JPanel bottom = new JPanel();
			bottom.add(addButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.addConnection((Node)firstNode.getSelectedItem(), (Node)secondNode.getSelectedItem());
					statusWindow.append("Connection " + ((Node)firstNode.getSelectedItem()).getName() + " < - > " + ((Node)secondNode.getSelectedItem()).getName() + " has been added.\n");
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the addMessage button
	 *  and sets up listeners for addMessage GUI layout
	 */
	@SuppressWarnings("serial")
	private class AddMessage extends JDialog
	{
		private JComboBox<Node> source;
		private JComboBox<Node> destination;
		private JButton addButton;
		private JButton cancelButton;
		
		public AddMessage()
		{
			source = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			destination = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			addButton = new JButton("Add");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Add Connection");
			JPanel top = new JPanel();
			top.add(new JLabel("Source: "));
			top.add(source);
			top.add(new JLabel("Destination: "));
			top.add(destination);
			JPanel bottom = new JPanel();
			bottom.add(addButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(300, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			addButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{	
					Message msg = new Message((Node)source.getSelectedItem(), (Node)destination.getSelectedItem());
					sim.addMsg(msg);
					statusWindow.append("Message " + msg.getId() + ": " + ((Node)source.getSelectedItem()).getName() + " - > " + ((Node)destination.getSelectedItem()).getName() + " has been added.\n");
					dispose();
				}		
			});
			//Possibly implement enter shortcut
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the removeNode button
	 *  and sets up listeners for removeNode GUI layout
	 */
	@SuppressWarnings("serial")
	private class RemoveNode extends JDialog
	{
		private JComboBox<Node> node;
		private JButton removeButton;
		private JButton cancelButton;
		
		public RemoveNode()
		{
			node = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			removeButton = new JButton("Remove");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Remove Connection");
			JPanel top = new JPanel();
			top.add(node);
			JPanel bottom = new JPanel();
			bottom.add(removeButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			removeButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.removeNode((Node)node.getSelectedItem());
					statusWindow.append("Node " + ((Node)node.getSelectedItem()).getName() + " has been removed.\n");
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the removeConnection button
	 *  and sets up listeners for removeConnection GUI layout
	 */
	@SuppressWarnings("serial")
	private class RemoveConnection extends JDialog
	{
		private JComboBox<Connection> connection;
		private JButton removeButton;
		private JButton cancelButton;
		
		public RemoveConnection()
		{
			connection = new JComboBox<Connection>(sim.getConnections().toArray(new Connection[sim.getConnections().size()]));
			removeButton = new JButton("Remove");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("Remove Connection");
			JPanel top = new JPanel();
			top.add(connection);
			JPanel bottom = new JPanel();
			bottom.add(removeButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			removeButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					sim.removeConnection((Connection)connection.getSelectedItem());
					statusWindow.append("Connection " + ((Connection)connection.getSelectedItem()).toString() + " has been removed\n.");
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the viewNode button
	 *  and sets up listeners for viewNode GUI layout
	 */
	@SuppressWarnings("serial")
	private class ViewNode extends JDialog
	{
		private JComboBox<Node> node;
		private JButton viewButton;
		private JButton cancelButton;
		
		public ViewNode()
		{
			node = new JComboBox<Node>(sim.getNodes().toArray(new Node[sim.getNodes().size()]));
			viewButton = new JButton("View");
			cancelButton = new JButton("Cancel");
			
			this.setTitle("View Node");
			JPanel top = new JPanel();
			top.add(node);
			JPanel bottom = new JPanel();
			bottom.add(viewButton);
			bottom.add(cancelButton);
			JSplitPane split = new JSplitPane();
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setDividerSize(1);
			this.setSize(200, 100);
			this.setResizable(false);
			split.setDividerLocation((int)(this.getHeight()*0.35));
			this.add(split);
			this.setLocationRelativeTo(null);
			setUpListeners();
			this.setVisible(true);
		}
		
		public void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					dispose();
				}
			});
			viewButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					statusWindow.append(((Node)node.getSelectedItem()).getDetails() + "\n");
					dispose();
				}		
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the setRate button
	 *  and sets up listeners for SetRate GUI layout
	 */
	@SuppressWarnings("serial")
	private class SetRate extends JDialog
	{
		private JTextField rateField;
		private JButton okButton;
		private JButton cancelButton;
		
		public SetRate()
		{
			JLabel label = new JLabel("Rate: ");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			JSplitPane split = new JSplitPane();
			JSplitPane top = new JSplitPane();
			JPanel bottom = new JPanel();
			rateField = new JTextField();
			okButton = new JButton("OK");
			cancelButton = new JButton("Cancel");
			bottom.add(okButton);
			bottom.add(cancelButton);
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setEnabled(false);
			split.setDividerSize(1);
			top.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			top.setLeftComponent(label);
			top.setRightComponent(rateField);
			top.setEnabled(false);
			top.setDividerSize(1);
			this.add(split);
			this.setSize(200,100);
			this.setResizable(false);
			this.setTitle("Set Rate");
			split.setDividerLocation((int)(this.getHeight()*0.35));
			top.setDividerLocation((int)(this.getWidth()*0.35));
			this.setLocationRelativeTo(null);
			setUpListeners();
			okButton.setEnabled(false);
			this.setVisible(true);
		}
		
		private void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dispose();	
				}			
			});
			okButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(!rateField.getText().equals(""))
					{
						int rate = Integer.parseInt(rateField.getText());
						sim.setRate(rate);
						statusWindow.append("Simulation rate changed to " + rate + ".\n");
						dispose();
					}
				}		
			});
			rateField.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(!(e.getKeyCode() == KeyEvent.VK_ENTER))
					{
						okButton.setEnabled(true);
					}
					else if(e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						okButton.doClick();
					}
				}
			});
		}
	}
	
	/*
	 *  Nested class that builds the GUI for the setRate button
	 *  and sets up listeners for SetRate GUI layout
	 */
	@SuppressWarnings("serial")
	private class SetLength extends JDialog
	{
		private JTextField lengthField;
		private JButton okButton;
		private JButton cancelButton;
		
		public SetLength()
		{
			JLabel label = new JLabel("Length: ");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			JSplitPane split = new JSplitPane();
			JSplitPane top = new JSplitPane();
			JPanel bottom = new JPanel();
			lengthField = new JTextField();
			okButton = new JButton("OK");
			cancelButton = new JButton("Cancel");
			bottom.add(okButton);
			bottom.add(cancelButton);
			split.setOrientation(JSplitPane.VERTICAL_SPLIT);
			split.setTopComponent(top);
			split.setBottomComponent(bottom);
			split.setEnabled(false);
			split.setDividerSize(1);
			top.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			top.setLeftComponent(label);
			top.setRightComponent(lengthField);
			top.setEnabled(false);
			top.setDividerSize(1);
			this.add(split);
			this.setSize(200,100);
			this.setResizable(false);
			this.setTitle("Set Length");
			split.setDividerLocation((int)(this.getHeight()*0.35));
			top.setDividerLocation((int)(this.getWidth()*0.35));
			this.setLocationRelativeTo(null);
			setUpListeners();
			okButton.setEnabled(false);
			this.setVisible(true);
		}
		
		private void setUpListeners()
		{
			cancelButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dispose();	
				}			
			});
			okButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(!lengthField.getText().equals(""))
					{
						int length = Integer.parseInt(lengthField.getText());
						sim.setLength(length);
						statusWindow.append("Simulation length changed to " + length + ".\n");
						dispose();
					}
				}		
			});
			lengthField.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if(!(e.getKeyCode() == KeyEvent.VK_ENTER))
					{
						okButton.setEnabled(true);
					}
					else if(e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						okButton.doClick();
					}
				}
			});
		}
	}
	
}
