# Quantum_Lib
A library to model several mechanical/ quantum models

Includes the following methods and classes:
FFT.java ~ From Princeton CS intro site, to manage Fast Fourier Tranformation
Complex.java ~ From Princeton CS intro site, to aid in management of Complex numbers as Java is unable to handle imaginary concepts
QuantumMethods.java ~ All methods which can be used to create simulations
  1. propogation ~ given the inputs, it can be used to calculate wave propogation
  2. getAngle ~ given an angle of bounce θ, this will give the angle the ball will bounce from reference angle θ
  3. getEnergy ~ given parameters, calculates the amount of energy an object has
  4. calculateVelocity ~ Will calculate the velocity of the object given the inputs
  5. predictChance ~ given an array of previous answers, and the prime answer choice, this method will tell you the % chance of getting the prime value
  
###Propogation
  For the propogation method, defining the variables in Java is a pain, so I have uploaded a program that defines all the variables and
sets the x_grid size from -10 to 15.6 (That way the propogation occurs from 0~128, a power of 2)

The file for use of testing and printing the variables is called: 
###TestingWaves.java

The file I called to define and store multiple variables is called: 
###InitialWaveStorage.java
  This file saves all aspects of the initial wave in a seperate file to make your overall program a more clean package


##Key Points:
I have also made a program that graphs the first two wavepackets by plugging the new packet as the initial and re-running the method.
this file is called: ###GraphMain.java
