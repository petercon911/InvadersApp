import java.awt.*;

public class PlayerBullet extends Sprite2D {

	private static double xSpeed = 0;
	
	
	public PlayerBullet (Image i, Image j) {
		super(i,j);
	}
	
	public void move() {
		y-=xSpeed;
	}
	
	public static void setBulletXSpeed(double dx) {
		xSpeed=dx;
	}
}
