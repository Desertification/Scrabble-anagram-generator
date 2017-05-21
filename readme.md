# Description

Implement a Scrabble anagram generator. In Scrabble a number of characters are combined to form a word. Each character has a value. The value of the word is determined by the sum of its characters. A word is only valid if it's present in a dictionary.The purpose of the anagram generator is to generate a word given a set of characters with the highest value.

Construct permutations of characters (e.g. given ABC, the permutations are ABC, ACB, BAC, BCA, CAB, CBA)
Find if a word is valid. You may use https://github.com/dwyl/english-words
Find the word with the highest value.
The classes of the Java Collections Framework may be used: https://www.javatpoint.com/collections-in-java

# Hint

Constructing the permutations of a set is O(n!). In order to keep the algorithm manageable it's important to prune partial permutations (e.g. no words start with QK so the partial permutation 

# Extra

Allow the algorithm to append characters to an existing word. The characters in the existing word are fixed and may not be permutated
Include up to two wildcards characters

# Demo

![alt text](https://raw.githubusercontent.com/Desertification/Scrabble-anagram-generator/master/demo.gif)

