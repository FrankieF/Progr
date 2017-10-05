using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Boggle
{
    class TestDriver
    {
        static void Main(string[] args)
        {
            Boggle boggle = new Boggle();
            string fileName = "../../dictionary.txt";
            boggle.LoadDictionary(fileName);
            boggle.CreateRandomBoard(100,100);
            boggle.CheckLetters();
            boggle.PrintAllWords();
            Console.ReadLine();
        }
    }
}
