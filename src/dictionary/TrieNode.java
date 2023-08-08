/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

/**
 *
 * @author Ilham Mukati
 */
public class TrieNode {
    private static final int ALPHABET_COUNT = 26;

    private final TrieNode[] children;
    private int score;

    TrieNode() 
    {
        this.children = new TrieNode[ALPHABET_COUNT];
        this.score = 0;
    }

    public TrieNode[] getChildren() 
    {
        return children;
    }

    public int getScore() 
    {
        return score;
    }

    public void setScore(int score) 
    {
        this.score = score;
    }
}
