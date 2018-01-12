
/**
 * TreeTraversals.
 * This program implements NextLeaf, AllLeaves, Nextvertex, and ByPass methods 
 * for words of length L over the alphabet of length k. 
   Each word is represented as an array of integers.
 * @authors 1. Lydia Sinapova - main method, nextLeaf
 *          2. -- write your name
 *
 */
import java.util.*;
public class TreeTraversals
{
    public static void main (String[] args)
    {
        int wordLength;  
        int maxDigit;    
        
        Scanner scan = new Scanner(System.in);
        System.out.println("\nThis program implements NextLeaf, AllLeaves" +
        "\nNextvertex, and ByPass methods for words of length L over the alphabet of length k. " +
        "\n Each word is represented as an array of integers"); 
        
                      
 // Read Input parameters   - length of words           
        System.out.print( "\nPlease enter word length as a positive integer: " );   
        wordLength = scan.nextInt();
        System.out.print( "\nPlease enter the number of letters (max digit) as a positive integer: " ); 
        maxDigit = scan.nextInt();   
        
        int [] aWord = new int[wordLength];
 // Testing
        int option = 0;
       do{
        System.out.println("Choose an option:");
        System.out.println("1: Test NextLeaf");
        System.out.println("2: Test AllLeaves");
        System.out.println("3: Test NextVertex");
        System.out.println("4: Test ByPass");
        System.out.println("5: Exit");
        option = scan.nextInt();
        switch(option)
        {
            case 1: // Next leaf
                System.out.println("Enter the current leaf as a sequence of " + wordLength + 
                    " digits with max digit = " + maxDigit);
                for(int i = 0; i< wordLength; i++)
                 aWord[i] = scan.nextInt();
                nextLeaf(aWord, wordLength, maxDigit);
                System.out.println("Next leaf:");
                System.out.println(Arrays.toString(aWord));
                break;
            case 2: // All Leaves
                allLeaves(wordLength, maxDigit);
                break;
            case 3: // Next Vertex
                System.out.println("Enter the level of the current vertex (levels start from 0): ");                  
               
                int level = scan.nextInt();
                if (level > 0)
                 {
                   System.out.println("Enter the current vertex as a sequence of " + level + 
                    " digits with max digit = " + maxDigit);
                }
                   for (int i = 0; i < level; i++)
                     aWord[i] = scan.nextInt();
                   for (int i = level;i <wordLength; i++)
                     aWord[i] = 0;
                 
                 level = nextVertex(aWord, level,  wordLength,  maxDigit);
                 if (level == -1)
                  System.out.println("No more vertices");
                 else {
                        System.out.println("Next vertex at level " + level + ":");
                        System.out.println(Arrays.toString(aWord));
                    }
                 break;
             case 4: // ByPass
             System.out.println("Enter the level of the current vertex (levels start from 0): ");                  
               
                level = scan.nextInt();
                if (level > 0)
                 {
                   System.out.println("Enter the current vertex as a sequence of " + level + 
                    " digits with max digit = " + maxDigit);
                 }
                   for (int i = 0; i < level; i++)
                     aWord[i] = scan.nextInt();
                   for (int i = level;i <wordLength; i++)
                     aWord[i] = 0;
                 
                 level = byPass(aWord, level,  wordLength,  maxDigit);
                 if (level == -1)
                  System.out.println("No more vertices");
                 else {
                        System.out.println("Next byPass vertex at level " + level + ":");
                        System.out.println(Arrays.toString(aWord));
                    }
                 break;
              case 5: System.out.println("End of Program");
        }
      } while(option != 5);
    
    }
 /*------------------------------------------------------------------------------*/
 public static void nextLeaf(int[] aWord, int wordLength, int maxDigit)
 {
     boolean done = false;
     for (int i = wordLength -1; i >= 0 && !done; i--)
     {
         if(aWord[i] < maxDigit)
            {
                aWord[i] = aWord[i] + 1;
                done = true;
            }
         else aWord[i] = 1;
      }
  }
 /*------------------------------------------------------------------------------*/
 public static void allLeaves(int wordLength, int maxDigit)
 {
            
             
 }
 
 /*---------------------------------------------------------------------------------*/
 
 public static int nextVertex(int [] aWord, int level, int wordLength, int maxDigit)
 {
 return -1;  // to be modified
 }
  /*---------------------------------------------------------------------------------*/
     
 public static int byPass(int [] aWord, int level, int wordLength, int maxDigit)
 {
     return -1;  // to be modified
 }     
}
