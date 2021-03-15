package com.refael.findmostfrequentwordswebpage.model;

public class Word implements Comparable<Word> {
    public String word;
    public int count;
    public int size;

    @Override
    public int hashCode() { return word.hashCode(); }

    @Override
    public boolean equals(Object obj) { return word.equals(((Word)obj).word); }

    @Override
    public int compareTo(Word b) { return b.count - count; }

    @Override
    public String toString() {
        return "Length: " + size + "\t" + word + "\t number of times the word appears: " + count;
    }
}
