package com.refael.findmostfrequentwordswebpage.service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.refael.findmostfrequentwordswebpage.model.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JsoupWordCount {
    private static final int N = 10;

    public boolean isValid(String url)
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

        Map<String, Integer> countMap = new HashMap<String, Integer>();

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

                countMap.putIfAbsent(word, 0);
                countMap.put(word, countMap.get(word) + 1);
            }
        }
        reader.close();

//        SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());

        Map<String,Integer> sortedMapReverse = countMap.entrySet().
                stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(sortedMapReverse);
        ArrayList<Word> sortedWords = new ArrayList<Word>(Arrays.asList(new Word[N]));
        int[] countLists = new int[N];


        for (int index = 0; index < N; index++) {
            for (Map.Entry<String, Integer> stringIntegerEntry : sortedMapReverse.entrySet()) {
                Map.Entry mapEntry = (Map.Entry) stringIntegerEntry;
                if (((String) mapEntry.getKey()).length() == index + 2 && countLists[index] < (int) mapEntry.getValue()) {
                    countLists[index] = (int) mapEntry.getValue();
                    sortedWords.set(index, new Word((String) mapEntry.getKey(), (int) mapEntry.getValue(), ((String) mapEntry.getKey()).length()));
                    System.out.println(sortedWords.get(index).toString());
                    break;
                }
            }
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Finished in " + time + " ms");
        return sortedWords;
    }
}