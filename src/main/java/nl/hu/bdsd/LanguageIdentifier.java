package nl.hu.bdsd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

public class LanguageIdentifier
{
	private Hashtable<String, Bigram> bigrams;
	private ArrayList<String> keys;

	public LanguageIdentifier()
	{
		bigrams = new Hashtable<String, Bigram>();
		keys = new ArrayList<String>();
	}

	public void train(String key, File file) throws FileNotFoundException, IOException
	{
		if (!bigrams.containsKey(key))
		{
			bigrams.put(key, new Bigram(file));
			addKey(key);
		} else
			bigrams.get(key).train(file);
	}

	public String evaluate(String s)
	{
		double totals[] = new double[keys.size()];
		Bigram bigrams[] = new Bigram[keys.size()];
		for(int i = 0; i < keys.size(); i++)
		{
			totals[i] = 0;
			bigrams[i] = this.bigrams.get(keys.get(i));
		}

		ArrayList<Character> tokens = Tokenizer.tokenize(s);

		for(int i = 0; i < tokens.size() - 1; i++)
		{
			char char1 = tokens.get(i);
			char char2 = tokens.get(i+1);
			if(char1 != ' ' && char2 != ' ')
			{
//				System.out.println("BIGRAM: " + char1 + char2);
				for(int j = 0; j < bigrams.length; j++)
				{
					double prob = bigrams[j].probability(char1,  char2);
					totals[j] += Math.log10(prob);
//					System.out.println(keys.get(j).toUpperCase() + ": P(" + char1 + "," + char2 + ") = " + prob + " ==> log prob of sequence so far: " + totals[j]);
				}
//				System.out.println();
			}
		}

		double max = -Double.MAX_VALUE;
		int maxIndex = -1;
		for(int i = 0; i < totals.length; i++)
		{
			if(totals[i] > max)
			{
				max = totals[i];
				maxIndex = i;
			}
		}
		return keys.get(maxIndex);
	}

	private void addKey(String key) {
		keys.add(key);
	}
}
