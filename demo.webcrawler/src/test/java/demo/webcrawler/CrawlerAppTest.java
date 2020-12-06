package demo.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerAppTest {

	@Mock
	Helper helper;
	@Mock
	Jsoup jsoup;

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHostName() {

		String url = "http://www.example.com/link_1/";
		String hostName = "www.example.com";
		when(helper.getHostName(url)).thenReturn(hostName);

		String name = helper.getHostName(url);

		assertEquals(name, hostName);

	}

}
