package anagram;

import javafx.collections.transformation.SortedList;

import java.util.*;

public class ScrabbleAnagramGenerator {
	private String set;
	private int wildcardCount;
	private SortedSet<String> sets = new TreeSet<>();
	private ArrayList<String> anagrams = new ArrayList<>();

	private ScrabbleAnagramGenerator(String set, int wildcardCount) {
		this.set = set;
		this.wildcardCount = wildcardCount;
	}

	// todo this looks weird, move to own class?
	public static Collection<String> generateAnagrams(String set, int wildcards) {
		set = set.toLowerCase().trim();
		validateInput(set, wildcards);
		ScrabbleAnagramGenerator scrabbleAnagramGenerator = new ScrabbleAnagramGenerator(set, wildcards);
		scrabbleAnagramGenerator.fillWildcards();
		for (String s : scrabbleAnagramGenerator.sets) {  // fixme: this is scandalously inefficient
			scrabbleAnagramGenerator.permutation(s);
		}
		scrabbleAnagramGenerator.anagrams.sort(new ScrabbleComparator().reversed());
		return scrabbleAnagramGenerator.anagrams;
	}

	private static void validateInput(String set, int wildcardCount) {
		RuntimeException invalidInputException = new RuntimeException("invalid input");
		int setLength = set.length() + wildcardCount;
		if (2 < wildcardCount || wildcardCount < 0) {
			throw invalidInputException;
		}
		if (setLength > Scrabble.MAX_WORD_LENGTH) {
			throw invalidInputException;
		}
		if (setLength < Scrabble.MIN_WORD_LENGTH) {
			throw invalidInputException;
		}
		if (!Scrabble.isValidSet(set)) {
			throw invalidInputException;
		}
	}

	/**
	 * get all the possible combinations with wildcards, sort the set and put it in a sorted set
	 */
	private void fillWildcards() {
		// todo optimize

		if (wildcardCount == 0) {
			sets.add(set);
			return;
		}

		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add(set);

		for (int i = 0; i < wildcardCount; i++) {
			arrayList = addWildcards(arrayList);
		}

		for (String s : arrayList) {
			char[] chars = s.toCharArray();
			Arrays.sort(chars);
			sets.add(new String(chars));
		}
	}

	private ArrayList<String> addWildcards(ArrayList<String> arrayList) {
		// todo optimize

		ArrayList<String> arrayList2 = new ArrayList<>();
		for (String s : arrayList) {
			for (int c = 'a'; c <= 'z'; c++) {
				arrayList2.add(s + (char) c);
			}
		}
		return arrayList2;
	}

	private void permutation(String str) {
		permutation("", str);
	}

	private void permutation(String prefix, String str) {
		int n = str.length();
		if (n == 0) {
			// check if full length permutation exists in list
			if (Scrabble.VALID_WORDS_SET.contains(prefix)) {
				anagrams.add(prefix);
			}
		} else {
			if (!prefix.isEmpty() && !anyWordsStartWith(prefix)) {
			} else {
				// check if partial permutation exists in list
				if (Scrabble.VALID_WORDS_SET.contains(prefix)) {
					anagrams.add(prefix);
				}
				for (int i = 0; i < n; i++) {
					permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
				}

			}
		}
	}

	private static boolean anyWordsStartWith(String prefix) {
		int length = prefix.length();
		String next = prefix.substring(0, length - 1) + (char) (prefix.charAt(length - 1) + 1);
		SortedSet<String> subSet = Scrabble.VALID_WORDS_TREE.subSet(prefix, next);
		return !subSet.isEmpty();
	}
}
