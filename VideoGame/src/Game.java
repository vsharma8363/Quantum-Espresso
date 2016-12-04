import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Game extends PApplet {
	
	int ellipseR = 0;	
	ArrayList <Projectile> projectiles;
	String windowName;
	PFont myFont;
	ArrayList<Enemy> enemies;
	PImage img;
	PImage survivor;
	PImage img1;
	PImage img2;
	PImage ready;
	PImage nextprop;
	PImage healthbar;
	int playerW = 40, playerH = 40;
	PImage propogator;
	PImage bullet;
	PImage propygun;
	PImage propogationMissile;
	PImage computer;
	PImage title;
	PImage scoreboard;
	PImage bob;
	int time = 0;
	int bulletSpawn = 0;
	int health = 100;
	boolean shooting;
	float x, y, x2, y2, zx, zy;
	float xSpeed = 5;
	float ySpeed = 5;
	float xHealth = 0;
	float totalKilled = 0;
	float yHealth = 540;
	boolean right = false, up = false, down = false, left = false, displayProp = false;
	boolean triggered = false, possible, gameOver = false, game = true, propgun = false;
    public static double [] processing_x;
    public static double [] processing_y;
    int [][] hitMap;
    long oldTime, newTime;
	int inter = 100;
    int totalTake = 0;
    String outputMessage = "";
    String rank = "";
    int play = 0;
    Sound wave, music, gun;
    long scoretime;
    long oldscoretime;
    int quantum = 0;
    int playGame = 0;

    public static void main(String []args)
    {
    	PApplet.main("Game");
    }
    
    public void settings()
    {
		size(900, 600);

    }
    
	public void setup() {
		shooting = false;
		frameRate(30);
		music = new Sound("music.wav");
		gun = new Sound("gun.wav");
		wave = new Sound("wave.wav");
		survivor = loadImage("survivor.png");
		img = loadImage("survivor.png");
		bob = loadImage("bob.png");
		propogationMissile = loadImage("missile.png");
		img1 = loadImage("Background.png");
		img2 = loadImage("termy.png");
		ready = loadImage("propready.png");
		nextprop = loadImage("nextproppy.png");
		propogator = loadImage("propy.png");
		propygun = loadImage("propygun.png");
		title = loadImage("texty.png");
		computer = loadImage("computer.jpg");
		bullet = loadImage("bully.png");
		scoreboard = loadImage("score.png");
		healthbar = loadImage("health.png");
		myFont = createFont("AppleBraille", 30);
		textFont(myFont);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		x = width / 2;
		y = height / 2;
		possible = false;
		oldTime = System.currentTimeMillis();
	}

	public void draw() {
		if(game == true)
		{
			control();
			fill(255, 255, 255);
			rect(-5, -5, width + 10, height+10);
			inter++;
			fill(33, 186, 238);
			textSize(15);
			text("Press 's' to skip", width - 125, height - 25);
			textSize(35);
			image(bob, 10, width/10, width/4, (float)(height/1.5));
			if(inter > 200 && inter < 600)
			{
			text("> Your name is Bob", width/2 - 200, height/2 - 100);
			text("> And you have just been recruited \nas an IT specialist at a tech company", width/2 - 200, height/2);
			}
			//Start Game
			if(inter > 600 && inter < 1000)
			{
				text("> But the company was hiding some \ndark secret", width/2 - 200, height/2 - 100);
				text("> A self-replicating robot went \nloose and killed everyone in the lab", width/2 - 200, height/2 + 100);
			}
			if(inter > 1000 && inter < 1300)
			{
				text("> Your name is Bob", width/2 - 200, height/2 - 100);
				text("> And you have to keep the lab and \nits experiments safe from the \nevil specimens", width/2 - 200, height/2);
			}
			if(inter > 1300 && inter < 1600)
			{
				text("> You need to keep the propogation \nmachine safe!", width/2 - 200, height/2);
			}
			if(inter > 1800)
			{
			game = false;
			gameOver = false;
			}
		}
		else if(gameOver == true)
		{
			control();
			fill(255, 255, 255);
			rect(-5, -5, width + 10, height+10);
			fill(33, 186, 238);
			textSize(15);
			text("Press 'r' to restart", width - 150, height - 25);
			textSize(35);
			image(bob, 10, width/10, width/4, (float)(height/1.5));
			if(totalKilled > 10)
			{
				outputMessage = "You were not able to protect \nthis lab long enough";
				rank = "Failure!";
			}
			if(totalKilled > 20)
			{
				outputMessage = "You showed bravery out there \ntoday but were not able to hold out long enough";
				rank = "Failure";
			}
			if(totalKilled > 30)
			{
				outputMessage = "You almost killed all the \nspecimens but alas, you were outnumbered greatly";
				rank = "Failure";
			}
			if(totalKilled > 40)
			{
				outputMessage = "You died, but so did all the \nspecimens \n> The propogation machine and \nmillions of people were saved";
				rank = "Success";
			}
			else
			{
				outputMessage = "You were consumed by the \nspecimens you were too weak \nfor them";
				rank = "Failure";
			}
			text("> " + rank, width/2 - 200, height/2 - 100);
			text("> " + outputMessage, width/2 - 200, height/2);
		}
		else
		{	
		scoretime = System.currentTimeMillis();
		playMusic();
		int secondscore = (int)((scoretime - oldscoretime)/1000);
		xHealth = width - 220;
		yHealth = height - 30;
		background(255);
		fill(255, 0, 0);
		image(img1, 0, 0, width, height);
		noStroke();
		fill(0);
		//health~~~~~~~~~~~~~~~~~~~~	
		image(healthbar, xHealth - 1, yHealth - 2, 220, 30);
		fill(255, 0, 0);
		rect(xHealth + 74, yHealth + 3, (int)(health*1.390) + 1, 20);
		//health~~~~~~~~~~~~~~~~~~~~
		fill(33, 186, 238);
		textSize(20);
		move();
		fill(33, 186, 238);
		//text("Kills: " + (int)totalKilled,  10, height - 60);
		drawPropogator();
		noStroke();
		time += 1;
		addEnemies();
		spawnEnemies();
		newTime = System.currentTimeMillis();
		double seconds = (newTime - oldTime)/1000;
		int sup = (int) (30 - seconds);
		if(sup <= 0)
		{
			quantum++;
			if(quantum < 100)
				image(ready , width/2 - 412, -30, 825, 300);
			displayProp = true;
			image(nextprop, 0, height-30, 220, 30);
			fill(33, 186, 238);
			text("0",  185, yHealth + 22);
			possible = true;
		}
		else
		{
			image(nextprop, 0, height-30, 220, 30);
			fill(33, 186, 238);
			text(sup, 185, yHealth + 22);
		}
		if(triggered == true)
		{
			if(possible == true)
			{
			float CenterX = x + playerW/2;
			float CenterY = y + playerH/2;
			float propogatorX = width/2;
			float propogatorY = height/2;
			float distance = (float)Math.sqrt((CenterX-propogatorX)*(CenterX-propogatorX) + (CenterY-propogatorY)*(CenterY-propogatorY));
			if(distance < 56)
			{
				quantum = 0;
				img = propygun;
				propgun = true;
				displayProp = true;
			}
			else
			{
				if(displayProp)
				{
				fill(0, 0, 255);
				text("Get closer to the propogator!",  width/2 - 175, 50);
				}
			}
			}
		}
		if (mousePressed == true)
		{
			if(propgun == true)
			{
				wave.play();
				propogate();
				oldTime = System.currentTimeMillis();
				img = survivor;
				create();
				displayProp = false;
				propgun = false;
			}
			//gun.play();
			bulletSpawn++;
			createBullets();	
		}
		spawnBullets();
		image(title, width/2 - 125, 0, 250, 63);
		image(scoreboard, 0, 0, 120, 30);
		fill(33, 186, 238);
		text(secondscore, 70, 22);
		segment(x, y, calcAngle());	
		hitDetection();
		triggered = false;
		reset();
		}
	}
	
	private void playMusic() {
		if(play <= 0)
		{
			oldscoretime = System.currentTimeMillis();
			music.play();
		}
		play++;
	}

	private void create()
	{
		Point bullet = new Point(mouseX, mouseY);
		Point player = new Point((int)this.x, (int)this.y);
		Projectile p = new Projectile(player, bullet);
		p.startR();
		p.setNormal(false);
		projectiles.add(p);
	}
	
	private void propogate() {
		WaveInit w = new WaveInit(10);
		w.start();
		processing_x = w.processing_x;
		processing_y = w.processing_y;
		//try{Thread.sleep(250);}catch(Exception e){}
		for (int i = 0; i < enemies.size(); i++) 
		{
			int take = (int)(Math.random() * 50);
			enemies.get(i).takeHit(take);
			totalTake += take;
			ellipse(enemies.get(i).getX(), enemies.get(i).getY(), take*5, take*5);
			if(enemies.get(i).health <= 0)
				enemies.remove(i);
				totalKilled++;
		}
		//try{Thread.sleep(250);}catch(Exception e){}
	}	
	private void drawPropogator()
	{
		if(displayProp == true)
		image(propogator, width/2 - 30, height/2 - 30, 60, 60);
	}
	
	private void reset() {
		right = false;
		up = false;
		down = false;
		left = false;
	}

	public void createBullets()
	{
		if(bulletSpawn > 10)
		{
		Point bullet = new Point(mouseX, mouseY);
		Point player = new Point((int)this.x, (int)this.y);
		Projectile p = new Projectile(player, bullet);
		projectiles.add(p);
		bulletSpawn = 0;
		}
	}
	
	public void spawnBullets()
	{
		for (int i = 0; i < projectiles.size(); i++) 
		{
			Projectile p = projectiles.get(i);
			p.move();
			fill(204, 102, 0);
			//ellipse(p.getX(), p.getY(), (float)p.getRadius()*2, (float)p.getRadius()*2);
			if(p.normal == true)
				image(bullet, p.getX() - 8, p.getY() - 8, (float)p.getRadius()*2, (float)p.getRadius()*2);
			else
				image(propogationMissile, p.getX() - 8, p.getY() - 8, (float)p.getRadius()*2, (float)p.getRadius()*2);

			if(p.getX() > width)
				projectiles.remove(i);
			if(p.getX() < 0)
				projectiles.remove(i);
			if(p.getY() > height)
				projectiles.remove(i);
			if(p.getY() < 0)
				projectiles.remove(i);
		}
		for (int i = 0; i < enemies.size(); i++) 
		{
			Enemy bob = enemies.get(i);
			if(checkIfHit(bob))
			{
				bob.takeHit((int) bob.healthTake);
				if(bob.health <= 0)
				{	enemies.remove(i);
					totalKilled++;}
			}
		}
	}
	
	public boolean checkIfHit(Enemy e)
	{
		for (int i = 0; i < projectiles.size(); i++) 
		{
			Projectile p = projectiles.get(i);
			boolean normality = p.normal;
			float CenterX = (float)(p.getX() + p.getRadius());
			float CenterY = (float)(p.getY() + p.getRadius());
			float eX = e.getX()+e.getRadius();
			float eY = e.getY()+e.getRadius();
			float distance = (float)Math.sqrt((CenterX-eX)*(CenterX-eX) + (CenterY-eY)*(CenterY-eY));
			float maxDistance = 20;
			if(distance < maxDistance)
			{
				if(normality)
				{
				projectiles.remove(i);
				}
				return true;
			}
		}
		return false;
	}
	
	public void hitDetection(){
		fill(0);
		for (int g = 0; g < enemies.size(); g++) {
			Enemy e = enemies.get(g);
			float CenterX = x + playerW/2;
			float CenterY = y + playerH/2;
			float eX = e.getX()+e.getRadius();
			float eY = e.getY()+e.getRadius();
			float distance = (float)Math.sqrt((CenterX-eX)*(CenterX-eX) + (CenterY-eY)*(CenterY-eY));
			if(distance <= 20)
			{
				health -= 1;
				if(health <= 0)
				{
					gameOver = true;
				}
			}
		}
	}
	
	public void addEnemies() {
		if (time > 80) {
			Enemy b;
			if (Math.random() > 0.5) {
				float randomLoc = (float) Math.random() * width;
				b = new Enemy(randomLoc, 0);
				float randomSpeedX = (float) (-1 + Math.random() * 1);
				float randomSpeedY = (float) (Math.random() * 1);
				b.setSpeeds(randomSpeedX, randomSpeedY);
			} else {
				float randomLoc = (float) Math.random() * 600;
				b = new Enemy(randomLoc, height);
				float randomSpeedX = (float) (-1 + Math.random() * 1);
				float randomSpeedY = (float) (-Math.random() * 1);
				b.setSpeeds(randomSpeedX, randomSpeedY);
			}
			b.setHealthTake(50);
			enemies.add(b);
			time = -30;
		}
	}

	public void spawnEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy bob = enemies.get(i);
			bob.move();
			fill(color(255, 165, 0));
			Follower.calculateValues((int) bob.getX(), (int) bob.getY(), (int) x + playerW/2, (int) y + playerH/2);
			bob.setX(Follower.newX);
			bob.setY(Follower.newY);
			Follower.setSpeed(2);
			strokeWeight((float) 2.0);
			stroke(255, 0, 0);
			noFill();
			fill(0);
			strokeWeight((float) 2.0);
			stroke(0);
			noFill();
			rect(bob.getX() + 5, bob.getY() -12, 30, 5);
			noStroke();
			fill(255, 0, 0);
			double e = bob.health/100;
			rect(bob.getX() + 6, bob.getY() -11, (int)(29*e), 4);
			if(playGame < 40)
			{
			image(img2, bob.getX(), bob.getY(), 40, 40);
			}
			else
			{
				if(playGame > 80)
				{
					playGame = 0;
				}
			image(img2, bob.getX(), bob.getY(), 40, 40);
			}
			playGame++;
		}
	}
	
	public void control()
	{
		if (keyPressed) {
		if(key == 's')
		{
			if(game == true)
			{
				game = false;
				gameOver = false;
				resetGame();
			}
		}
		if(key == 'r')
		{
			if(gameOver == true)
			{
				game = false;
				gameOver = false;
			}
			resetGame();
		}
		}
	}

	public void move() {
		int margin = 25;
		if (keyPressed) {
			if(key == 'e')
				triggered = true;
			if(key == 'w')
				up = true;
			if(key == 's')
				down = true;
			if(key == 'd')
				right = true;
			if(key == 'a')
				left = true;
		if(right == true)
		{
			if(x+40 > width - margin)
			{}
			else
				x = x + xSpeed;
		}
		if(up == true)
		{
			if(y < 60)
			{}
			else
			y = y - ySpeed;
		}
		if(left == true)
		{
			if(x < 60)
			{}
			else
			x = x - xSpeed;
		}
		if(down == true)
		{
			if(y + 40 > height - margin)
			{}
			else
			y = y + ySpeed;
		}
		}
	}

	public float calcAngle() {
		float distanceX = mouseX - x;
		float distanceY = mouseY - y;
		float angle1 = atan2(distanceY, distanceX);
		return angle1;
	}

	void segment(float x, float y, float a) {
		strokeWeight((float) 2.0);
		stroke(0, 0, 255);
		translate(x, y);
		rotate(a);
		noFill();
		ellipse(0, 0, playerW + 10, playerH + 10);
		image(img, 0 - playerW/2, 0 - playerH/2, playerW, playerH);
	}
	
	public void resetGame()
	{
		shooting = false;
		frameRate(60);
		img = loadImage("survivor.png");
		img1 = loadImage("Background.png");
		img2 = loadImage("termy.png");
		propogator = loadImage("propy.png");
		title = loadImage("texty.png");
		computer = loadImage("computer.jpg");
		bullet = loadImage("bully.png");
		size(900, 600);
		myFont = createFont("AppleBraille", 30);
		textFont(myFont);
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		x = width / 2;
		y = height / 2;
		possible = false;
		oldTime = System.currentTimeMillis();
		health = 100;
		propgun = false;
		displayProp = false;
		}
	
}
