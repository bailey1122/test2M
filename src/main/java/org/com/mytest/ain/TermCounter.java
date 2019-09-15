package org.com.mytest.ain;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// counts some words
class TermCounter {

    private Map<String, Integer> map;
    private String externalUrl; // identifies the URL some words came from

    protected TermCounter(String externalUrl) {
        this.externalUrl = externalUrl;
        this.map = Collections.synchronizedMap(new HashMap<>()); // implements the synchronized mapping

    }

    // returns the total of all count
    private synchronized int size() {
        int total = 0;
        for (Integer value : map.values()) {
            total += value;
        }
        return total;
    }

    // takes a collection of Elements and counts their words
    void processElements(Elements paragraphs) {
        for (Node node : paragraphs) {
            processTree(node); // calls processTree on each
        }
    }

    // finds TextNodes in a DOM tree and count their words
    private void processTree(Node root) {
        for (Node node : new AinNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    // splits 'text' into words and count them
    private synchronized void processText(String text) {
        String[] array = text.replaceAll("\\pP", " ").
                toLowerCase().
                split("\\s+");

        for (int i = 0; i < array.length; i++) {
            String term = array[i];
            incrementTermCount(term);
        }
    }

    private void put(String term, int count) {
        map.put(term, count);
    }

    private Integer get(String term) {
        Integer count = map.get(term);
        return count == null ? 0 : count; // if the term doesn't appear in the map, it returns 0
    }

    // returns the set of terms that have been counted
    private Set<String> keySet() {
        return map.keySet();
    }

    // print the terms and their counts in arbitrary order
    void printCounts() {
        synchronized (this) {
            for (String key : keySet()) {
                Integer count = get(key);
                System.out.println(key + ", " + count); // get the number of each word
            }
        }
        System.out.println("Total of all counts = " + size() + " "+ externalUrl); // get the number of words
        System.out.println("----------------------------------------------");
    }

    // takes a term and increases by one the counter associated with that term
    private synchronized void incrementTermCount(String term) {
        put(term, get(term) + 1); // replaces the old value
    }
}
