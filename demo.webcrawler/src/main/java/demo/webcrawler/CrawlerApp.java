package demo.webcrawler;

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

	public CrawlerApp(String url, String hostname) {
		this.url = url;
		this.hostname = hostname;
	}

	public static void main(String[] args) {

		String url = "http://wiprodigital.com";
		Helper helper = new Helper();
		String hostname = helper.getHostName(url);
		CrawlerApp crawlerApp = new CrawlerApp(url, hostname);
		crawlerApp.crawl();
	}

	public void crawl() {

	}
}
