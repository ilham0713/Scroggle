package model;

import game.UserResult;

public class GameStats 
{
    private final UserResult userResult;

    public GameStats(UserResult userResult) 
    {
        this.userResult = userResult;
    }

    @Override
    public String toString() 
    {
        String result = "Words - %d\nScore - %d";

        int userWordsCount = this.userResult.getWordCount();
        int userScore = this.userResult.getTotalScore();

        return String.format(result, this.userResult.getWordCount(),
                            this.userResult.getTotalScore());
    }
}
