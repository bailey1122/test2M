package org.com.mytest.ain;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// a crawler
public class GeneralCrawler implements Runnable {

    private final String url;
    private java.sql.Connection connectionFactory;

    protected GeneralCrawler(final String url, java.sql.Connection connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.url = url;
    }

    // fetches and parses a URL string
    @Override
    public void run() {
        // download and parse the document
        Connection conn = Jsoup.connect(url); // get this connection
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // select the content text and pull out the elements
        Elements elem = doc.getElementsByClass("post-link with-labels ");

        List<Element> elements = elem.stream().limit(1).collect(Collectors.toList()); // get one raw string
        // extract the urL from the raw string
        List<String> links = elements.stream().map(e -> e.attr("href")).collect(Collectors.toList());

        try {
            // download and parse the document
            Connection conn2 = Jsoup.connect(links.get(0));
            Document doc2 = conn2.get();

            // select the content text and pull out the elements
            Elements elements2 = doc2.getElementsByAttributeValue("class", "post-content");
            List<String> textParagraphs = elements2.stream() // extract some text from this topic
                    .flatMap(d -> d.children().stream())
                    .map(Element::text)
                    .collect(Collectors.toList());

            textParagraphs.forEach(System.out::println); // print data

            ArticleDao artDao = new ArticleDaoImpl(textParagraphs, connectionFactory, url, elements2);
            artDao.insertArticles(); // insert article text into a database

            TermCounter tc = new TermCounter(url);
            tc.processElements(elements2); // word count
            tc.printCounts(); // print the number of words

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}