package userInterface;

/**
 * ExportGUI class:
 * 
 * Class that will prompt a user with a JFileChooser
 * in order to export the current canvas as a .gif,
 * .jpg, or .png file to the selected directory.
 * 
 * Author: Ryan Ha
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportImageGUI {
	JFileChooser chooser;
	JComponent canvas;
	public ExportImageGUI(JComponent canvas){
		chooser = new JFileChooser();
		this.canvas = canvas;
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
		chooser.setFileFilter(new FileNameExtensionFilter(".gif", "gif"));
		chooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));	
		int option = chooser.showSaveDialog(null);
		if(option == JFileChooser.APPROVE_OPTION){
			CreateImage();
		} 	
	}
	
	/**
	 * CreateImage method:
	 * 
	 * Creates an image file based on the selected
	 * directory, file name, and extension from the
	 * JFileChooser.
	 */
	public void CreateImage(){
		String directory = chooser.getSelectedFile().getPath();
		String extension = chooser.getFileFilter().getDescription();
		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		canvas.printAll(g);
		g.dispose();
		try{
			ImageIO.write(image, extension, new File(directory + "." + extension));
		}
		catch(IOException IOe){
			IOe.printStackTrace();
		}
	}
}
