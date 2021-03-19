package com.refael.findmostfrequentwordswebpage.controller;

import com.refael.findmostfrequentwordswebpage.model.Word;
import com.refael.findmostfrequentwordswebpage.service.JsoupWordCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class MainController {
    @Autowired
    JsoupWordCount jsoupWordCount;

    Logger LOG = LoggerFactory.getLogger(MainController.class);

    // GET  list/he.wikipedia.org,www.talniri.co.il,www.ynet.co.il/
    @RequestMapping(value = "/list/{urls}", method = RequestMethod.GET)
    @ResponseBody
    public String getListWordsCountByListOfURLs(@PathVariable String[] urls) throws IOException {
        StringBuilder str = new StringBuilder();
        int[] countAppears = new int[12];
        ArrayList<Word> listWordsByCount = new ArrayList<Word>(Arrays.asList(new Word[12]));

        for (String url : urls) {
            if (jsoupWordCount.isValid("https://" + url)) {
                ArrayList<Word> listWordCount = jsoupWordCount.getAllWordsCount("https://" + url);
                for (int indexList = 0; indexList < listWordCount.size(); indexList++) {
                    if (listWordCount.get(indexList) != null && countAppears[indexList] < listWordCount.get(indexList).count) {
                        countAppears[indexList] = listWordCount.get(indexList).count;
                        listWordsByCount.set(indexList, listWordCount.get(indexList));
                    }
                }
            }
        }
        for (Word word : listWordsByCount) {
            if (word == null) break;
            str.append(word.toString()).append("<br>");
        }

        if (str.toString().isEmpty()) {
            LOG.error("Please check website again..");
            return "Please check website again..";
        } else {
            LOG.info("Printed to UI the most frequent words from this URL " + Arrays.toString(urls));
            return str.toString();
        }

    }

    // GET  /he.wikipedia.org
    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    private String getListWordsCountByURL(@PathVariable("url") String url) throws IOException {
        StringBuilder str = new StringBuilder();
        if (jsoupWordCount.isValid("https://" + url)) {
            ArrayList<Word> listWordsCount = jsoupWordCount.getAllWordsCount("https://" + url);
            for (Word word : listWordsCount) {
                if (word == null) break;
                str.append(word.toString()).append("<br>");
            }
            LOG.info("Printed to UI the most frequent " + listWordsCount.size() + " words from this URL " + url);
            return str.toString();
        }
        LOG.error("URL not valid");
        return "Please check website again..";
    }

    @GetMapping("/")
    private String getListWordsCount() throws IOException {
        //http://he.wikipedia.org/,  https://www.ynet.co.il/home/0,7340,L-8,00.html,  http://www.talniri.co.il/

        StringBuilder str = new StringBuilder();
        ArrayList<Word> listWordsCount = jsoupWordCount.getAllWordsCount("http://he.wikipedia.org/");
        for (Word word : listWordsCount) {
            if (word == null) break;
            str.append(word.toString()).append("<br>");
        }
        LOG.info("Printed to UI the most frequent" + listWordsCount.size() + " words from this URL http://he.wikipedia.org/");
        return str.toString();
    }

}
