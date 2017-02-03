package quantum;

import utilities.Complex;
import utilities.FFT;

public class QuantumMethods {

	public QuantumMethods()
	{
		
	}
	
	/*
	 * This method will give you the angle of bounce direction based on what angle and object hit a barrier
	 * 
	 */
	public static double getAngle(double angle)
	{
		double r = 180 - angle*2;
		return r;
	}
	
	/*
	 * Calculating velocity of any object hitting the ground in a conservative system
	 */
	public static double calculateVelocity(double initialHeight, double mass, double gravity, double secondHeight)
	{
		double E0 = getEnergy(mass, gravity, initialHeight);
		double E1 = getEnergy(mass, gravity, secondHeight);
		double v1 = (2*(E0-E1))/mass;
		double v = Math.sqrt(v1);
		return v;
	}
	  
	/*
	 * Gives energy of any object with a certain mass+gravitationalConstant+initial height
	 */
	public static double getEnergy(double mass, double gravity, double initialHeight)
	{
		double Energy = mass*gravity*initialHeight;
		return Energy;
	}
	
	/*
	 * Similar to a neuronal calculator, this method returns the chance that a PrimeValue will be returned given the array of previousAnswers
	 */
	public static double predictChance(double [] previousAnswers, double PrimeValue)
	{
		double average = 0;
		double smallest = 1000000000;
		double largest = -1000000000;
		double a1 = 0;
		double answer = 0;

		for(int i = 0; i < previousAnswers.length; i++)
		{
			average += previousAnswers[i];
			if(previousAnswers[i] <= smallest)
				smallest = previousAnswers[i];
			else if(previousAnswers[i] >= largest);
				largest = previousAnswers[i];
		}
		average = average/(previousAnswers.length);
		
		a1 = (smallest + largest)/2;
		
		a1 = (a1 + average)/2;
		
		answer = PrimeValue/a1;		
		
		if(answer > 100)
		{
			answer = answer - 100;
			answer = 100-answer;
		}
			
		return answer;
		
	}
	
	/* 
	 * @param x_grid ==> Array of all x values
	 * @param delta_x ==> change in each x-value
	 * @param delta_t ==> change in time for each x-value point
	 * @param V ==> Array of all potentials 
	 * @param psi_t ==> Array (range of wave packet) (This is essentially an array of complex numbers)
	 */

	public static Complex[] propagator(double [] x_grid, double delta_x, double delta_t, double [] V, Complex[] psi_t)
	{
		double x_grid_size = x_grid.length;
		double delta_k_x = (2 * Math.PI)/(x_grid_size*delta_x);
		
		//Gets values from (-Math.PI/delta_x) to (Math.PI/delta_x) from the array delta_k_x, & sets it to kx_grid
		double s = -Math.PI/delta_x;
		double e = Math.PI/delta_x;
		double a = delta_k_x;
		
		double[] kx_grid = new double[x_grid.length];
		int w = 0;
		
		for(double i = s; i < e; i+=a)
		{
			kx_grid[w] = i;
			w+=1;
			if(w == 128)
				break;
		}
		
		//Double k is the same as kx_grid, but every value is the square of the one from kx_grid
		double [] K = new double[kx_grid.length];
		for(int i = 0; i < kx_grid.length; i++)
		{
			K[i] = Math.pow((kx_grid[i]), 2);
		}
		
		//Kinetic energy propogator
		
		//exp(i * K * delta_t)
		Complex [] expK = new Complex[K.length];
		
		//Outside library to calculate value of the square root of -1
		
		for(int x = 0; x < K.length; x++)
		{
			expK[x] = new Complex(Math.cos(K[x] * delta_t), Math.sin(K[x] * delta_t));
		}
		
		//exp(i * K * delta_t/2) (Complex)
		Complex [] expV = new Complex[K.length];
		
		for(int x = 0; x < K.length; x++)
		{
			expV[x] = new Complex(Math.cos(V[x] * delta_t/2), Math.sin(V[x] * delta_t/2));
		}
		
		//Initialize bulb first
		double [] bulb = new double[(int)x_grid_size];
		
		//for loop (i in range of x_grid_size)
		for(double x = 0; x < x_grid_size; x++)
		{
			bulb[(int)x] = Math.pow(-1, x);
		}
		//bulb[i] ==> add this number = (-1^i)
		
		//Multiplies elements together
		for(int x = 0; x < expV.length; x++)
		{
			//Complex psi_t & complexx expV
			psi_t[x] = psi_t[x].times(expV[x]);
		}
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//psi_t = bulb * (fast fourier transform of (bulb*psi_t))
		Complex [] fft = new Complex[bulb.length];
		
		
		for(int x = 0; x < bulb.length; x++)
		{
			fft[x] = psi_t[x].times(bulb[x]);
		}		
		//apply fft to the array and get something like fft[x] = output[x]
		fft = FFT.fft(fft);
		
		for(int x = 0; x < fft.length; x++)
		{
			psi_t[x] = fft[x].times(bulb[x]);
		}	
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		for(int x = 0; x < expK.length; x++)
		{
			psi_t[x] = psi_t[x].times(expK[x]);
		}
		
		
		//psi_t = bulb * (inverse fast fourier transform of (bulb*psi_t))
		
		Complex [] ifft = new Complex [bulb.length];
		for(int x = 0; x < bulb.length; x++)
		{
			ifft[x] = psi_t[x].times(bulb[x]);
		}		
		//apply fft to the array and get something like fft[x] = output[x]
		ifft = FFT.ifft(ifft);
		
		for(int x = 0; x < ifft.length; x++)
		{
			psi_t[x] = ifft[x].times(bulb[x]);
		}	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		for(int x = 0; x < expV.length; x++)
		{
			psi_t[x] = psi_t[x].times(expV[x]);
		}
		
		return psi_t;
		
	}
	  	
	/**
	 * @param x_grid ==> Array of all x values
	 * @param delta_x ==> change in each x-value
	 * @param delta_t ==> change in time for each x-value point
	 * @param V ==> Array of all potentials 
	 * @param psi_t ==> Array (range of wave packet) (This is essentially an array of complex numbers)
	 * @param delta_y ==> Total y-axis length
	 * @return ==> Complex number of y-axis value at certain point
	 */
		public static Complex[][] propogator2d(double x_grid_size, double y_grid_size, double delta_x, double delta_y, double delta_t, double [][] V, Complex[][] psi_t)
	{
		double delta_k_x = (2 * Math.PI)/(x_grid_size*delta_x);
		double delta_k_y = (2 * Math.PI)/(x_grid_size*delta_y);
		
		double s = -Math.PI/delta_x;
		double e = Math.PI/delta_x;
		double a = delta_k_x;
		
		double[] kx_grid = new double[(int) x_grid_size];
		int w = 0;
		
		for(double i = s; i < e; i+=a)
		{
			kx_grid[w] = i;
			w+=1;
			if(w == 128)
				break;
		}
		
		s = -Math.PI/delta_y;
		e = Math.PI/delta_y;
		a = delta_k_y;
		
		double[] ky_grid = new double[(int) y_grid_size];
		w = 0;
		
		for(double i = s; i < e; i+=a)
		{
			ky_grid[w] = i;
			w+=1;
			if(w == 128)
				break;
		}
		
		double[][] kx_grid_2d = new double[ky_grid.length][kx_grid.length];
		
		
		for(int eg = 0; eg < ky_grid.length; eg++)
		{
			int j = 0;
			for(int i = 0; i < kx_grid.length; i++)
			{
				System.out.println(j);
				kx_grid_2d[j][eg] = kx_grid[i];
				j++;
			}
		}
	
		double[][] ky_grid_2d = new double[ky_grid.length][kx_grid.length];
		
		for(int i = 0; i < ky_grid_2d.length; i++)
		{
			ky_grid_2d[i] = ky_grid;
		}
		
		double [][] K = new double[kx_grid_2d.length][ky_grid_2d.length];
		for(int x = 0; x < kx_grid_2d.length; x++)
		{
			for(int i = 0; i < kx_grid_2d.length; i++)
			{
				K[i][x] = Math.pow((kx_grid_2d[i][x]), 2)/2;
			}
		}
		
		//double [][] vK = new double[kx_grid_2d.length][ky_grid_2d.length];
		for(int x = 0; x < ky_grid_2d.length; x++)
		{
			for(int i = 0; i < ky_grid_2d.length; i++)
			{
				K[i][x] += Math.pow((ky_grid_2d[i][x]), 2)/2;
			}
		}
		
		Complex [][] expK = new Complex[(int) x_grid_size][(int) y_grid_size];
		
		for(int x = 0; x < x_grid_size; x++)
		{
			for(int i = 0; i < expK.length; i++)
			{
				expK[x][i] = new Complex(Math.cos(K[x][i] * delta_t), Math.sin(K[x][i] * delta_t));
			}
		}
		
		Complex [][] expV = new Complex[(int) x_grid_size][(int) y_grid_size];
		
		for(int x = 0; x < x_grid_size; x++)
		{
			for(int i = 0; i < expK.length; i++)
			{
				expV[x][i] = new Complex(Math.cos(V[x][i] * delta_t/2), Math.sin(V[x][i] * delta_t/2));
			}
		}
		
		double [][] bulb = new double[(int)x_grid_size][(int)y_grid_size];
		
		for(int i = 0; i < x_grid_size; i++)
		{
			for(int x = 0; x < y_grid_size; x++)
			{
				bulb[i][x] = Math.pow(-1, i+x);
			}
		}
		
	/*
	 * HERE IS THE STOPPING POINT wHERE SOMEONE NEEDS TO FIND FFT2 ARRAY PROGRAMS BRUH
	 * psi_t = new double[][];
	 */
		psi_t = new Complex[(int) y_grid_size][(int) x_grid_size];
		
		for(int i = 0; i < y_grid_size; i++)
		{
			//rows ~ done
			psi_t[i] = FFT.fft(psi_t[i]);
		}
		for(int i = 0; i < x_grid_size; i++)
		{
			//columns
			psi_t[i] = FFT.fft(psi_t[i]);
		}
		
		for(int i = 0; i < y_grid_size; i++)
		{
			for(int w1 = 0; w1 < x_grid_size; w1++)
			{
				Complex ax = expK[i][w];
				Complex bx = psi_t[i][w];
				psi_t[i][w1] = ax.times(bx);
			}
		}
		
		for(int i = 0; i < y_grid_size; i++)
		{
			//rows ~ done
			psi_t[i] = FFT.ifft(psi_t[i]);
		}
		for(int i = 0; i < x_grid_size; i++)
		{
			//columns
			psi_t[i] = FFT.ifft(psi_t[i]);
		}
		
		for(int i = 0; i < y_grid_size; i++)
		{
			for(int w1 = 0; w1 < x_grid_size; w1++)
			{
				Complex ax = expV[i][w];
				Complex bx = psi_t[i][w];
				psi_t[i][w1] = ax.times(bx);
			}
		}
		
		return psi_t;
	}
	
	
	
	
	
	
}
