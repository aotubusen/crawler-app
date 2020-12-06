package demo.webcrawler;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

public class CrawlerApp {

	/**
	 * 
	 * Crawler is limited to current domain, only fetch urls for current domain
	 * 
	 * print a sitemap reprensentation
	 * 
	 */

	private String url;
	private String hostname;
	private Helper helper;
	private Collection<String> fetchedUrls = Collections.synchronizedCollection(new HashSet<String>());

	public CrawlerApp(String url, String hostname, Helper helper) {
		this.url = url;
		this.hostname = hostname;
		this.helper = helper;
	}

	public static void main(String[] args) {

		if (args.length != 1 || !Helper.urlValidator(args[0])) {
			System.out.println(" ******************************************");
			System.out.println("       Please provide a valid URL");
			System.out.println(" ******************************************");
			return;
		}

		Instant start = Instant.now();
//		String url = "http://wiprodigital.com";
		String url = args[0];

		Helper helper = new Helper();
		String hostname = helper.getHostName(url);
		CrawlerApp crawlerApp = new CrawlerApp(url, hostname, helper);

		Node root = new Node(url, Optional.empty());

		Optional<Node> result = crawlerApp.pageCrawl(url, Optional.of(root));
		PrintHelper.print(result);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(String.format("CrawlerApp Time Elapsed : %s (approx. %s secs.) ", timeElapsed,
				TimeUnit.MILLISECONDS.toSeconds(timeElapsed))); // Prints: Time Elapsed: 2501
//		TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
	}

	public Optional<Node> pageCrawl(String url, Optional<Node> parent) {

		if (parent.isEmpty())
			return parent;

		// Only fetch page if url contains hostName,,Needs better verification
		if (url.contains(hostname) && !fetchedUrls.contains(url)) {
			Optional<Document> doc = helper.fetchLink(url);
			fetchedUrls.add(url);

			parent = handleDoc(doc, parent);

			parent.get().getNodes().parallelStream().forEach(n -> pageCrawl(n.getUrl(), Optional.of(n)));
		}
		return parent;
	}

	private Optional<Node> handleDoc(Optional<Document> doc, Optional<Node> parent) {
		Collection<String> urls = helper.handleLink(doc);
		Collection<String> images = helper.handleMedia(doc);
		parent.get().addNodes(urls);
		parent.get().addImages(images);
		return parent;
	}
}
