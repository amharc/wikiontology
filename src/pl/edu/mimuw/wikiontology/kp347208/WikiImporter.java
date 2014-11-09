package pl.edu.mimuw.wikiontology.kp347208;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pl.edu.mimuw.wikiontology.kp347208.wikiontology.Entity;
import pl.edu.mimuw.wikiontology.kp347208.wikiontology.FilteredReader;
import pl.edu.mimuw.wikiontology.kp347208.wikiontology.GraphSearch;
import pl.edu.mimuw.wikiontology.kp347208.wikiontology.PeopleFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

public class WikiImporter {
	private HashSet<String> peopleNames = new HashSet<>();
	private GraphSearch search;
	
	public WikiImporter(String filename) throws SAXException, IOException {
		String fullName = new File(filename).getAbsolutePath();
		if(File.separatorChar != '/')
            fullName = fullName.replace(File.separatorChar, '/');
        if(!fullName.startsWith("/"))
			fullName = "/" + fullName;
		
		FilteredReader reader = new FilteredReader(PeopleFilter.getInstance(), new InputSource(fullName));
		reader.getEntities().stream()
		                    .map(Entity::getTitle)
		                    .forEach(peopleNames::add);

        search = new GraphSearch(reader.getEntities());
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: WikiImporter filename");
            System.exit(1);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            WikiImporter importer = new WikiImporter(args[0]);
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("***");
                importer.processRequest(line);
                System.out.println("***");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getLocalizedMessage());
            System.exit(1);
        } catch (SAXException | IOException e) {
            System.err.println("Error: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

    private Predicate<Entity> makePredicate(String filter) {
        Predicate<Entity> pred = PeopleFilter.getInstance();
        if (filter.equals("physicist"))
            pred = pred.and(x -> x.categoriesStream().anyMatch(cat ->
                    cat.contains("physicist")));
        return pred;
    }

    public void processRequest(String line) throws IOException {
        String tokens[] = line.split(" ");

        if(tokens.length != 3) {
            System.out.println("Invalid query");
            return;
        }

        for (int i = 1; i <= 2; ++i)
            tokens[i] = tokens[i].replace("_", " ");

        if (!peopleNames.contains(tokens[1]))
            System.out.println("No such person: " + tokens[1]);
        else if (!peopleNames.contains(tokens[2]))
            System.out.println("No such person: " + tokens[2]);
        else {
            ArrayList<String> result = search.getPath(tokens[1], tokens[2], makePredicate(tokens[0]));

            if (result == null)
                System.out.println("No path found");
            else {
                System.out.println("Path length: " + (result.size() - 1));
                result.forEach(System.out::println);
            }
		}
	}
}
