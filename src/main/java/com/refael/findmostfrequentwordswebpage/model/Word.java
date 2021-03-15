package com.refael.findmostfrequentwordswebpage.model;

public class Word implements Comparable<Word> {
    public String word;
    public int count;
    public int length;

    public Word(String word, int count, int length) {
        this.word = word;
        this.count = count;
        this.length = length;
    }

    @Override
    public int hashCode() { return word.hashCode(); }

    @Override
    public boolean equals(Object obj) { return word.equals(((Word)obj).word); }

    @Override
    public int compareTo(Word b) { return b.count - count; }

    @Override
    public String toString() {
        return "Length: " + length + "\t" + word + "\t number of times the word appears: " + count;
    }
}
