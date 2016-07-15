import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;

public class GraphMain extends JApplet implements ActionListener{
	
  private static Container c;
  private static Polygon graph;
  private static int i = 0;
  private static double output = 0;
  private static double input = 0;
  private static Rectangle r;
  private static Scanner s;
  
 
  //Propogation Methods
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
  
  
  public void init()
  {
    c = getContentPane();
    c.setBackground(Color.WHITE);
    graph = new Polygon();
    setSize(1200, 400);
    //Timer clock = new Timer(50, this);  // calls actionPerformed(ActionEvent e) ==> every 30 milliseconds 
    //clock.start();
    define();
  }
  
	
	public static void define()
	  {
		  delta_t = 0.05;
		  delta_x = 0.2;
		  quanta_1 = 0;
		  omega = 1;
		  Normalization = (Math.pow((omega/Math.PI), 0.25))/(Math.sqrt((Math.pow(2, quanta_1))*(factorial((int)quanta_1)))); 
		 
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
		  
		  //double alpha
		  
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


  public void paint(Graphics g)
  {
    super.paint(g);
    getValues(g);
  }

  public void actionPerformed(ActionEvent e)
  {
    repaint();
  }
  
 /* public void drawAxis(Graphics g)
  {
	  Rectangle r = graph.getBounds();
	  g.setColor(Color.RED);
	  g.drawLine(c.getWidth()/2, r.y, c.getWidth()/2, r.y + r.height);	  
	  g.setColor(Color.BLUE);
	  g.drawLine(r.x, (r.height + r.y) / 2, r.x + r.width, (r.height + r.y) / 2);
  }*/
  
  
  public void getValues(Graphics g)
  {

	  for(int w = 0; w < 10; w++){
	  	answers = QuantumMethods.propagator(x_grid, delta_x, delta_t, V, initial_wavepacket);
		for(int x = 0; x < answers.length; x++)
		{
			System.out.println("#" + (x+1) + " ||| Input:" + x_grid[x] + " ||| Output:" + answers[x]);
			
			int i = (int)(x_grid[x]*10);
			int o = (int)(answers[x].re()*100);
			
			if(w == 9 || w == 0)
			graph.addPoint(i+ 400, o + c.getHeight()/2);
		}
		  g.setColor(Color.BLUE);
		  g.drawPolygon(graph);
		  
		  initial_wavepacket = answers;
		
	  
/*	  for(int x = 0; x < x_grid.length; x ++)
	  {
		  int i = (int)(x_grid[x]*10);
		  int o = (int)(initial_wavepacket[x].re()*10);
		  graph.addPoint(i+400, o + c.getHeight()/2);

	  }*/
	  }
  }
  
}
  
