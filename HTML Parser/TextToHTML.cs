using System;
using System.Text;
using System.IO;

namespace HTML_Parser
{
    /// <summary>
    /// Author: Frankie Fasola
    /// Date: 3/29/17
    /// 
    /// This class is used to convert plain text into HTML markup text.
    /// </summary>

    class TextToHTML
    {
        /// These bools are used to keep track of which tags are currently open.
        private bool isParagraph = false;
        private bool isBold = false;
        private bool isItaltic = false;
        private bool isHeader = false;
        private bool isListElement = false;
        private bool isOrderedList = false;
        private bool isUnorderedList = false;

        /// <summary>
        /// Converts plain text to HTML format with tags. Plain text is assumed to be formatted correctly.
        /// </summary>
        /// <param name="inputMarkdownFilePath">The path to the plain text file.</param>
        /// <param name="outputMarkdownFilePath">The path to output the HTML mark up file to.</param>
        /// <remarks>This code was tested using Notepad++; it may produce different results when 
        /// opening the output file in other text editors, i.e. Notepad.</remarks>
        public void MarkdownToHtml(string inputMarkdownFilePath, string outputMarkdownFilePath)
        {
            if (inputMarkdownFilePath == null)
                throw new Exception("ERROR: inputMarkdownFilePath is null!.");
            if (outputMarkdownFilePath == null)
                throw new Exception("ERROR: outputMarkdownFilePath is null!.");
            if (inputMarkdownFilePath.Equals(""))
                throw new Exception("ERROR: inputMarkdownFilePath is empty!.");
            if (outputMarkdownFilePath.Equals(""))
                throw new Exception("ERROR: outputMarkdownFilePath is empty!.");

            string line = "";
            string markDownText = "";
            string buffer = "";
            using (StreamReader reader = new StreamReader(inputMarkdownFilePath))
            using (StreamWriter writer = new StreamWriter(outputMarkdownFilePath))
            {
                // We want the reader to write everytime we call it to ensure everything is written.
                writer.AutoFlush = false;
                while ((line = reader.ReadLine()) != null)
                {
                    // Reset the markDownText to avoid writing anything twice.
                    markDownText = "";
                    // First encode the current line
                    line = Encode(line);
                    if (line.Length == 0)
                    {
                        // Check if the line before the current line was apart of a paragraph or list.
                        if (isParagraph)
                        {
                            markDownText = buffer.Remove(buffer.Length-1) + "</p>" + System.Environment.NewLine;
                            buffer = "";
                            isParagraph = false;
                        }
                        else if (isOrderedList)
                        {
                            markDownText = markDownText.Trim();
                            markDownText +="</ol>" + System.Environment.NewLine;
                            isOrderedList = false;
                        }
                        else if (isUnorderedList)
                        {
                            markDownText = markDownText.Trim();
                            markDownText +="</ul>" + System.Environment.NewLine;
                            isUnorderedList = false;
                        }
                    }
                    else
                        markDownText += line;

                    // If the current text is a paragraph we buffer the input into a string.
                    // This avoids writing to the output file before closing the paragraph tag.
                    if (isParagraph)
                        buffer += markDownText + "\n";
                    else
                    {
                        writer.WriteLine(markDownText);
                        writer.Flush();
                        markDownText = "";
                    }
                }

                // After reading the file perform final checks to add appropriate closing tag.
                if (isParagraph)
                    markDownText = buffer.Remove(buffer.Length-1) + "</p>";
                else if (isOrderedList)
                {
                    markDownText = "</ol>" + System.Environment.NewLine;
                    isOrderedList = false;
                }
                else if (isUnorderedList)
                {
                    markDownText = "</ul>" + System.Environment.NewLine;
                    isUnorderedList = false;
                }
                writer.WriteLine(markDownText);
                writer.Flush();
                writer.Close();
            }
            
        }

        /// <summary>
        /// Encodes a line of plain text to HTML mark up text.
        /// </summary>
        /// <param name="line">The string to encode to HTML mark up.</param>
        /// <returns>A string encoded with HTML mark ups.</returns>
        /// <remarks> Can return a null or empty string if the string passed in is null or empty.</remarks>
        private String Encode(string line)
        {
            if (line == null)
                return line;
            if (line.Length == 0)
                return line;
            
            int headers = 0;
            StringBuilder htmlString = new StringBuilder();
            // Check if the current line is the start of a new paragraph.
            if (!isParagraph && (line[0] != '#' && 
                line[0] != '-' && line[0] != '+' &&
                line[0] != '*'))
            {
                htmlString.Append("<p>");
                isParagraph = true;
            }

            for (int i = 0; i < line.Length; i++)
            {
                char letter = line[i];
                switch (letter)
                {
                    case '*':
                        // The asterisk has 3 possible encodings.
                        // * - Italic, ** - Bold, * - As a list bullet point.
                        // Start by checking for bold
                        if ((i+1) < line.Length && line[i + 1] == '*')                           
                        {
                            // Check for start of paragraph.
                            if (i == 0)
                            {
                                htmlString.Append("<p>");
                                isParagraph = true;
                            }
                            // We know another asterisk is the next character.
                            // Advance i by one to skip the asterisk and add the strong tag.
                            i++;
                            if (isBold)
                                htmlString.Append("</strong>");
                            else
                                htmlString.Append("<strong>");
                            isBold = !isBold;
                        }
                        // Next check if it is the start of a list.
                        else if (i == 0 && (i + 1) < line.Length && line[i + 1] == ' ')
                        {                            
                            htmlString.Append("<li>");
                            isUnorderedList = true;
                            isListElement = true;
                        }
                        // It must be italic then.
                        else 
                        {
                            // Check for the start of a paragraph.
                            if (i == 0)
                            {
                                htmlString.Append("<p>");
                                isParagraph = true;
                            }
                            if (isItaltic)
                                htmlString.Append("</em>");
                            else
                                htmlString.Append("<em>");
                            isItaltic = !isItaltic;
                        }
                        break;
                    case '_':
                        // The underscore has 2 possible encodings.
                        // _ - Italic or __ - Bold.
                        if ((i+1) < line.Length && line[i+1] == '_')
                        {
                            // We know the next character is an underscore and this is a strong tag,
                            // advance i to skip the next character.
                            i++;
                            if (isBold)
                                htmlString.Append("</strong>");
                            else 
                                htmlString.Append("<strong>");
                            isBold = !isBold;
                        }
                        else 
                        {
                            if (isItaltic)
                                htmlString.Append("</em>");
                            else
                                htmlString.Append("<em>");
                            isItaltic = !isItaltic;
                        }
                        break;
                    case '#':
                        if (!isHeader)
                        {
                            // Count the number of # signs and append the header tag,
                            // then skip over the # characters and move i past them.
                            headers = GetHeaderNumber(line, i);
                            htmlString.Append("<h" + headers + ">");
                            i += headers;
                            isHeader = true;
                        }
                        else
                        {
                            // Remove any trailing whitespace characters before appending the tag.
                            // Then set i to stop processing additional # characters.
                            string s = htmlString.ToString().Trim();
                            htmlString = new StringBuilder(s);
                            htmlString.Append("</h" + headers + ">");
                            isHeader = false;
                            i += line.Length;
                        }
                        break;
                    case '&':
                        // If the character after the & is not c, then it is not a copyright symbol.
                        if (i + 1 < line.Length && line[i + 1] != 'c')
                            htmlString.Append("&amp;");
                        else
                            htmlString.Append(letter);
                        break;
                    case '<':
                        htmlString.Append("&lt;");
                        break;
                    case '>':
                        htmlString.Append("&gt;");
                        break;
                    case '-':
                    case '+':
                        // If the + or - sign is the first character then it is a bullet point for a list.
                        if (!isListElement && !isParagraph && i == 0)
                        {
                            htmlString.Append(!isUnorderedList ? "<ul>\n" + "<li>" : "<ul>");
                            isListElement = true;
                            isUnorderedList = true;
                        }
                        else
                            htmlString.Append(letter);
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        // All of the digits are encoded the same. They can start an ordered list
                        // or be encoded as a digit.
                        if (i == 0 && (i + 1) < line.Length && line[i + 1] == '.')
                        {
                            // The paragraph tag was appended, so clear the string.
                            // Set i to skip the period character.
                            htmlString.Clear();
                            isParagraph = false;
                            isListElement = true;
                            i += 2;
                            if (!isOrderedList)
                            {
                                htmlString.Append("<ol>\n<li>");
                                isOrderedList = true;
                            }
                            else
                                htmlString.Append("<li>");                                
                            
                        }
                        else
                            htmlString.Append(letter);
                        break;
                    default:
                        htmlString.Append(letter);
                        break;
                }
            }
            // Check if the header or list tag needs to be closed.
            if (isHeader)
            {
                string s = htmlString.ToString().Trim();
                htmlString = new StringBuilder(s);
                htmlString.Append("</h" + headers + ">\n");
                isHeader = false;
            }
            if (isListElement)
            {
                htmlString.Append("</li>");
                isListElement = false;
            }
            return htmlString.ToString();
        }

        /// <summary>
        /// Counts the number of # signs to determine the header number.
        /// </summary>
        /// <param name="line">The string header to count.</param>
        /// <param name="i">The current position in the string.</param>
        /// <returns>The number of # signs in the header.</returns>
        private int GetHeaderNumber(string line, int i)
        {
            int headers = 0;
            while (i < line.Length && line[i] == '#')
            {
                headers++;
                i++;
            } 
            return headers;
        }
    }
}
