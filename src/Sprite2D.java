import java.awt.*;

public class Sprite2D {
	protected double x,y;
	protected double xSpeed = 0;
	protected Image myImage;
	protected Image myImage2;
	protected int framesDrawn = 0;
	
	protected static int winWidth;
	
	public Sprite2D(Image i, Image j) {
		
		myImage = i;
		myImage2 = j;
	}
	
	public void setPosition(double xx, double yy) {
		x = xx;
		y = yy;
	}
	
	public void setXSpeed(double dx) {
		xSpeed = dx;
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, (int)x, (int)y, null);
	}
	
	public static void setWinWidth(int w) {
		winWidth = w;
	}
}
