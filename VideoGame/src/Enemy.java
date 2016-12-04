
public class Enemy {
	float x;
	float y;
	float xSpeed;
	float ySpeed;
	int radius = 80;
	float health = 100;
	float healthTake = 0;
	
	public Enemy(float a, float b){
		this.x = a;
		this.y = b;
	}
	public void move(){
			this.x = this.x + this.xSpeed;
			this.y = this.y + this.ySpeed;
		
	}
	
	public void takeHit(int hp)
	{
		health -= hp;
	}
	
	public void setHealthTake(float x)
	{
		healthTake = x;
	}
	
	public void setSpeeds(float a, float b) {
		this.xSpeed = a;
		this.ySpeed = b;
	}
	
	public void attack(){
		
	}
	public boolean checkIfDead(){
		return false;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getRadius() {
		return radius;
	}
	public int rightSideX() {
		return (int) (this.radius + this.x);
	}
	public int leftSideX(){
		return (int)(this.x - this.radius);
	}
	
}
