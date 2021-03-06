import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DNAProfileGreedyOld
{
	
	public static void main(String[] args) {

		String[] dnaArray = { 
				"aatgcaagaatgaactccttcctggaataccccatacttagcagtggcga",
				"tgctcagcccgagcctacccctcggaccataggattacaactttccagtc",
				"agcgccaacagttgcggcggcgacgaccgcttcctagtgggcaggggggt",
				"tcgccccaccaccaccaccaccaccaccatcgccacccccagccggctac",
				"tccgggaacctgggggtgtcctactcccactcaagttgtggtccaagcta",
				"aacttcagtgcgccttacagcccctacgcgttaaatcaggaagcagacga"};     


		
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the motif length: ");
		int motifLength = in.nextInt();
		System.out.println();
		
		System.out.print("Enter the number of iterations: ");
		int iterations = in.nextInt();
		System.out.println();
		
		int bestScore = -1;
		int[] bestIndex = new int[dnaArray.length];
		Random rand = new Random();
		String[] tempDNA = null;
		for(int i = 0; i < iterations; i++)
		{
			int a = rand.nextInt(dnaArray.length);
			int b = rand.nextInt(dnaArray.length);
			
			tempDNA = dnaArray.clone();		
			
			a = 5;
			String temp = tempDNA[a];
			for(int j = a; j > 0; j--)
				tempDNA[j] = tempDNA[j - 1];
			tempDNA[0] = temp;
			temp = tempDNA[b];
			for(int j = b - 1; j >= 1; j--)
				tempDNA[j + 1] = tempDNA[j];
			tempDNA[1] = temp;
			
			int[] tempIndex = generateGreedyProfile(tempDNA, motifLength);
			int tempScore = createScore(createProfile(tempDNA, tempIndex, motifLength));
			
			for(int j = 0; j < tempIndex.length; j++)
			{
				System.out.println(tempIndex[j]);
				System.out.println(tempDNA[j]);
				System.out.println();
			}
			
			
			if(tempScore > bestScore)
			{
				bestScore = tempScore;
				bestIndex = tempIndex.clone();
				
				int tempInt = bestIndex[1];
				for(int j = 1; j <= b - 1; j++)
					bestIndex[j] = bestIndex[j + 1];
				bestIndex[b] = tempInt;
				tempInt = bestIndex[0];
				for(int j = 0; j <= a - 1; j++)
					bestIndex[j] = bestIndex[j + 1];
				bestIndex[a] = tempInt;
				
			}
			System.out.println();
			for(int j = 0; j < tempIndex.length; j++)
			{
				System.out.println(bestIndex[j]);
				System.out.println(dnaArray[j]);
				System.out.println();
			}
		}
		
		String bestMotif = createMotif(createProfile(dnaArray, bestIndex, motifLength));
		int bestScoreTest = createScore(createProfile(dnaArray, bestIndex, motifLength));
		System.out.println("bestIndex vector: " + Arrays.toString(bestIndex));
		System.out.println("bestScore: " + bestScore + " " + bestScoreTest);
		System.out.println();
		System.out.println("Consensus found: " + bestMotif);
		System.out.println();
		for (int i = 0; i < bestIndex.length; i++)
		{
			System.out.println("String found in DNA line " + (i+1) + ": " + dnaArray[i].substring(bestIndex[i] - 1, bestIndex[i] + motifLength - 1));
		}
	}
	
	public static int[] generateGreedyProfile(String[] dnaArray, int motifLength)
	{
		int[] position = new int[] {1, 1};
		int[] originalPosition = position.clone();
		
		int bestScore = createScore(createProfile(dnaArray, position, motifLength));
		int[] bestIndex = position.clone();
		nextLeaf(position, position.length, dnaArray[0].length() - motifLength + 1);
		while(!Arrays.equals(position, originalPosition))
		{
			int currentScore = createScore(createProfile(dnaArray, position, motifLength));
			if (currentScore > bestScore)
			{
				bestScore = currentScore;
				bestIndex = position.clone();
			}
			nextLeaf(position, position.length, dnaArray[0].length() - motifLength + 1);
		}
		
		for(int i = 2; i < dnaArray.length; i++)
		{
			position = bestIndex.clone();
			int[] newPosition = new int[position.length + 1];
			for(int j = 0; j < position.length; j++)
			{
				newPosition[j] = position[j];
			}
			position = newPosition.clone();
			bestScore = 0;
			for(int j = 1; j < dnaArray[0].length() - motifLength + 1; j++)
			{
				position[position.length - 1] = j;
				int currentScore = createScore(createProfile(dnaArray, position, motifLength));
				if (currentScore > bestScore)
				{
					bestScore = currentScore;
					bestIndex = position.clone();
				}
			}
		}
		
		return bestIndex;
	}
	
	public static int[][] createProfile(String[] dnaArray, int[] positions, int motifLength)
	{
		String[] section = new String[dnaArray.length];
		for(int i = 0; i < positions.length; i++)
			section[i] = dnaArray[i].substring(positions[i] - 1, positions[i] - 1 + motifLength);
		int[][] scoreTable = new int[4][motifLength];
		for(int i = 0; i < motifLength; i++)
			for(int j = 0; j < positions.length; j++)
			{
				if (section[j].substring(i, i+1).equalsIgnoreCase("A"))
					scoreTable[0][i]++;
				if (section[j].substring(i, i+1).equalsIgnoreCase("C"))
					scoreTable[1][i]++;
				if (section[j].substring(i, i+1).equalsIgnoreCase("G"))
					scoreTable[2][i]++;
				if (section[j].substring(i, i+1).equalsIgnoreCase("T"))
					scoreTable[3][i]++;
			}
		return scoreTable;
	}
	
	public static int createScore(int[][] profile)
	{
		int totalScore = 0;
		for(int i = 0; i < profile[0].length; i++)
			totalScore += max(profile[0][i], profile[1][i], profile[2][i], profile[3][i]);
		return totalScore;
	}
	
	public static String createMotif(int[][] profile)
	{
		String motif = "";
		for(int i = 0; i < profile[0].length; i++)
			motif = motif + maxLetter(profile[0][i], profile[1][i], profile[2][i], profile[3][i]);
		return motif;
	}
	
	public static int max(int a, int b, int c, int d)
	{
		return Math.max(a, Math.max(b, Math.max(c, d)));
	}

	public static char maxLetter(int a, int b, int c, int d)
	{
		if (max(a, b, c, d) == a)
			return 'a';
		else if (max(a, b, c, d) == b)
			return 'c';
		else if (max(a, b, c, d) == c)
			return 'g';
		else if (max(a, b, c, d) == d)
			return 't';
		else
			return ' ';
	}
	
	public static void nextLeaf(int[] aWord, int wordLength, int maxDigit)
	{
		boolean done = false;
		for (int i = wordLength - 1; i >= 0 && !done; i--) {
			if (aWord[i] < maxDigit) {
				aWord[i] = aWord[i] + 1;
				done = true;
			} else
				aWord[i] = 1;
		}
	}
}
