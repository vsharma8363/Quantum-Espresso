import java.awt.Point;

public class Projectile {
	float x;
	float y;
	float xSpeed;
	float ySpeed;
	double radius = 5;
	Boolean isAlive;
	int height = 5;
	int width = 5;
	Point mouse, player;
	float slope;
	boolean stop = false;
	boolean normal = true;
	
	public Projectile (Point player, Point mouse){
		height = 5;
		width = 5;
		x = (float)player.getX();
		y = (float)player.getY();
		this.mouse = mouse;
		this.player = player;
		slope = (float) ((mouse.getY()-player.getY())/(mouse.getX()-player.getX()));
	}
	public void move(){
		if(stop == true)
			radius+=15;
		int xSpeed = 10;
		if(mouse.getX() <= player.getX())
		{
			xSpeed*=-1;
			y -= slope;
		}
		else
		{
			y += slope;
		}
		x += xSpeed;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(int x)
	{
		radius = x;
	}
	
	public void startR()
	{
		radius = 0;
		stop = true;
	}
	public void setNormal(boolean b) {
		// TODO Auto-generated method stub
		normal = b;
	}
}
