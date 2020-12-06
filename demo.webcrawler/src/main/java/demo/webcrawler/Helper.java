package demo.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Helper {

	private Connection getConnection(String url) {
		return Jsoup.connect(url);
	}

	public String getHostName(String url) {
		return getConnection(url).request().url().getHost();
	}
}
