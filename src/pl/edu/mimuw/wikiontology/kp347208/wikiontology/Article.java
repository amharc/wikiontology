package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import java.util.Objects;

/**
 * Class representing an article
 *
 * @author Krzysztof Pszeniczny
 */
public class Article {
    private final String title, text;

    /**
     * Default constructor
     *
     * @param title Title of the article (must not be null)
     * @param text  Article text (must not be null)
     */
    public Article(String title, String text) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(text, "Text must not be null");

        this.title = title;
        this.text = text;
    }

    /**
     * Get title of the article
     *
     * @return Title of the article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get text of the article
     *
     * @return Text of the article
     */
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return title.equals(article.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
