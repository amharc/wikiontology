package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class processing articles and extracting information from them.
 * <p>
 * <p>
 * This class acts transforms instances of {@link Article}
 * into instances of {@link Entity}. Every link in the article is
 * recorded in the created entity. Links starting with "Category:"
 * are treated as category informations and included in respective list
 * in the entity. Templates found in the article are also recorded.
 * <p>
 * This is a singleton class.
 *
 * @author Krzysztof Pszeniczny
 */
public class ArticleTransformer implements Function<Article, Entity> {
    private final static ArticleTransformer instance = new ArticleTransformer();
    private final static String CATEGORY_PREFIX = "Category:";
    private final static Pattern TEMPLATE_PATTERN = Pattern.compile("\\{\\{([^\\} |\\n]+?)(\\}\\}|\\| |\\n)", Pattern.UNICODE_CHARACTER_CLASS);
    private final static Pattern LINK_PATTERN = Pattern.compile("\\[\\[([^\\]|#]+?)((\\]\\])|\\||#)", Pattern.UNICODE_CHARACTER_CLASS);

    private ArticleTransformer() {
    }

    /**
     * Returns instance.
     *
     * @return Instance of ArticleTransformer.
     */
    public static ArticleTransformer getInstance() {
        return instance;
    }

    @Override
    public Entity apply(Article t) {
        Entity entity = new Entity(t.getTitle());
        Matcher matcher = LINK_PATTERN.matcher(t.getText());

        while (matcher.find()) {
            String link = matcher.group(1).replace("_", " ");
            if (link.startsWith(CATEGORY_PREFIX))
                entity.getCategories().add(link.substring(CATEGORY_PREFIX.length()));
            else
                entity.getLinks().add(link);
        }
        
        matcher = TEMPLATE_PATTERN.matcher(t.getText());
        while (matcher.find()) {
        	entity.getTemplates().add(matcher.group(1));
        }

        return entity;
    }
}
