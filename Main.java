import anagram.Benchmark;
import anagram.Scrabble;
import anagram.ScrabbleAnagramGenerator;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by thoma on 20-May-17.
 */
public class Main {
	/**
	 * invalid input means that the set is too long or too short,
	 * contains invalid letters, or some letter are exhausted
	 * @param args
	 */
	public static void main(String[] args) {
		int wildcards = 1;
		String set = Scrabble.randomSet();
//		String set = "evbatelhonscei"; // set with a lot of anagrams
		System.out.println("set: " + set);
		System.out.println("wildcards: " + wildcards);
		System.out.println();

		Collection<String> anagrams = ScrabbleAnagramGenerator.generateAnagrams(set, wildcards);
		System.out.println("found: " + anagrams.size() + " anagrams");

		// ignore extra delay from this benchmark
		Benchmark.test_ms(() -> ScrabbleAnagramGenerator.generateAnagrams(set, wildcards));
		System.out.println();

		System.out.println("<points : anagram>");
		for (String s : anagrams) {
			System.out.println(Scrabble.points(s) + " : " + s);
		}

	}
}
