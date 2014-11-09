package pl.edu.mimuw.wikiontology.kp347208.wikiontology;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.function.Consumer;

/**
 * Class handling processing of SAX events.
 *
 * @author Krzysztof Pszeniczny
 */
public class XMLHandler extends DefaultHandler {
    private Consumer<Article> articleConsumer;
    private StringBuffer buffer;
    private String title;
    private String text;

    /**
     * Default constuctor.
     *
     * @param articleConsumer {@link Consumer} which should be notified about every article
     */
    public XMLHandler(Consumer<Article> articleConsumer) {
        this.articleConsumer = articleConsumer;
    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attributes) {
        switch (qName) {
            case "page":
                title = text = null;
                break;

            case "title":
            case "text":
                buffer = new StringBuffer();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (buffer != null)
            buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {
        switch (qName) {
            case "page":
                articleConsumer.accept(new Article(title, text));
                break;
            case "title":
                title = buffer.toString();
                break;
            case "text":
                text = buffer.toString();
                break;
        }
    }
}
