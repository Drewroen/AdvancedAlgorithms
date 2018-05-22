/**
 * Authors: Chad Carter and Drew Roen
 * Date: 3/?/18
 * Created by Chad Carter on 3/14/2018.
 * ON page 264
 */
import java.util.Scanner;
import java.util.Arrays;
import java.lang.StringIndexOutOfBoundsException;
public class ShortestSuperstring {
    public static void main(String[] args) {
    	//Creates a new Scanner to read numbers and strings.
    	//Begins creating a loop for the program that can be exited.
		Scanner scan = new Scanner(System.in);
        String answer = "y";
        do {
        	//Asks for the number of strings the user wants to test.
        	//Fast results for 1-7 strings, takes significantly longer at 8+ strings.
        	System.out.println("Enter the number of strings you want to test: ");
            int numberOfStrings = scan.nextInt();
            
            //Creates a list for our strings to be taken from the scanner.
            String[] stringMatrix = new String[numberOfStrings];
            
            //Stores strings from input into the list of strings.
            System.out.println("Enter " + numberOfStrings + " strings: ");
            for (int i=0; i < numberOfStrings; i++)
                stringMatrix[i] = scan.next();
            
         	//Combo creates a basis for the sorting algorithm we use.
            //The algorithm relies on order of strings, so we change this order over time by storing combinations within combo.
            int[] combo = new int[numberOfStrings];
            for(int i = 0; i < combo.length; i++)
                combo[i] = (i + 1) * -1;
            
            //Factorial to find the number of combinations the strings can be organized in.
            //This is why the program takes so long to run with 8+ strings.
            int numCombinations = 1;
            for (int i=1; i<=combo.length; i++)
                numCombinations*=i;

            //Starts with an empty shortest string, replaced by the superstring from the first combination.
            String shortestString = null;
            
            //Loops through every combination, finds the shortest superstring for said combination, and compares it to the overall shorest superstring.
            for (int i=0; i<numCombinations; i++)
            {
            	String tempSuperString = shortestSuperString(stringMatrix, combo);
                nextCombination(combo);
                if(shortestString == null || tempSuperString.length() < shortestString.length())
                	shortestString = tempSuperString;
            }
            
            //Prints out one option for the shortest superstring, along with the length.
            System.out.println("The shortest superstring in this set is: " + shortestString + "\nThe length of the shortest superstring in the set is: " + shortestString.length());
            System.out.println("Another run? (y/n)");
            answer = scan.next();
        } while (answer.equalsIgnoreCase("y"));


    }
    
    //Shortest superstring method, finds the shortest superstring from the combination integer array.
    //For example, if combo is [1, 2, 3], then it takes from the string array in the order of 1, 2, 3.
    //If combo is [1, 3, 2], it will take from the string array in the order of 1, 3, 2.
    public static String shortestSuperString(String[] stringMatrix, int[] combo)
    {
    	//Starts with the basic superstring, which adds the first string of the combination.
        String output = stringMatrix[absolute(combo[0]) - 1];
        
        //Loops through the remaining strings.
        for(int i = 1; i < stringMatrix.length; i++)
        {
        	//Checks if the strings is already contained within the superstring. If it is, ignore it.
            if(!output.contains(stringMatrix[absolute(combo[i]) - 1]))
            {
            	//Compares the end of the superstring to the beginning of the current string. If they match, merge them together.
            	int endPos = 0;
	            for(int j = 0; j <= stringMatrix[absolute(combo[i]) - 1].length(); j++)
	            {
	                if (output.endsWith(stringMatrix[absolute(combo[i]) - 1].substring(0, j)))
	                    endPos = j;
	            }
	            output += stringMatrix[absolute(combo[i]) - 1].substring(endPos);
            }
        }
        return output;
    }

    //Creates the next combination the strings are to be ordered in.
    //Uses a Johnson-Trotter algorithm to efficiently create the combinations.
    //It is slightly faster because it uses significantly less swaps than other combination creating algorithms.
    public static void nextCombination(int[] combo)
    {
        int numCombos = 1;
        for (int i=1; i<=combo.length; i++) {
            numCombos *=i;
        }
        int[][] allCombos = new int[numCombos][combo.length];
        int maxValue = 0;
        int maxPosition = -1;
        for(int i = 0; i < combo.length; i++)
        {
            if(absolute(combo[i]) > maxValue)
            {
                if(combo[i] < 0)
                    if(i > 0)
                        if(absolute(combo[i]) > absolute(combo[i - 1]))
                        {
                            maxValue = absolute(combo[i]);
                            maxPosition = i;
                        }
                if (combo[i] > 0)
                    if(i < combo.length - 1)
                        if(absolute(combo[i]) > absolute(combo[i + 1]))
                        {
                            maxValue = absolute(combo[i]);
                            maxPosition = i;
                        }
            }
            allCombos[i] = combo;
        }
        if(maxPosition != -1)
        {
            if(combo[maxPosition] < 0)
            {
                int temp = combo[maxPosition];
                combo[maxPosition] = combo[maxPosition - 1];
                combo[maxPosition - 1] = temp;
            }
            else if(combo[maxPosition] > 0)
            {
                int temp = combo[maxPosition];
                combo[maxPosition] = combo[maxPosition + 1];
                combo[maxPosition + 1] = temp;
            }
        }
        if(maxValue > 0)
            for(int i = 0; i < combo.length; i++)
                if(absolute(combo[i]) > maxValue)
                    combo[i] *= -1;
    }
    
    //Basic absolute value function for personal use.
    public static int absolute(int j)
    {
        if(j < 0)
            return j * -1;
        else
            return j;
    }

}
