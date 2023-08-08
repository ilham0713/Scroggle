/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;



/**
 *
 * @author Ilham Mukati
 */
public class Dictionary {
    
    private static final String WORDS_FILE = "words.txt";
    
    private final Trie trie;
    
    public Dictionary()
    {
        Scanner inputFile = null;
        String word;
        
        try
        {
           trie = new Trie();
           URL url = getClass().getResource(WORDS_FILE);
           File file = new File(url.toURI());
           inputFile = new Scanner(file);
           
           if (inputFile == null) 
               throw new IOException("Invalid URL specified");
           
           while(inputFile.hasNext() == true)
           {
               word = inputFile.next();
               word = word.trim().toLowerCase();
               trie.insert(word);
           }
           
           System.out.println("The data file has been read and the words have been loaded into the trie.");
           
        }
        catch( IOException | URISyntaxException e)
        {
            System.out.println("There was an error reading the file or loading the word(s) into the trie.");
            
            throw new RuntimeException(e);
        }
        
    }
     
    
    public int search(String word)
        {
            return this.trie.search(word);
        }
    
    public boolean prefix(String word)
    {
        return this.trie.prefix(word);
    }
    
}
