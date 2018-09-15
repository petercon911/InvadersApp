import java.awt.*;

public class Spaceship extends Sprite2D{

	public Spaceship(Image i, Image j) {
		super(i,j);
		
	}
	
	public void setXSpeed(double dx) {
		xSpeed=dx;
	}
	
	public void move() {
		x+=xSpeed;
		
		if(x<=0) {
			x = 0;
			xSpeed = 0;
		}
		else if (x>=winWidth-myImage.getWidth(null)) {
			x = winWidth-myImage.getWidth(null);		
		}
	}
}
