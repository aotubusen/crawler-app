package demo.webcrawler;

import java.util.Collection;
import java.util.Optional;

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

	public CrawlerApp(String url, String hostname, Helper helper) {
		this.url = url;
		this.hostname = hostname;
		this.helper = helper;
	}

	public static void main(String[] args) {

		String url = "http://wiprodigital.com";
		Helper helper = new Helper();
		String hostname = helper.getHostName(url);
		CrawlerApp crawlerApp = new CrawlerApp(url, hostname, helper);

		Node root = new Node(url, Optional.empty());

		Optional<Node> result = crawlerApp.pageCrawl(url, Optional.of(root));
	}

	public Optional<Node> pageCrawl(String url, Optional<Node> parent) {

		if (parent.isEmpty())
			return parent;

		// Only fetch page if url contains hostName,,Needs better verification
		if (url.contains(hostname)) {
			Optional<Document> doc = helper.fetchLink(url);
			parent = handleDoc(doc, parent);
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
