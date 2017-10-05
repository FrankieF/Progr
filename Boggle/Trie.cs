namespace Boggle
{
     /// <summary>
    /// Nested Node class used to hold the data in the Trie tree.
    /// </summary>
    public class Node
    {
        readonly char value;
        // A node can only have 26 possible values so we can use an array
        // because no resizing is needed.
        public Node[] nodes = new Node[26];
        public bool EndOfWord { get; set;  }

        /// <summary>
        /// Creates a new Node with value of letter.
        /// </summary>
        /// <param name="value">The letter to be stored in the node.</param>
        public Node(char value)
        {
            this.value = value;
            EndOfWord = false;
        }
    }

     /// <summary>
    /// Basic implementation of a Trie data structure to store the dictionary words.
    /// </summary>
    public class Trie
    {
        public readonly Node root;

        /// <summary>
        /// Creates a new Trie and sets the root to null. 
        /// </summary>
        public Trie()
        {
            root = new Node('\0');
        }

        /// <summary>
        /// Inserts a word into the Trie.
        /// </summary>
        /// <param name="word">The word to be added to the tree.</param>
        public void Insert(string word)
        {
            Node node = root;
            // Check if each letter of the word is already a node in the Trie,
            // if not, create a new node representing that letter of the word.
            // If the letter is the end of the word, set the EndOfWord flag to true.
            for (int i = 0; i < word.Length; i++)
            {
                int letter = word[i] - 'a';
                if (node.nodes[letter] == null)
                {
                    node.nodes[letter] = new Node(word[i]);
                    if (i == word.Length - 1)
                        node.nodes[letter].EndOfWord = true;
                }
                node = node.nodes[letter];
            }
        }
    }
}
