package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * A {@link Predicate} checking if given {@link Entity} represents a person.
 *
 * @author Krzysztof Pszeniczny
 */
public class PeopleFilter implements Predicate<Entity> {
    private final static PeopleFilter instance = new PeopleFilter();
    private final static String[] categoryEndings = {"births", "deaths"};
    private final static String[] categoryPatterns = {"mathematician", "philosopher",
            "physicist", "writer", "theorist",
            "artist", "programmer", "people"};

    private PeopleFilter() {
    }

    public static PeopleFilter getInstance() {
        return instance;
    }

    @Override
    public boolean test(Entity t) {
        return t.templatesStream().anyMatch("Persondata"::equals)
                || Arrays.stream(categoryEndings).anyMatch(pattern ->
                    t.categoriesStream().anyMatch(category ->
                        category.toLowerCase().endsWith(pattern)))
                || Arrays.stream(categoryPatterns).anyMatch(pattern ->
                    t.categoriesStream().anyMatch(category ->
                        category.toLowerCase().contains(pattern)));
    }
}
