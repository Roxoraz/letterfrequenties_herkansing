package nl.hu.bdsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileOps {

	public static String readTextFromFile(File file)
		throws FileNotFoundException, IOException {

		BufferedReader br  = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		
		while(line != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
			line = br.readLine();
		}
		br.close();
		return sb.toString();
	}
}
