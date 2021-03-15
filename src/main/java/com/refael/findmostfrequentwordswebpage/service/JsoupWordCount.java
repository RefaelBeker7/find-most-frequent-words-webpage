package com.refael.findmostfrequentwordswebpage.service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.refael.findmostfrequentwordswebpage.model.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JsoupWordCount {

    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Word> getAllWordsCount(String url) throws IOException {
        long time = System.currentTimeMillis();

        Map<String, Word> countMap = new HashMap<String, Word>();

        //connect to wikipedia and get the HTML
        System.out.println("Downloading page from url: " + url);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the actual text from the page, excluding the HTML
        if (doc == null) return null; // Method invocation body() -> 'NullPointerException'
        String text = doc.body().text();

        System.out.println("Analyzing text...");
        //Create BufferedReader so the words can be counted
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("[^א-תÃƒâ€¦Ãƒâ€žÃƒâ€â“א-תÃƒÂ¥ÃƒÂ¤ÃƒÂ¶]+");
            for (String word : words) {
                if (word.length() <= 1) {
                    continue;
                }

                Word wordObj = countMap.get(word);
                if (wordObj == null) {
                    wordObj = new Word();
                    wordObj.word = word;
                    wordObj.count = 0;
                    wordObj.size = word.length();
                    countMap.put(word, wordObj);
                }
                wordObj.count++;
            }
        }
        reader.close();

//        SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
        ArrayList<Word> sortedWords = new ArrayList<Word>(countMap.values());
        ArrayList<Word> returnSortedWords = new ArrayList<Word>();
        int i = -1;
        int maxCount;

        String[] wordsToIgnore = {"של", "את", "על"};

        for (int index = 2; index < 14; index++) {
            maxCount = 0;
            for (int countWord = 0; countWord < sortedWords.size(); countWord++) {
                if (Arrays.asList(wordsToIgnore).contains(sortedWords.get(countWord).word)) {
                    continue;
                }
                if (sortedWords.get(countWord).size == index && maxCount < sortedWords.get(countWord).count) {
                    maxCount = sortedWords.get(countWord).count;
                    i = countWord;
                }
            }
            if (i != -1) {
                System.out.println(sortedWords.get(i).toString());
                returnSortedWords.add(sortedWords.get(i));
                i = -1;
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Finished in " + time + " ms");
        return returnSortedWords;
    }
}