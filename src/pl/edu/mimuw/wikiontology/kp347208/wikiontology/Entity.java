package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class representing an entity.
 * <p>
 * <p>
 * This class represents an entity with:
 * <ul>
 * <li>Article title</li>
 * <li>List of links contained in the article</li>
 * <li>List of categories which the article belongs to</li>
 * <li>List of templates found in the article</li>
 * </ul>
 * <p>
 * <p>
 * Only reading the data is publicly available, creating entities and
 * modyfying them is restricted to the package.
 * </p>
 *
 * @author Krzysztof Pszeniczny
 */
public class Entity {
    private final List<String> links;
    private final List<String> categories;
    private final List<String> templates;
    private String title;

    /**
     * Default constructor
     *
     * @param title         Title of the article (must not be null)
     */
    Entity(String title) {
        Objects.requireNonNull(title, "Title must not be null");

        this.links = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.templates = new ArrayList<>();

        this.title = title;
    }

    /**
     * Returns a stream of links contained in the corresponding article.
     *
     * @return Stream of titles of pages the corresponding article links to
     */
    public Stream<String> linksStream() {
        return links.stream();
    }

    /**
     * Returns a stream of categories the corresponding article belongs to
     *
     * @return Stream of names of categories the corresponding article belongs to
     */
    public Stream<String> categoriesStream() {
        return categories.stream();
    }
    
    /**
     * Returns a stream of templates found in the article
     *
     * @return Stream of names of templates found in the article
     */
    public Stream<String> templatesStream() {
        return templates.stream();
    }

    /**
     * Returns the title of corresponding article.
     *
     * @return Title of the corresponding article.
     */
    public String getTitle() {
        return title;
    }


    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return title.equals(entity.title);
    }

    List<String> getLinks() {
        return links;
    }

    List<String> getCategories() {
        return categories;
    }
    
    List<String> getTemplates() {
        return templates;
    }
}
