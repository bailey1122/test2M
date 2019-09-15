package org.com.mytest.ain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// an ExecutorService
public class ExecutorServiceAin {
    public static void main(String[] args) {

        Db db = new Db();
        db.initialize(); // initialize a database

        ExecutorService executorService = Executors.newFixedThreadPool(5); // will create a thread-pool with 5 threads
        executorService.submit(new GeneralCrawler("https://ain.ua/tag/biznes", ConnectionFactory.getConnection()));
        executorService.submit(new GeneralCrawler("https://ain.ua/tag/gosudarstvo",  ConnectionFactory.getConnection()));
        executorService.submit(new GeneralCrawler("https://ain.ua/tag/investicii",  ConnectionFactory.getConnection()));
        executorService.submit(new GeneralCrawler("https://ain.ua/tag/kiev/",  ConnectionFactory.getConnection()));
        executorService.submit(new GeneralCrawler("https://ain.ua/tag/relokejt",  ConnectionFactory.getConnection()));

        executorService.shutdown(); // shut down the ExecutorService
    }
}