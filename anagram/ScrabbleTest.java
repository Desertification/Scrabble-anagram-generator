package anagram;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by thoma on 20-May-17.
 */
class ScrabbleTest {
	@Test
	void randomSet() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(Scrabble.isValidSet(Scrabble.randomSet()));
		}
	}

	@Test
	void points_string() {
		assertEquals(8,Scrabble.points("unittest"));
	}

	@Test
	void points_char() {
		assertEquals(1,Scrabble.points('a'));
		assertEquals(5,Scrabble.points('k'));
		assertEquals(10,Scrabble.points('z'));
	}

	@Test
	void chars_valid() {
		assertTrue(Scrabble.isValidSet("abcdefghijklmno"));		// alphabet
		assertFalse(Scrabble.isValidSet("hélicoptère"));		// unicode
		assertFalse(Scrabble.isValidSet("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // length
	}

}