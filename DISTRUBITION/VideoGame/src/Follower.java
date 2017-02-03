public class Follower 
{	
	public static int newX = 0, newY = 0;
	private static double linearize = 0.5;
	private static double Speed = 3 + linearize;
	public static double angle = 0;
	
	public static void calculateValues(int currentX, int currentY, int targetX, int targetY)
	{
		newX = currentX;
		newY = currentY;
		
        int MoveToX = targetX;
        int MoveToY = targetY;

        int diffX = MoveToX - newX;
        int diffY = MoveToY - newY;

        float angle = (float)Math.atan2(diffY, diffX);

        Follower.angle = angle;
        
        newX += Speed * Math.cos(angle);
        newY += Speed * Math.sin(angle);
	}
	
	public static void setSpeed(int x)
	{
		Speed = Speed/2;
		Speed = x + linearize;
	}
}
