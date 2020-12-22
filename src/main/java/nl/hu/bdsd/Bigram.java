package nl.hu.bdsd;

import nl.hu.bdsd.FileOps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Bigram {
	private final static int CHAR_SET_SIZE = Tokenizer.ACCEPTABLE_CHARS.length;
	private Double[][] bigrams = new Double[CHAR_SET_SIZE][CHAR_SET_SIZE];
	private int[] bigramTotals = new int[CHAR_SET_SIZE];
	private final static double SMOOTHING = 0.5;

	protected Bigram(File file)
		throws FileNotFoundException, IOException {
		train(file);
	}

	protected Bigram(String filePath)
		throws FileNotFoundException, IOException {
		this(new File(filePath));
	}

	protected void train(File file) throws FileNotFoundException, IOException {
		String fileContents = FileOps.readTextFromFile(file);
		ArrayList<Character> tokens = Tokenizer.tokenize(fileContents);

		for(int i = 0; i < bigrams.length; i++) {
			for(int j = 0; j < bigrams[i].length; j++) {
				bigrams[i][j] = 0.0;
			}
		}

		for(int i = 0; i < tokens.size()-1; i++) {
			char token1 = tokens.get(i);
			char token2 = tokens.get(i+1);
			if(token1 != ' ' && token2 != ' ') {
				incrementValue(tokens.get(i), tokens.get(i+1));
				bigramTotals[getDecimalValue(token1)]++;
			}
		}
	}

	protected void setValue(Character first, Character second, Double value) {
		bigrams[getDecimalValue(first)][getDecimalValue(second)] = value;
	}

	private void incrementValue(Character first, Character second) {
		bigrams[getDecimalValue(first)][getDecimalValue(second)]++;
	}

	private int getDecimalValue(char character) {
		return Integer.valueOf(character) - 97;
	}

	protected Double probability(char char1, char char2) {
		int row = getDecimalValue(char1);
		int col = getDecimalValue(char2);
		double val = bigrams[row][col];
		double numerator = val + SMOOTHING;
		double denominator = bigramTotals[row] + (SMOOTHING * CHAR_SET_SIZE * CHAR_SET_SIZE);
		return numerator / denominator;
	}
}
