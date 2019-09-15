package org.com.mytest.ain;

import org.jsoup.select.Elements;

import java.sql.*;
import java.util.List;

// an ArticleDao implementation
public class ArticleDaoImpl implements ArticleDao {
    private List<String> list; // a list with some text from the topic
    private Connection connection;
    private String url;
    private Elements elements; // to count words

    public ArticleDaoImpl(List<String> list, Connection connection, String url, Elements elements) {
        this.list = list;
        this.connection = connection;
        this.url = url;
        this.elements = elements;
    }

    @Override
    public void insertArticles() {
        try {
            Statement s;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i) + " "); // append paragraphs
            }

            s = connection.createStatement();
            s.executeUpdate("USE somearticles;"); // use this database

            // insert some text
            PreparedStatement statement = connection.prepareStatement("INSERT INTO articles(top_id," +
                    "some_text, fetched_date_time)" +
                    "VALUES (NULL,?, CURRENT_TIMESTAMP );");
            statement.setString(1, sb.toString());
            statement.addBatch();

            statement.executeBatch(); // execute

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}