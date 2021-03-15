package com.refael.findmostfrequentwordswebpage.controller;


import com.refael.findmostfrequentwordswebpage.model.Word;
import com.refael.findmostfrequentwordswebpage.service.JsoupWordCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
    @RequestMapping(value="/list/{urls}", method=RequestMethod.GET)
    @ResponseBody
    public String getListWordsCountByListOfURLs(@PathVariable String[] urls) throws IOException {
        StringBuilder str = new StringBuilder();
        int[] countAppears = new int[12];
        ArrayList<Word> listWordsByCount = new ArrayList<Word>(Arrays.asList(new Word[12]));

        for (String url : urls) {
            if (jsoupWordCount.isValid("https://" + url)) {
                ArrayList<Word> listWordCount = jsoupWordCount.getAllWordsCount("https://" + url);
                for (int indexList = 0; indexList < listWordCount.size(); indexList++) {
                    if (countAppears[indexList] < listWordCount.get(indexList).count) {
                        countAppears[indexList] = listWordCount.get(indexList).count;
                        listWordsByCount.set(indexList, listWordCount.get(indexList));
                    }
                }
            }
        }
        for (Word word : listWordsByCount) {
            if (word == null) break;
            str.append(word.toString()).append(System.getProperty("line.separator"));
        }

        LOG.info("Info: " + str);
        return str.toString();
    }

    // GET  /he.wikipedia.org
    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    private ResponseEntity getListWordsCountByURL(@PathVariable("url") String url) throws IOException {
        //http://he.wikipedia.org/
        //https://www.ynet.co.il/home/0,7340,L-8,00.html
        //http://www.talniri.co.il/
            LOG.info("Info: " + jsoupWordCount.getAllWordsCount("http://" + url));
            return ResponseEntity.ok("{\"status\": \"ok\"}\n");
        }

    @GetMapping("/")
    private ResponseEntity getListWordsCount() throws IOException {
        //http://he.wikipedia.org/
        //https://www.ynet.co.il/home/0,7340,L-8,00.html
        //http://www.talniri.co.il/
        jsoupWordCount.getAllWordsCount("http://he.wikipedia.org/");
        LOG.info("Info: ");
        return ResponseEntity.ok("{\"status\": \"ok\"}\n");
    }
    }
