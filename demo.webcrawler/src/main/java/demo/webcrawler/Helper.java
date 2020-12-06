package demo.webcrawler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Helper {

	private Connection getConnection(String url) {
		return Jsoup.connect(url);
	}

	public String getHostName(String url) {
		return getConnection(url).request().url().getHost();
	}

	public Optional<Document> fetchLink(String url) {

		try {
			Connection conn = getConnection(url);
			System.out.println(String.format("\n\n___Fetching %s   ", url));
			Document doc = conn.get();
//				count++;
			return Optional.of(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Collection<String> handleLink(Optional<Document> doc) {

		if (doc.isEmpty())
			return Collections.emptySet();

		Collection<String> list = new HashSet<>();

		Elements links = doc.get().select("a[href]");
		for (Element link : links) {
			String url = link.attr("abs:href");
			list.add(url);
		}
		return list;
	}

	public Collection<String> handleMedia(Optional<Document> doc) {

		if (doc.isEmpty())
			return Collections.emptySet();

		Collection<String> list = new HashSet<>();

		Elements media = doc.get().select("[src]");
		for (Element src : media) {
			String url = "";
			if (src.normalName().equals("img")) {
				url = src.attr("abs:src");
			} else {
				url = src.attr("abs:src");
			}
		}
		return list;
	}
}
