import math
import numbers
import numpy as np

class Neuron:
    #Constructor that has no-args
    def __init__(self):


    #Get the ratio of correct answers in the array (1 being correct and 0 being wrong)
        def GetRatioCorrect(previousInput):
            previousCorrect = 0
            previousWrong = 0
            for i in previousInput:
                if(i == 1):
                    previousCorrect+=1
                if(i == 0):
                    previousWrong+=1
                else:
                    print("invalid output")
            return previousCorrect/(previousCorrect + previousWrong)

    #Get weight between neuron SELF-->n
        def GetNeuronalWieght(self, n):

            print("hello")

    #Force a connection between neuron SELF-n
        def ForceFire(self, targetN, connections):
            oldIndex = 0
            oldValue = 0
            for p in range(len(connections)):
                oldValue = connections[p].getLargestConnection()
                oldIndex = p

