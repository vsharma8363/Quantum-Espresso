public class WaveInit {
	 
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
	  public static double [] x_values;
	  public static double [] y_values;	  
	  public static double [] processing_x;
	  public static double [] processing_y;
	  static int w = 0;
	
	public WaveInit(int w)
	{
	  	WaveInit.w = w;
	}
	
	public static void start()
	{
		define();
	  	getValues(w);
	}
	
	  public static void getValues(int w)
	  {	  
		  processing_x = new double[w * x_values.length];
		  processing_y = new double[w * y_values.length];
		  int count = 0;
		  double margin = 0;		  
		  for(int i = 0; i < w; i++)
		  {
		  	answers = QuantumMethods.propagator(x_grid, delta_x, delta_t, V, initial_wavepacket);
			for(int x = 0; x < answers.length; x++)
			{
			  	x_values[x] = (x_grid[x]+10)*10 + margin;
			  	y_values[x] = (answers[x].re()*100 + 100);
			  	processing_x[count] = x_values[x];
			  	processing_y[count] = y_values[x];
			//	System.out.println("X: " + processing_x[count] + " ||| Y: " + processing_y[count] + " ~~~ Count: " + x);
			  	count++;
			}
			initial_wavepacket = answers;
			margin += 100;
		  }
	  }
	
		public static void define()
		  {
			  y_values = new double[128];
			  x_values = new double[128];
			  delta_t = 0.05;
			  delta_x = 0.2;
			  quanta_1 = 0;
			  omega = 1;
			  Normalization = (Math.pow((omega/Math.PI), 0.25))/(Math.sqrt((Math.pow(2, quanta_1))*(factorial((int)quanta_1)))); 
			 
			  x_grid = new double[128];
			  
			  iter = 0;
			  for(double x = -10; x < 15.6; x+=0.2)
			  {
				 // System.out.println(x);
				  x_grid[iter] = x;
				  iter++;
				  if(iter >= 128)
				  {
					  break;
				  }

			  }
			  V = new double[x_grid.length];
			  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		/*	  int i = 0;
			  for(int x = 0; x < (int)(x_grid.length*0.7); x++)
			  {
				  V[x] = 0;
				  i = x + 1;
			  }
			  
			  V[i - 1] = enemy_potential; 
					  
			  for(int x = i; x < x_grid.length - (int)(x_grid.length*0.73) - 1; x++)
			  {
				  V[x] = 0;
			  }*/
			  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			  //This is for a harmonic oscillation
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
				  double alpha = (Normalization * (Math.exp((-0.25) * newXGRID)));
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
		  
		  public int[][] generateHitMap()
		  {
			  int col = 10;
			  int row = 10;
			  int [][] map = new int[row][col];
				
			  for(int i = 0; i < row; i++)
			  {
				  for(int w = 0; w < col; w++)
				  {
					  map[i][w] = (int)((Math.random()*5) + 0.5);					  
				  }
			  }
				return map;
		  }

}
