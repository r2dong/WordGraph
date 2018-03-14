# Overview
This program determines how closely words are related in a graph by measuring path length from one word to another.
An edge is established between words if one can be transformed into another by exactly one substitution, deletion, addition, or sbustitution in either word.
# Assumptions
No special characters such as "@" or "(" in a word
# Usage
Constructor of WordGraph takes a List<String> object which has all the words to be considered. A WordGraph instance has several member methods.
- int numberOfComponents(): returns the number of connected components in the WordGraph instance as an integer
- List<String> shortestPath(String word1, String word2): returns the words on the path from word1 to word2 in the WordGraph instance. An empty list is returned if such path does not exist.
