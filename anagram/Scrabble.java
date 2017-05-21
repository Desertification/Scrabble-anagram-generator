package anagram;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by thoma on 20-May-17.
 * <p>
 * Contains utility functions for scrabble game logic
 */
public class Scrabble { // not final unlike utility classes, scrabble rules can depend on locale, inheritance should be allowed
	private Scrabble() {
	}

	public static final short MAX_WORD_LENGTH = 15;
	public static final short MIN_WORD_LENGTH = 2;

	// https://en.wikipedia.org/wiki/Scrabble_letter_distributions
	private static final short[] POINT_DISTRIBUTION = {
			1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
	};
	private static final short[] LETTER_DISTRIBUTION = {
			9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
	};

	// todo probably way to heavy for just class initialisation, but who h*cking cares
	// todo but it might be garbage collected when the class unloads, hmmm...
	public static final Set<String> VALID_WORDS_SET = Collections.unmodifiableSet(loadWords());
	public static final NavigableSet<String> VALID_WORDS_TREE = Collections.unmodifiableNavigableSet(
			new TreeSet<>(VALID_WORDS_SET));

	private static ThreadLocalRandom r = ThreadLocalRandom.current();

	public static boolean isValidSet(String s) {
		if (!isAlphabetic(s)) {
			return false;
		}
		if (!isCorrectLength(s)) {
			return false;
		}
		if (!isValidDistribution(s)) {
			return false;
		}
		return true;
	}

	private static boolean isCorrectLength(String s) {
		return MIN_WORD_LENGTH <= s.length() && s.length() <= MAX_WORD_LENGTH;
	}

	private static boolean isAlphabetic(String s) {
		return s.chars().allMatch(c -> 'a' <= c && c <= 'z');
	}

	public static short points(String s) {
		short points = 0;
		for (char c : s.toCharArray()) {
			points += points(c);
		}
		return points;
	}

	public static short points(char c) {
		return POINT_DISTRIBUTION[charToIndex(c)];
	}

	public static String randomSet() {
		short[] distribution = LETTER_DISTRIBUTION.clone();
		int length = r.nextInt(MIN_WORD_LENGTH, MAX_WORD_LENGTH + 1);
		StringBuilder stringBuilder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			stringBuilder.append(getValueFromDistribution(distribution));
		}
		return stringBuilder.toString();
	}

	private static char getValueFromDistribution(short[] distribution) {
		int randIndex = r.nextInt(distribution.length);
		if (distribution[randIndex] > 0) {
			distribution[randIndex]--;
			return indexToChar(randIndex);
		} else {
			return getValueFromDistribution(distribution);
		}
	}

	private static char indexToChar(int randIndex) {
		return (char) (randIndex + 'a');
	}

	private static boolean isValidDistribution(String s) {
		short[] distribution = LETTER_DISTRIBUTION.clone();
		for (char c : s.toCharArray()) {
			int index = charToIndex(c);
			distribution[index]--;
			if (distribution[index] < 0) {
				return false;
			}
		}
		return true;
	}

	private static int charToIndex(char c) {
		return c - 'a';
	}

	private static Set<String> loadWords() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classloader.getResourceAsStream("sowpods.txt");
		Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream)).lines();
		// fixme: expects a clean formatted text file, no cleanup is done, only a filter to remove the readme part
		return stream.filter(Scrabble::isCorrectLength).collect(Collectors.toSet());
	}
}
