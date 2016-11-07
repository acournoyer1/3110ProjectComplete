import java.awt.Point;

public class PolarPoint {
	private double radius;
	private double theta;
	
	public PolarPoint(double radius, double theta)
	{
		this.radius = radius;
		this.theta = theta;
	}
	
	public Point convertToCartesian()
	{
		int x = (int)(radius * Math.cos(theta));
		int y = (int)(radius * Math.sin(theta));
		return new Point(x, y);
	}
	
	public String toString()
	{
		return "Radius: " + radius + ", Theta: " + theta; 
	}
}
