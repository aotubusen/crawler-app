package demo.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;

public class PrintHelperTest {

	String baseUrl = "www.example.com";
	String link_1 = "www.example.com/link_1/";
	String link_2 = "www.example.com/link_2";
	String link_3 = "www.example.com/link_3";

	String link_1_1 = "www.example.com/link_1/link_1_1";
	String link_1_1_1 = "www.example.com/link_1/link_1_1_1";
	String link_2_1 = "www.example.com/link_2/link_2_1";
	String link_2_2 = "www.example.com/link_2/link_2_2";
	String link_3_1 = "www.example.com/link_3/link_3_1";

	String image_1 = "www.example.com//images/img1.png";
	String image_2 = "www.example.com/images/img2.png";
	String image_3 = "www.example.com/images/img3.png";

	private Node buildRoot() {
		Node root = new Node(baseUrl, Optional.empty());

		root.addNodes(Arrays.asList(link_1, link_2, link_3));

		root.addImages(Arrays.asList(image_1, image_2));

		Optional<Node> link_1Node = root.getNode(link_1);

		link_1Node.get().addNodes(Arrays.asList(link_1_1));
		link_1Node.get().addImages(Arrays.asList(image_2));

		Optional<Node> link_1_1Node = link_1Node.get().getNode(link_1_1);
		link_1_1Node.get().addNodes(Arrays.asList(link_1_1_1));

		return root;
	}

	@Test
	public void testGetindent() {

		int times = 2;
		String expected = PrintHelper.indent;

		String actual = PrintHelper.getindent(times);
		assertEquals(expected.repeat(times), actual);

	}

	@Test
	public void testCreateRootNodeWithDoubleNestedChildren() {

		Node root = buildRoot();

//		  **  www.example.com
//		    |__  **  www.example.com/link_3
//		    |__  **  www.example.com/link_2
//		    |__  **  www.example.com/link_1
//		      |__  **  www.example.com/link_1/link_1_1
//		        |__  **  www.example.com/link_1/link_1_1_1
//		      |__  --  www.example.com/images/img2.png
//		    |__  --  www.example.com//images/img1.png
//		    |__  --  www.example.com/images/img2.png

		Collection<Node> rootLinks = root.getNodes();
		assertEquals(3, rootLinks.size());
		assertEquals(2, root.getImages().size());
		assertTrue(root.getImages().contains(image_1));
		assertTrue(root.getImages().contains(image_2));
		assertTrue(root.getUrl().equals(baseUrl));
		assertTrue(root.getNode(link_1).get().getUrl().equals(link_1));
		assertTrue(root.getNode(link_2).get().getUrl().equals(link_2));
		assertTrue(root.getNode(link_3).get().getUrl().equals(link_3));

		Optional<Node> link_1Node = root.getNode(link_1);

		assertEquals(1, link_1Node.get().getNodes().size());

		Collection<String> link_1_Images = link_1Node.get().getImages();
		assertEquals(1, link_1_Images.size());
		assertTrue(link_1_Images.contains(image_2));

		Optional<Node> link_1_1_Node = link_1Node.get().getNode(link_1_1);

		assertEquals(1, link_1_1_Node.get().getNodes().size());
		assertEquals(0, link_1_1_Node.get().getImages().size());
	}

	@Test
	public void testWhenPrintEmptyNode() {
		assertEquals("Nothing to print.........", PrintHelper.print(Optional.empty()));
	}

	@Test
	public void testWhenPrintEmptyRootNode() {
		Node root = new Node(baseUrl, Optional.empty());
		assertEquals(String.format(PrintHelper.printFormat, "", "", "", PrintHelper.Link, baseUrl),
				PrintHelper.print(Optional.of(root)));
	}

	@Test
	public void testWhenPrintRootNodeWithChildren() {

		Node root = buildRoot();

		StringBuilder expect = new StringBuilder(
				String.format(PrintHelper.printFormat, "", "", "", PrintHelper.Link, baseUrl));

		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(1), PrintHelper.subDir,
				PrintHelper.Link, link_3));
		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(1), PrintHelper.subDir,
				PrintHelper.Link, link_2));
		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(1), PrintHelper.subDir,
				PrintHelper.Link, link_1));

		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(2), PrintHelper.subDir,
				PrintHelper.Link, link_1_1));
		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(3), PrintHelper.subDir,
				PrintHelper.Link, link_1_1_1));
		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(2), PrintHelper.subDir,
				PrintHelper.image, image_2));

		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(1), PrintHelper.subDir,
				PrintHelper.image, image_1));
		expect.append(String.format(PrintHelper.printFormat, "\n", PrintHelper.getindent(1), PrintHelper.subDir,
				PrintHelper.image, image_2));

		System.out.println(expect.toString());
		System.out.println("++++++++++++++++++++++++++++++++++++++++");

		String result = PrintHelper.print(Optional.of(root));
		System.out.println(result);

		assertEquals(expect.toString(), result);
	}

}
