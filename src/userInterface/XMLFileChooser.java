package userInterface;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class XMLFileChooser {
	public static File importXML()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileNameExtensionFilter(".xml", "xml"));	
		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION){
			return chooser.getSelectedFile();
		}
		else
		{
			return null;
		}
	}
	
	public static File exportXML()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileNameExtensionFilter(".xml", "xml"));	
		int option = chooser.showSaveDialog(null);
		if(option == JFileChooser.APPROVE_OPTION){
			if(chooser.getSelectedFile().getAbsolutePath().endsWith(".xml"))
			{
				return chooser.getSelectedFile();
			}
			return new File(chooser.getSelectedFile().getAbsolutePath() + ".xml");
		}
		else
		{
			return null;
		}
	}
}
