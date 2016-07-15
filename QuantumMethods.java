/*
 * @Vikram Sharma
 *  
 */

public class QuantumMethods {

	public QuantumMethods()
	{
		
	}
	
	//This method will give you the angle of bounce direction based on what angle and object hit a barrier
	public static double getAngle(double angle)
	{
		double r = 180 - angle*2;
		return r;
	}
	
	//Calculating velocity of any object hitting the ground in a conservative system
	public static double calculateVelocity(double initialHeight, double mass, double gravity, double secondHeight)
	{
		double E0 = getEnergy(mass, gravity, initialHeight);
		double E1 = getEnergy(mass, gravity, secondHeight);
		double v1 = (2*(E0-E1))/mass;
		double v = Math.sqrt(v1);
		  
		return v;
	}
	  
	//Gives energy of any object with a certain mass+gravitationalConstant+initial height
	public static double getEnergy(double mass, double gravity, double initialHeight)
	{
		double Energy = mass*gravity*initialHeight;
		return Energy;
	}
	
	//Similar to a neuronal calculator, this method returns the chance that a PrimeValue will be
	//returned given the array of previousAnswers
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
	
	//x_grid ==> Array of all x values
	//delta_x ==> change in each x-value
	//delta_t ==> change in time for each x-value point
	//V ==> Array of all potentials 
	//psi_t ==> Array (range of wave packet) (This is essentially an array of complex numbers)

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
	  	
	
	
	
	
	
	
	
}
