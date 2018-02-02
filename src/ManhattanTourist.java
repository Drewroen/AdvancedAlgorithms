
/**
 * class ManhattanTourist 
 * 
 * Implements the Manhattan Tourist algorithm 
 * Prints the created dynamic table and restores the best path
 * 
 * @author Drew Roen
 * @version v0.1
 */
import java.util.*;
public class ManhattanTourist
{
public static void main (String [] args)
    {
        int [][] eastEdges = {{3,2,4,0},
                              {3,2,4,2},
                              {0,7,3,4},
                              {3,3,0,2},
                              {1,3,2,2}};
        int [][] southEdges = {{1,0,2,4,3},
                               {4,6,5,2,1},
                               {4,4,5,2,1},
                               {5,6,8,5,3}};
 
 /*
   The two tables above encode the grid below.
   points in the grid: "o".
   The numbers represent the distances between two
   horizontal or vertical neighbors
   
   o  - 3 - o - 2 - o - 4 - o - 0 - o
   |        |       |       |       |
   1        0       2       4       3
   |        |       |       |       |   
   o  - 3 - o - 2 - o - 4 - o - 2 - o   
   |        |       |       |       |
   4        6       5       2       1
   |        |       |       |       |     
   o  - 0 - o - 7 - o - 3 - o - 4 - o  
   |        |       |       |       |
   4        4       5       2       1
   |        |       |       |       |  
   o  - 3 - o - 3 - o - 0 - o - 2 - o   
   |        |       |       |       |
   5        6       8       5       3
   |        |       |       |       |   
   o  - 1 - o - 3 - o - 2 - o - 2 - o   
   
  */
 
        int east = 4;   // (4,4) are the coordinates of the bottom rightmost point
        int south = 4;
        
        int [][] paths = new int [south+1][east+1]; 
                //  paths[i][j] holds the largest score of a path to point [i][j]
        
        paths[0][0] = 0;
 // 1. Initialize the leftmost column
 //    paths(i,0) <- paths (i-1, 0) + southEdges(i-1,0)
        for(int i = 1; i < paths.length; i++)
        	paths[i][0] = paths[i-1][0] + southEdges[i-1][0];
 
 // 2. Initialize the top row
 //    paths(0,j) <- paths (0, j-1) + eastEdges(0,j-1)
        for(int j = 1; j < paths[0].length; j++)
        	paths[0][j] = paths[0][j-1] + eastEdges[0][j - 1];
 
 // 3. Fill the dynamic table row by row 
 //    paths(i,j) <- maximum(paths(i-1,j) + southEdges(i-1,j), 
 //                           paths(i,j-1) + eastEdges (i,j-1))
        for(int i = 1; i < paths.length; i++)
        	for(int j = 1; j < paths[0].length; j++)
        		paths[i][j] = Math.max(paths[i-1][j] + southEdges[i-1][j], paths[i][j-1] + eastEdges[i][j-1]);
 
 // 4. Print dynamic table
        for(int[] rowA: paths)
        {
        	for(int colA: rowA)
        		System.out.print(colA + " ");
        	System.out.println();
        }
        System.out.println();
      
 // 5. Restore the path     
 // start with bottom left corner
 // for the current point determine if its score has been computed 
 // from the above neighbor or from the left neighbor
 // set new current point to be the neighbor determined in the previous step
 // stop when (0,0) is reached
        int i = 4;
        int j = 4;
        int directionVal = i + j;
        int[] direction = new int[directionVal];
        while(i != 0 && j != 0)
        {
        	directionVal--;
        	if (paths[i][j] - paths[i - 1][j] == southEdges[i-1][j])
        	{
        		direction[directionVal] = -1;
        		i--;
        	}
        	else
        	{
        		direction[directionVal] = 1;
        		j--;
        	}
        }
        if (j == 0)
        	for(int k = 0; k < direction.length; k++)
        		if (direction[k] == 0)
        			direction[k] = -1;
        if (i == 0)
        	for(int k = 0; k < direction.length; k++)
        		if (direction[k] == 0)
        			direction[k] = 1;
   
 // 6. print the path
        int x = 0;
        int y = 0;
        System.out.println("From ("+ x + ","+ y + ")");
        for(int k: direction)
        {
        	if(k == -1)
        	{
        		x++;
        		System.out.println("Go South to (" + x + "," + y + ")");
        	}
        	else
        	{
        		y++;
        		System.out.println("Go East\t to (" + x + "," + y + ")");
        	}
        }
}  
}
/* Results
 * Dynamic table computed:
 0,  3, 5, 9, 9,
 1,  4, 7,13,15,
 5, 10,17,20,24,
 9, 14,22,22,25,
14, 20,30,32,34,

From (0,0)
Go South to (1,0)
Go East  to (1,1)
Go South to (2,1)
Go East  to (2,2)
Go South to (3,2)
Go South to (4,2)
Go East  to (4,3)
Go East  to (4,4)
*/
