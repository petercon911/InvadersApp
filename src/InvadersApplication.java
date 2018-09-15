import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;


public class InvadersApplication extends JFrame implements Runnable, KeyListener{
	
	private static boolean isGraphicsInit = false;
	private static final Dimension WindowSize = new Dimension(800,600);
	private BufferStrategy strategy;
	private Graphics offscreenGraphics;
	private static final int NUMALIENS = 30;
	private Alien[] AliensArray = new Alien[NUMALIENS];
	private Spaceship PlayerShip;
	private Image bulletImage;
	private ArrayList bulletList = new ArrayList();
	private ImageIcon icon;
	private Image death;
	private boolean isGameInProgress = false;
	private boolean isGameOver = false;
	private Image startScreen;
	private Image gameOver;
	private int score = 0; // counter
	private int best = 0;// counter
	private String prScore = "score: 0";// paint this string
	private String prBest = "best: 0";// paint
	private Image alienImage2;// so i can access it in new game
	private Image alienImage;// ""
	private int deaths = 0;
	private int speed = 2;// for access in extra stage
	
	public InvadersApplication() {
		
		this.setTitle("Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x,y, WindowSize.width, WindowSize.height);
		setVisible(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		icon = new ImageIcon("C:\\work\\images\\alien_ship_1.png");
		alienImage = icon.getImage();
		
		ImageIcon icon2 = new ImageIcon("C:\\work\\images\\alien_ship_2.png");
		alienImage2 = icon2.getImage();
		
		newGame();
		
		
		icon = new ImageIcon("C:\\work\\images\\player_ship.png");
		Image shipImage = icon.getImage();
		PlayerShip = new Spaceship(shipImage, shipImage);
		PlayerShip.setPosition(300,530);
		
		icon = new ImageIcon("C:\\work\\images\\bullet.png");
		bulletImage = icon.getImage();
		
		icon = new ImageIcon("C:\\work\\images\\death.png");
		death = icon.getImage();
		
		Sprite2D.setWinWidth(WindowSize.width);
		
		Thread t = new Thread(this);
		t.start();
		
		addKeyListener(this);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		offscreenGraphics = strategy.getDrawGraphics();
		
		icon = new ImageIcon("C:\\work\\images\\startScreen.png");
		startScreen = icon.getImage();
		
		icon = new ImageIcon("C:\\work\\images\\gameOver.png");
		gameOver = icon.getImage();
		
		isGraphicsInit = true;
		
	}
	
	public void run() {
		while(1==1) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
			
			boolean alienDirectionReversalNeeded = false;
			for (int i = 0; i<NUMALIENS; i++) {
				if (AliensArray[i].move()) {
					alienDirectionReversalNeeded = true;
				}
			}
			if(alienDirectionReversalNeeded) {
				Alien.reversalDirection();
				for (int j=0; j<NUMALIENS; j++)
					AliensArray[j].jumpDownwards();
			}
			
			Iterator iterator = bulletList.iterator();
			while(iterator.hasNext()) {
			
				PlayerBullet b = (PlayerBullet) iterator.next();
				b.move();
				
				for(int i = 0; i<NUMALIENS; i++) {
					if((AliensArray[i].x<b.x && AliensArray[i].x+54>b.x) || (b.x<AliensArray[i].x && b.x+6>AliensArray[i].x)) {
						if ((AliensArray[i].y<b.y && AliensArray[i].y+32>b.y) || (b.y<AliensArray[i].y && b.y+16>AliensArray[i].y)) {
							
							AliensArray[i].x = -1000; // throw it off screen
							
							iterator.remove(); 
							score += 20;
							deaths++;// death counter to indicate new stage
						}
					}
					
				
			}
			}
			for(int i = 0; i<NUMALIENS; i++) {
			if((AliensArray[i].x<PlayerShip.x && AliensArray[i].x+54>PlayerShip.x) || (PlayerShip.x<AliensArray[i].x && PlayerShip.x+6>AliensArray[i].x)) {
				if ((AliensArray[i].y<PlayerShip.y && AliensArray[i].y+32>PlayerShip.y) || (PlayerShip.y<AliensArray[i].y && PlayerShip.y+16>AliensArray[i].y))
				{
						isGameOver = true; // paints game over
						newGame(); // calls a new set of space invaders
						deaths = 0;
				}		
			}
			}
			
			if(deaths==NUMALIENS) {
				deaths = 0;// resets death counter 
				extraStage();// calls new space invades and increments speed
			}
			String s = Integer.toString(score);
			prScore = "score: "+ s;
			if (score > best) {
				best = score;
				prBest = "best: " + best;
			}
									
			PlayerShip.move();
			
			
			
			this.repaint();
		
		}
	}
	
	public void newGame() {
		score = 0;
		speed = 2;
		for (int i = 0; i < NUMALIENS; i++) {
			
			AliensArray[i] = new Alien(alienImage,alienImage2);
			double xx = (i%5)*80 + 70;
			double yy = (i/5)*60 + 50;
			AliensArray[i].setPosition(xx, yy);
			
		}
		
		Alien.setFleetXSpeed(speed); 
		PlayerBullet.setBulletXSpeed(speed);
	}
	
	public void extraStage(){
		speed+=2;
		for (int i = 0; i < NUMALIENS; i++) {
			
			AliensArray[i] = new Alien(alienImage,alienImage2);
			double xx = (i%5)*80 + 70;
			double yy = (i/5)*60 + 50;
			AliensArray[i].setPosition(xx, yy);
			
		}
		
		Alien.setFleetXSpeed(speed); 
		PlayerBullet.setBulletXSpeed(speed);
	}
	
	public void shootBullet() {
		PlayerBullet b = new PlayerBullet(bulletImage, bulletImage);
		b.setPosition(PlayerShip.x+54/2,  PlayerShip.y);
		bulletList.add(b);
	}
	
	public void keyPressed(KeyEvent e) {
		if(!isGameInProgress){
			int key= e.getKeyCode();

			if((((key>=65)&&(key<=90)) || ((key>=97)&&(key<=122)) || (key>=48)&&(key<=57)))
			{
			isGameInProgress = true;
			}
		}
		else if(isGameOver) {
			int key= e.getKeyCode();

			if((((key>=65)&&(key<=90)) || ((key>=97)&&(key<=122)) || (key>=48)&&(key<=57)))
			{
			isGameOver = false;
			}
		}
		else {
		if (e.getKeyCode()==KeyEvent.VK_LEFT)
			PlayerShip.setXSpeed(-4);
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT)
			PlayerShip.setXSpeed(+4);
		else if (e.getKeyCode()==KeyEvent.VK_SPACE)
			shootBullet();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT)
			PlayerShip.setXSpeed(0);
	}

	public void keyTyped(KeyEvent e) {}
	
	public void paint(Graphics g) {
		
			
		if (!isGraphicsInit)
			return;
		
		g = offscreenGraphics;
		
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  WindowSize.width, WindowSize.height);
			
		if (!isGameInProgress) {
			g.drawImage(startScreen, 50, 100, null);
		
		}
		
		else if(isGameOver) {
			g.drawImage(gameOver, 50, 100, null);
		}
		
		else {
			
			g.setColor(Color.WHITE);
			g.drawString(prScore, 200, 70);
			g.drawString(prBest, 300, 70);
		for(int i = 0; i<NUMALIENS; i++)
				AliensArray[i].paint(g);
			
		PlayerShip.paint(g);
			
		Iterator iterator = bulletList.iterator();
			while(iterator.hasNext()) {
				PlayerBullet b = (PlayerBullet) iterator.next();
				b.paint(g);
			}
		}	
		strategy.show();
		
	}
	
	public static void main(String[] args) {
		InvadersApplication w = new InvadersApplication();
		
	}

}
