
public class LargestRectangle {
	public static void main(String[] args)
	{
		int[][] grid = new int[10][10];
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[i].length; j++)
				grid[i][j] = 0;
		grid[1][1] = 1;
		grid[4][5] = 1;
		grid[1][5] = 1;
		grid[8][8] = 1;
		grid[9][4] = 1;
		
		printGrid(grid);
	}
	
	public static void printGrid(int[][] grid)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}			
	}
}
