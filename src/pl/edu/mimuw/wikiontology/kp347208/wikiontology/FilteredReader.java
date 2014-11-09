package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Class representing a reader which reads a wiki XML from an {@link InputSource}
 * and retains only articles satisfying given {@link Predicate}&lt;{@link Entity}&gt;.
 *
 * @author Krzysztof Pszeniczny
 */
public class FilteredReader {
    private HashMap<String, Entity> entities = new HashMap<>();

    /**
     * Default constuctor
     *
     * @param filter A predicate which should be evaluated on every article
     * @param source A source which should be used to read XML
     * @throws SAXException Should the XML file be malformed
     * @throws IOException  Should some IO error occur
     */
    public FilteredReader(Predicate<Entity> filter, InputSource source) throws SAXException, IOException {
        Consumer<Article> consumer = article -> {
            Entity entity = ArticleTransformer.getInstance().apply(article);
            if (filter.test(entity))
                entities.put(entity.getTitle(), entity);
        };

        XMLHandler handler = new XMLHandler(consumer);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = spf.newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new Error("Should not happen", e);
        }
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(handler);
        reader.parse(source);

        entities.forEach((name, ent) -> ent.getLinks().retainAll(entities.keySet()));
    }

    /**
     * Returns a collection of Entities created during construction.
     *
     * @return A collection of entities
     */
    public Collection<Entity> getEntities() {
        return entities.values();
    }
}
