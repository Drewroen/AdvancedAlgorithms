
/**
 * class LCS.
 * Impelements the algorithm for finding the longest commen sequence in two strings on p. 176
 * 
 * Data:
 *  string1, string2 - variables of type String, contain the strings to be aligned
 *  size1, size1 - length of the strings
 *  
 *  int [size1+1][size2+1] simTable  
 *       simTable[i][j] - shows the length of LCS between the i-th prefix of string1 and j-th prefix of string2
 *       Initialization: first row and first column = 0, i.e. 0 length if one of the prefixes is empty
 *       
 *       simTable[i][j] = max of
 *                          a) simTable[i-1][j-1] + 1   if string1[i-1] = string2[j-1]
 *                              (note that string1[i-1] contains the character 
 *                               corresponding to i-th row of simTable, 
 *                               string2[j-1] contains the character corresponding to the j-th column )
 *                          b) simTable[i-1][j],
 *                          c) simTable[i][j-1]
 *       
 *        
 *  int [size1][size2] backTable - used to record the movements in order to restore the LCS
 *      Note that the number of roes equals the number of characters in string1, 
 *      columns equal the number of characters in string2
 *      
 *      backTable[i][j] = 0 if max above for simTable[i+1][j+1]  is (a) - diagonal movements
 *                      = 1 if max above for simTable[i+1][j+1]  is (b) - vertical movement (from up)
 *                      = -1 if max above for simTable[i+1][j+1] is (c) - horizontal movement (from left)
 * 
 * int [size1+1][size2+1] editDistance  number of insertions and deletions needed to 
 *                                      transform string1 into string2
 * 
 *      Initialization: first row[i] = i, i = 0, ..., size2
 *                      first column[j] = j, j = 0, ..., size1
 *                      
 *          editDistance[i][j] = min of
 *                               a) editDistance[i][j-1] + 1,
 *                               b) editDistance[i-1][j] + 1,
 *                               c) editDistance[i-1][j-1]   if string1[i-1] = string2[j-1]
 *      
 * 
 * @author 
 * @version 
 */
import java.util.*;
public class LCS
{
public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("This program computes the longest common sequence of two strings");
 
        String string1 = "ATCTGAT";
        String string2 = "TGCATA";
      
        int size1 = string1.length();
        int size2 = string2.length();
        
        int [][] simTable = new int [size1+1][size2+1];
        int [][] editDistTable = new int [size1+1][size2+1];
        int [][] backTable = new int [size1][size2];
        
// initialize first row and first column of simTable
       for(int i = 0; i < simTable.length; i++)
    	   simTable[i][0] = 0;
       
       for(int i = 0; i < simTable[0].length; i++)
    	   simTable[i][0] = 0;
 
// main loop
// Start filling the dynamic table row by row  
      for(int i = 1; i < simTable.length; i++)
    	  for(int j = 1; j < simTable[0].length; j++)
    	  {  
    		  if(string1.substring(i-1,i).equals(string2.substring(j-1, j)))
    			  simTable[i][j] = simTable[i-1][j-1] + 1;
    		  else
    			  simTable[i][j] = Math.max(simTable[i-1][j], simTable[i][j-1]);
    	  }
    			  
// Start filling in the back table row by row
      for(int i = 0; i < simTable.length - 1; i++)
    	  for(int j = 0; j < simTable[0].length - 1; j++)
    	  {
    		  if(string1.substring(i,i+1).equals(string2.substring(j,j+1)))
    			  backTable[i][j] = 0;
    		  else if(simTable[i+1][j] > simTable[i][j+1])
    			  backTable[i][j] = -1;
    		  else
    			  backTable[i][j] = 1;
    	  }

 
// compute edit distance table
// initialize first row and first column
         

// Start filling the edit distance table row by row  
        
// Print dynamic table
      System.out.println ("Dynamic table computed:");
      for(int i = 0; i <= size1; i++)
      {
        for(int j = 0; j <= size2; j++)      
          System.out.print(simTable[i][j] + ",");
        System.out.println();
      }
 
 // Print back table    
 
     System.out.println ("back table computed:");
      for(int i = 0; i < size1; i++)
      {
        for(int j = 0; j < size2; j++)      
          System.out.print(backTable[i][j] + ",");
        System.out.println();
      }
 // Print Edit Distance Table table
      System.out.println ("Edit Distance table computed:");
      for(int i = 0; i <= size1; i++)
      {
        for(int j = 0; j <= size2; j++)      
          System.out.print(editDistTable[i][j] + ",");
        System.out.println();
      }
 // Print longest common sequence
 System.out.println("Longest common sequence:");
  printLCS(size1,size2, string1, backTable);
 
}  
/*------------------------------------------------------*/
 public static void printLCS(int row, int column, String string1, int [][]backTable)
 {
         
 }          

 public static int maxThree(int a, int b, int c)
 {
 	return Math.max(Math.max(a, b), c);
 }
}
/*
 * 
 * Dynamic table computed:
0,0,0,0,0,0,0,
0,0,0,0,1,1,1,
0,1,1,1,1,2,2,
0,1,1,2,2,2,2,
0,1,1,2,2,3,3,
0,1,2,2,2,3,3,
0,1,2,2,3,3,4,
0,1,2,2,3,4,4,
back table computed:
1,1,1,0,-1,0,
0,-1,-1,1,0,-1,
1,1,0,-1,1,1,
0,1,1,1,0,-1,
1,0,1,1,1,1,
1,1,1,0,1,0,
0,1,1,1,0,1,
Edit Distance table computed:
0,1,2,3,4,5,6,
1,2,3,4,3,4,5,
2,1,2,3,4,3,4,
3,2,3,2,3,4,5,
4,3,4,3,4,3,4,
5,4,3,4,5,4,5,
6,5,4,5,4,5,4,
7,6,5,6,5,4,5,
Longest common sequence:
TCTA
 */