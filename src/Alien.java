import java.awt.*;

public class Alien extends Sprite2D{

	private static double xSpeed = 0;
	public boolean dead = false;
	
	
	public Alien(Image i, Image j) {
		super(i,j);
		
	}
	
	public boolean move() {
		
		
		x+=xSpeed;
		
		if (x<=0 && x>=-100 || x>=winWidth-myImage.getWidth(null))
			return true;
		else
			return false;
	}
	
	
	
	public static void reversalDirection() {
		xSpeed=-xSpeed;
	}
	
	public static void setFleetXSpeed(double dx) {
		xSpeed = dx;
	}
	
	public void jumpDownwards() {
	
		y+=20;
	}
	
	public void setDead(boolean d) {
		dead = d;
	}
	
	@Override
	public void paint(Graphics g) {
		if (!dead) {
		framesDrawn++;
		if(framesDrawn%100<50)
			g.drawImage(myImage, (int)x, (int)y, null);
		else
			g.drawImage(myImage2, (int)x, (int)y, null);
	}
	}
}
