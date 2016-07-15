
public class TestingWaves {

	  static double quanta_1;
	  static double omega;
	  static double Normalization;
	  static Complex [] initial_wavepacket;
	  static double [] V;
	  static double [] x_grid = new double[]{};
	  static double delta_x;
	  static int iter = 0;
	  static double delta_t;
	  
	  static Complex [] answers;
	  
	
	public static void main(String[] args) {
		define();
		
		answers = QuantumMethods.propagator(x_grid, delta_x, delta_t, V, initial_wavepacket);
		
		System.out.println("Total Answers : " + answers.length);
		System.out.println();
		
		for(int x = 0; x < answers.length; x++)
		{
		System.out.println("#" + (x+1) + " ||| Input:" + x_grid[x] + " ||| Output:" + answers[x]);
		}
	}
	
	public static void define()
	  {
		  delta_t = 0.05;
		  delta_x = 0.2;
		  quanta_1 = 0;
		  omega = 1;
		  Normalization = (Math.pow((omega/Math.PI), 1/4))/(Math.sqrt((Math.pow(2, quanta_1))*(factorial((int)quanta_1)))); 
		 
		  x_grid = new double[128];
		  for(double x = -10; x < 15.6; x+=0.2)
		  {
			 /* System.out.println(x);
			  System.out.println(iter);*/
			  x_grid[iter] = x;
			  iter++;
			  if(iter == 128)
			  {
				  break;
			  }

		  }
		  V = new double[x_grid.length];
		  
		  for(int x = 0; x < x_grid.length; x++)
		  {
			  double w = (Math.pow(omega, 2) * (Math.pow(x_grid[x],2)))/2;
			  V[x] = w;
		  }
		  
		  initial_wavepacket = new Complex[x_grid.length];
		  
		  for(int x = 0; x < x_grid.length; x++)
		  {
			  double newXGRID = Math.pow((x_grid[x] + 5), 2);
			  Complex w = new Complex(Math.cos(x_grid[x]), Math.sin(x_grid[x]));
			  double alpha = (Normalization * (Math.exp((-1/4) * newXGRID)));
			  initial_wavepacket[x] = w.times(alpha);
		  }
		  
		  
	  }
	
	  public static int factorial(int n) {
	      int fact = 1; // this  will be the result
	      for (int i = 1; i <= n; i++) {
	          fact *= i;
	      }
	      return fact;
	  }

}
