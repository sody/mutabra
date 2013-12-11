package com.mutabra.services.game;

import java.util.Random;

public class Names {
    private static final Names DEFAULT_NAMES = new Names(
            "aeiou",
            "bcdfghjklmnpqrstvwxy",
            "cvccvc", "cvccv", "vccvcv", "vccvc");

    private final Random random = new Random();

    private final String vowels;
    private final String consonants;
    private final String[] patterns;

    private Names(final String vowels, final String consonants, final String... patterns) {
        this.vowels = vowels;
        this.consonants = consonants;
        this.patterns = patterns;
    }

    public String nextName() {
        final String word = nextWord();
        return Character.toTitleCase(word.charAt(0)) + word.substring(1);
    }

    public String nextWord() {
        final int index = random.nextInt(patterns.length);
        return nextWord(patterns[index]);
    }

    public String nextWord(final String pattern) {
        final StringBuilder word = new StringBuilder();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == 'c') {
                word.append(nextConsonant());
            } else if (c == 'v') {
                word.append(nextVowel());
            }
        }
        return word.toString();
    }

    public char nextVowel() {
        final int index = random.nextInt(vowels.length());
        return vowels.charAt(index);
    }

    public char nextConsonant() {
        final int index = random.nextInt(consonants.length());
        return consonants.charAt(index);
    }

    public static String generate() {
        return DEFAULT_NAMES.nextName();
    }
}