package anagram;

import java.util.Comparator;

/**
 * Created by thoma on 20-May-17.
 */
public class ScrabbleComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return Integer.compare(Scrabble.points(o1),Scrabble.points(o2));
	}
}
