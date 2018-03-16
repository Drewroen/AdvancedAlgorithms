import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DNAProfileGreedy
{
	
	public static void main(String[] args)
	{

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
		
		System.out.print("Enter the number of iterations: ");
		int iterations = in.nextInt();
		System.out.println();
		
		Random rand = new Random();
		int finalScore = 0;
		int[] positions = new int[dnaArray.length];
		int[][] finalProfile = new int[4][dnaArray.length];
		int tempScore = 0;
		String finalConsensus = "";
		int a = 0;
		int b = 0;
		for(int i = 0; i < iterations; i++)
		{
			a = rand.nextInt(dnaArray.length);
			b = rand.nextInt(dnaArray.length - 1);
			if(a == b)
				b = dnaArray.length - 1;
			positions = new int[dnaArray.length];
			seedProfile(dnaArray, motifLength, positions, a, b);
			tempScore = processRest(dnaArray, motifLength, positions, a, b);
			
			if(tempScore > finalScore)
			{
				finalProfile = createProfile(dnaArray, positions, motifLength);
				finalScore = createScore(finalProfile);
			}
		}
		finalConsensus = createMotif(finalProfile);
		
		System.out.println("Consensus: " + finalConsensus);
		System.out.println("Profile Index Vector: " + Arrays.toString(positions));
		System.out.println();
		System.out.println("Profile:");
		System.out.println("A: " + Arrays.toString(finalProfile[0]));
		System.out.println("C: " + Arrays.toString(finalProfile[1]));
		System.out.println("G: " + Arrays.toString(finalProfile[2]));
		System.out.println("T: " + Arrays.toString(finalProfile[3]));
		System.out.println();
		System.out.println("Best Score: " + finalScore);
		
	}
		
	public static void seedProfile(String[] dnaArray, int motifLength, int[] positions, int sample1, int sample2)
	{
		int bestScore = 0;
		int[] tempPos = positions.clone();
		for(int i = 1; i <= dnaArray[0].length() - motifLength + 1; i++)
		{
			for(int j = 1; j <= dnaArray[0].length() - motifLength + 1; j++)
			{
				tempPos[sample1] = i;
				tempPos[sample2] = j;
				int tempScore = createScore(createProfile(dnaArray, tempPos, motifLength));
				if(tempScore > bestScore)
				{
					positions[sample1] = i;
					positions[sample2] = j;
					bestScore = tempScore;
				}
			}
		}
	}
	
	public static int processRest(String[] dnaArray, int motifLength, int[] positions, int sample1, int sample2)
	{
		int bestScore = 0;
		for(int i = 0; i < dnaArray.length; i++)
		{	
			int[] tempPos = positions.clone();
			if((i != sample1) && (i != sample2))
			{
				for(int j = 1; j <= dnaArray[0].length() - motifLength + 1; j++)
				{
					tempPos[i] = j;
					int tempScore = createScore(createProfile(dnaArray, tempPos, motifLength));
					if(tempScore > bestScore)
					{
						positions[i] = j;
						bestScore = tempScore;
					}
				}
			}
		}
		return bestScore;
	}
	
	public static int[][] createProfile(String[] dnaArray, int[] positions, int motifLength)
	{
		String[] section = new String[dnaArray.length];
		for(int i = 0; i < positions.length; i++)
		{
			if(positions[i] != 0)
				section[i] = dnaArray[i].substring(positions[i] - 1, positions[i] - 1 + motifLength);
		}
		
		int[][] scoreTable = new int[4][motifLength];
		for(int i = 0; i < motifLength; i++)
			for(int j = 0; j < positions.length; j++)
			{
				if(positions[j] != 0)
				{
					if (section[j].substring(i, i+1).equalsIgnoreCase("A"))
						scoreTable[0][i]++;
					else if (section[j].substring(i, i+1).equalsIgnoreCase("C"))
						scoreTable[1][i]++;
					else if (section[j].substring(i, i+1).equalsIgnoreCase("G"))
						scoreTable[2][i]++;
					else if (section[j].substring(i, i+1).equalsIgnoreCase("T"))
						scoreTable[3][i]++;
				}
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
			return 'A';
		else if (max(a, b, c, d) == b)
			return 'C';
		else if (max(a, b, c, d) == c)
			return 'G';
		else if (max(a, b, c, d) == d)
			return 'T';
		else
			return ' ';
	}
}
