/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import model.WordResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Ilham Mukati
 */
public class UserResult {
    
    int totalScore;
    
    
    private final Map <String, WordResult>
    wordToResultMap = new LinkedHashMap<>();
    
    public void add(String word, WordResult result)
    {
        this.wordToResultMap.put(word, result);
        totalScore += result.getScore();
        
    }
    
    public WordResult get(String word)
    {
        return this.wordToResultMap.get(word);
    }
    
    public int getTotalScore()
    {
       return this.totalScore;
    }
    
    public Map<String, WordResult> all()
    {
        return this.wordToResultMap;
    }
    
    
    public boolean exist(String word)
    {
        return this.wordToResultMap.containsKey(word);
    }
    
    public int getWordCount()
    {
        return this.wordToResultMap.size();
    }
}
    

