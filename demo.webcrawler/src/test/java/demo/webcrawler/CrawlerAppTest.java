package demo.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerAppTest {

	@Mock
	Helper helper;

	private static final String baseUrl = "www.example.com";
	private static final String link_1 = "www.example.com/link_1/";
	private static final String link_2 = "www.example.com/link_2";

	private static final String image_1 = "www.example.com//images/img1.png";

	@Test
	public void testGetHostName() {

		String url = "http://www.example.com/link_1/";
		String hostName = "www.example.com";
		when(helper.getHostName(url)).thenReturn(hostName);

		String name = helper.getHostName(url);

		assertEquals(name, hostName);

	}

	private Node createNode(String url, Optional<Node> parent) {
		return new Node(link_2, parent);
	}

	@Test
	public void testCreateNodeAsExpedted() {
		Node node = new Node("www.example.com", Optional.empty());
		assertTrue(node.getParent().isEmpty());
		assertEquals(node.getUrl(), "www.example.com");
	}

	@Test
	public void testCreateRootNode() {

		Node root = createNode(baseUrl, Optional.empty());

		root.addNodes(Arrays.asList(link_1, link_2));

		root.addImages(Arrays.asList(image_1));

		Collection<Node> children = root.getNodes();
		assertTrue(children.size() == 2);

		assertTrue(root.getNode(link_1).isPresent());
		assertTrue(root.getNode(link_1).get().getUrl().equals(link_1));
		assertTrue(root.getNode(link_1).get().getParent().get() == root);

		assertTrue(root.getNode(link_2).isPresent());
		assertTrue(root.getNode(link_2).get().getUrl().equals(link_2));
		assertTrue(root.getNode(link_2).get().getParent().get() == root);

		assertTrue(root.getImages().size() == 1);
		assertTrue(root.getImages().contains(image_1));

	}

	@Test
	public void whenCrawlWitEmptyNodeHelperNotCalled() {

		CrawlerApp crawlerApp = new CrawlerApp("www.example.com", "www.example.com", helper);

		crawlerApp.pageCrawl("www.example.com", Optional.empty());

		verify(helper, times(0)).fetchLink(Mockito.any());
		verify(helper, times(0)).handleLink(Mockito.any());
		verify(helper, times(0)).handleMedia(Mockito.any());

	}

	@Test
	public void whenCrawlWitNodeHelperMethodsCalled() {

		CrawlerApp crawlerApp = new CrawlerApp(baseUrl, baseUrl, helper);

		Node node = createNode(baseUrl, Optional.empty());
		crawlerApp.pageCrawl("www.example.com", Optional.of(node));

		verify(helper, atLeastOnce()).fetchLink(Mockito.any());
		verify(helper, atLeastOnce()).handleLink(Mockito.any());
		verify(helper, atLeastOnce()).handleMedia(Mockito.any());

	}

	@Test
	public void whenCrawlWitWrontHostNameHelperMethodsNotCalled() {

		CrawlerApp crawlerApp = new CrawlerApp(baseUrl, "www.wrong.com", helper);

		Node node = createNode(baseUrl, Optional.empty());
		crawlerApp.pageCrawl("www.example.com", Optional.of(node));

		verify(helper, times(0)).fetchLink(Mockito.any());
		verify(helper, times(0)).handleLink(Mockito.any());
		verify(helper, times(0)).handleMedia(Mockito.any());

	}

	@Test
	public void testStripURLValid() {

		Helper hepler_ = new Helper();
		String expected = "https://wiprodigital.com/what-we-do";
		String url = "https://wiprodigital.com/what-we-do/";
		assertEquals(hepler_.stripUrl(url), expected);

		expected = "https://wiprodigital.com/what-we-do";
		url = "https://wiprodigital.com/what-we-do";
		assertEquals(hepler_.stripUrl(url), expected);

		expected = "https://wiprodigital.com/what-we-do";
		url = "https://wiprodigital.com/what-we-do/#work-three-circles-row";
		assertEquals(hepler_.stripUrl(url), expected);

		expected = "https://wiprodigital.com/what-we-do";
		url = "https://wiprodigital.com/what-we-do/?test=work-three-circles-row";
		assertEquals(hepler_.stripUrl(url), expected);

		expected = "wiprodigital.com/get-in-touch";
		url = "wiprodigital.com/get-in-touch#wddi-contact";
		assertEquals(hepler_.stripUrl(url), expected);

	}
}
