package me.grovre.afkunscramble.game;

import me.grovre.afkunscramble.Log;

import java.util.Arrays;
import java.util.Random;

public class WordHints {

    private boolean[] hintStatus;
    private char[] wordChars;
    private char[] hintedWordChars;

    public WordHints(String str) {
        hintStatus = new boolean[str.length()];
        wordChars = str.toCharArray();
        hintedWordChars = new char[wordChars.length];
        Arrays.fill(hintedWordChars, '_');
        new Log("Hints init");
    }

    public WordHints(char[] chars) {
        hintStatus = new boolean[chars.length];
        wordChars = chars;
        hintedWordChars = new char[wordChars.length];
        Arrays.fill(hintedWordChars, '_');
        new Log("Hints init");
    }

    public void resetHintProgress() {
        Arrays.fill(hintStatus, false);
        Arrays.fill(hintedWordChars, '_');
        new Log("Hint progress reset: " + this);
    }

    public String progressHints(int count) {
        if(hasGivenAllHints()) return new String(hintedWordChars);
        if(count == 0) return new String(hintedWordChars);

        Random r = new Random();
        int nextIndexToReveal;
        do {
            nextIndexToReveal = r.nextInt(wordChars.length);
        } while(hintStatus[nextIndexToReveal]);
        hintedWordChars[nextIndexToReveal] = wordChars[nextIndexToReveal];

        new Log("Hints progressed by 1: " + this);
        return progressHints(count-1);
    }

    public String getHintedChars() {
        new Log("Hinted chars received: " + this);
        return new String(hintedWordChars);
    }

    public boolean hasGivenAllHints() {
        for(boolean b : hintStatus) {
            if(!b) {
                new Log("Has given all hints: " + this);
                return false;
            }
        }
        new Log("Has not given all hints: " + this);
        return true;
    }
}
