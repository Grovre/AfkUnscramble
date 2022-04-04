package me.grovre.afkunscramble.game;

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
    }

    public WordHints(char[] chars) {
        hintStatus = new boolean[chars.length];
        wordChars = chars;
        hintedWordChars = new char[wordChars.length];
        Arrays.fill(hintedWordChars, '_');
    }

    public void resetHintProgress() {
        Arrays.fill(hintStatus, false);
        Arrays.fill(hintedWordChars, '_');
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

        return progressHints(count-1);
    }

    public String getHintedChars() {
        return new String(hintedWordChars);
    }

    public boolean hasGivenAllHints() {
        for(boolean b : hintStatus) {
            if(!b) {
                return false;
            }
        }
        return true;
    }
}
