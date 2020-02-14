using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Linq;
using System.IO;

namespace Boggle
{
    /// <summary>
    /// Author: Frankie Fasola
    /// Date: 3/27/17
    /// 
    /// This class is designed to find all possible words on a Boggle board.
    /// It uses dynamic programming to recursively search through the board.
    /// There are 9 base cases when searching, the 8 letters around the current letter
    /// and the last case is returning to the letter that came before it.
	/// The estimated runtime of the solver is O(D x M x N x W).
	/// D is the longest word in the dictionary, M and N represent the board dimensions
	/// and W is the longest word on the board. The program forces M and N to be equal
	/// which makes the O(D X N^2 x W).
    /// 
    /// ***
    /// This Boggle solver does not represent Qu as Q on the board. In most games of Boggle,
    /// the letter Q is interpreted as Qu, but this solver does not append a U to a Q.
    /// ***
    /// 
    /// </summary>
    class Boggle
    {
        private Trie trie;
        private char[,] board;
        private HashSet<string> words = new HashSet<string>();
        private const int OutOfBoundsLeft = -1;
        private const int MinimumWordLength = 3;

        /// <summary>
        /// Consturct a random Boggle board.
        /// </summary>
        /// <param name="length">The length dimension of the board.</param>
        /// <param name="width">The width dimension of the board.</param>
        public void CreateRandomBoard(int length, int width)
        {
            if (length != width)
                throw new Exception("ERROR: The board dimensions are not equal. The board must be a square.");

            char[] letters = { 'a', 'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            Random rand = new Random();
            board = new char[length,width];
            for (int i = 0; i < length; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    board[i, j] = letters[rand.Next(26)];
                }
            }
        }

        /// <summary>
        /// Consturct the Boggle board from a 2D array.
        /// </summary>
        /// <param name="length">The length dimension of the board.</param>
        /// <param name="width">The width dimension of the board.</param>
        /// <param name="letters">The letters on the board.</param>
        public void CreateBoard(int length, int width, char[,] letters)
        {
            if (length != width)
                throw new Exception("ERROR: The board dimensions are not equal. The board must be a square.");

            board = new char[length, width];
            for (int i = 0; i < length; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    board[i, j] = letters[i, j];
                }
            }
        }

        /// <summary>
        /// Go through each letter of the board and check which words can be made.
        /// </summary>
        public void CheckLetters()
        {
            if (board == null)
                throw new Exception("ERROR: The board is null.");

            int row = board.GetLength(0);
            int column = board.GetLength(1);
            Node node = trie.root;
            bool[,] moves = CreatePossibleMovesArray(row, column);
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < column; j++)
                {
                    if (node.nodes[board[i, j] - 'a'] != null)
                    {
                        string word = "" + board[i, j];
                        SearchWord(board, i, j, word, node.nodes[board[i, j] - 'a'], moves);
                    }
                }
            }
        }

        /// <summary>
        /// Creates a new array and sets all the values to false;
        /// </summary>
        /// <param name="row">The number of rows.</param>
        /// <param name="column">The number of columns.</param>
        /// <returns>A new array with all values set to false.</returns>
        private bool[,] CreatePossibleMovesArray(int row, int column)
        {
            bool[,] moves = new bool[row, column];
            for (int i = 0; i < row; i++)
                for (int j = 0; j < column; j++)
                    moves[i, j] = false;
            return moves;
        }

        /// <summary>
        /// Searches the through the board and finds all possible words.
        /// </summary>
        /// <param name="board">The boggle board.</param>
        /// <param name="row">The current row on the board.</param>
        /// <param name="column">The current column on the board.</param>
        /// <param name="word">The current word from the connected letters.</param>
        /// <param name="node">The Trie tree to check for valid words.</param>
        /// <param name="moves">The possible moves on the board from the current letter.</param>
        private void SearchWord(char[,] board, int row, int column, string word, Node node, bool[,] moves)
        {
            // Only check the word if it is at least 3 characters to increase performance.
            if (word.Length >= MinimumWordLength && IsValidWord(word))
                words.Add(word);

            // Check the parameters passed were valid before searching for adjacent letters.
            if (IsValidMove(row, column, moves))
            {
                // Mark this character as visited to avoid endless loops.
                moves[row, column] = true;
                for (int f = 0; f < node.nodes.Length; f++)
                {
                    // Only check adjacent characters if the current node has a link to the letter.
                    // This saves on performance by only checking for valid words.
                    if (node.nodes[f] != null)
                    {
                        char letter = (char)(f + 'a');

                        // Check each adjacent character starting with the character one up and one to the left,
                        // then search clock-wise around this character.
                        if (IsValidMove(row - 1, column - 1, moves) && board[row - 1, column - 1] == letter)
                            SearchWord(board, row - 1, column - 1, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row - 1, column, moves) && board[row - 1, column] == letter)
                            SearchWord(board, row - 1, column, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row - 1, column + 1, moves) && board[row - 1, column + 1] == letter)
                            SearchWord(board, row - 1, column + 1, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row, column + 1, moves) && board[row, column + 1] == letter)
                            SearchWord(board, row, column + 1, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row + 1, column + 1, moves) && board[row + 1, column + 1] == letter)
                            SearchWord(board, row + 1, column + 1, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row + 1, column, moves) && board[row + 1, column] == letter)
                            SearchWord(board, row + 1, column, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row + 1, column - 1, moves) && board[row + 1, column - 1] == letter)
                            SearchWord(board, row + 1, column - 1, word + letter, node.nodes[f], moves);
                        if (IsValidMove(row, column - 1, moves) && board[row, column - 1] == letter)
                            SearchWord(board, row, column - 1, word + letter, node.nodes[f], moves);
                    }
                }
                // Reset this character as unvisited so other combinations of characters can visit.
                moves[row, column] = false;
            }

        }

        /// <summary>
        /// Prints all the words inside of the HashSet.
        /// </summary>
        public void PrintAllWords()
        {
            Console.WriteLine("Found Words:");
            int i = 1;
            foreach (String word in words)
                Console.WriteLine("{0}. {1}", i++, word);
        }

        /// <summary>
        /// Checks if the row and column position is inside the bounds of the board
        /// and checks if the character has been visited already.
        /// </summary>
        /// <param name="node">The current node of the Trie tree.</param>
        /// <param name="letter">The adjacent letter on the board</param>
        /// <param name="moves">The possible moves on the board from the current letter.</param>
        /// <returns>True if the current node has a pointer to the adjacent letter.</returns>
        private bool IsValidMove(int row, int column, bool[,] moves)
        {
            return row > OutOfBoundsLeft && row < board.GetLength(0) &&
                   column > OutOfBoundsLeft && column < board.GetLength(1) &&
                   moves[row, column] == false;
        }

        /// <summary>
        /// Checks if the word is in the Trie tree.
        /// </summary>
        /// <param name="word">The word to check for inside the Trie tree.</param>
        /// <returns>True if the word is inside the Trie, otherwise false.</returns>
        private bool IsValidWord(string word)
        {
            Node node = trie.root;
            int i = 0;
            for (; i < word.Length && node.nodes[word[i] - 'a'] != null; i++)
                node = node.nodes[word[i] - 'a'];
            return i == word.Length && node.EndOfWord;
        }


        /// <summary>
        /// Loads a dictionary of valid words for the game of Boggle.
        /// The dictionary is the enable dictrionary from Scrabble.
        /// http://www.puzzlers.org/pub/wordlists/enable1.txt
        /// </summary>
        /// <param name="fileName">The name of the file.</param>
        /// <remarks>The path to the current directory will be appended to the fileName.</remarks>
        public void LoadDictionary(string fileName)
        {
            if (fileName == null)
                throw new Exception("ERROR: fileName is null!.");
            if (fileName.Equals(""))
                throw new Exception("ERROR: fileName is empty!.");

            string path = Path.Combine(Environment.CurrentDirectory, fileName);
            //Read every line of the file into a list
            // and then insert each word into the Trie.
            List<String> words = File.ReadAllLines(path).ToList();
            trie = new Trie();
            foreach (var word in words)
                trie.Insert(word);
        }

    }
}
   
