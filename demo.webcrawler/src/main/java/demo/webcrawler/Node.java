package demo.webcrawler;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class Node {
	/**
	 * A Node represents a page, containing all the links and images on the page
	 * every links that fetches a page is represented as a Node in the collection of
	 * Nodes, with the current Node a parent
	 * 
	 */
	private Map<String, Node> nodes = new HashMap<>();
	private Collection<String> images = new HashSet<String>();

	private Optional<Node> parent;
	private String url;

	public Node(String url, Optional<Node> parent) {
		this.url = url;
		this.parent = parent;
	}

	public void addNodes(Collection<String> urls) {
		urls.forEach(url -> {
			Node node = new Node(url, Optional.of(this));
			this.nodes.put(url, node);
		});
	}

	public Collection<Node> getNodes() {
		return nodes.values();// need to fix sorting to guarantee print order

//		return Set.copyOf(nodes.values());//
	}

	public Optional<Node> getNode(String url) {
		if (!nodes.keySet().contains(url))
			Optional.empty();

		return Optional.of(nodes.get(url));
	}

	public void addImages(Collection<String> images) {
		this.images.addAll(images);
	}

	public Collection<String> getImages() {
		return images;
//		return Set.copyOf(images);// need to fix sorting to guarantee print order
	}

	public Optional<Node> getParent() {
		return parent;
	}

	public void print() {

	}

	public String getUrl() {
		return url;
	}

	public boolean isRoot() {
		return parent.isEmpty();
	}
}
