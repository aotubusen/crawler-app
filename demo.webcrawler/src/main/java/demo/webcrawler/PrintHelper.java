package demo.webcrawler;

import java.util.Optional;

public class PrintHelper {
	public static final String Link = " **";
	public static final String image = " oo";
	public static final String subDir = "__";
	public static final String indent = "  |";
	public static final String printFormat = "%s%s%s%s %s";

	public static String print(Optional<Node> nodeOptional) {
		return print(nodeOptional, 0, null);
	}

	private static String print(Optional<Node> nodeOptional, int indentCount, StringBuilder sb) {

		if (sb == null)
			sb = new StringBuilder();

		if (nodeOptional.isEmpty()) {
			sb.append("Nothing to print.........");
			return sb.toString();
		}
		Node node = nodeOptional.get();

		String newLine = node.isRoot() ? "" : "\n";
		String subDir_ = node.isRoot() ? "" : subDir;
		String indent_ = getindent(indentCount++);
		sb.append(String.format(printFormat, newLine, indent_, subDir_, Link, node.getUrl()));

		for (Node node_ : node.getNodes()) {
			indent_ = getindent(indentCount);
//			if (nOption.isPresent()) {
			sb.append(String.format(printFormat, "\n", indent_, subDir, Link, node_.getUrl()));

			int childIndent = indentCount;
			if (node_.getNodes().size() > 0 || node_.getImages().size() > 0)
				childIndent += 1;

			for (Node nOptionsub : node_.getNodes()) {
				print(Optional.of(nOptionsub), childIndent, sb);
			}

			for (String img : node_.getImages()) {
				indent_ = getindent(childIndent);
				sb.append(String.format(printFormat, "\n", indent_, subDir, image, img));
			}
		}
//		}

		for (String img : node.getImages()) {
			indent_ = getindent(indentCount);
			sb.append(String.format(printFormat, "\n", indent_, subDir, image, img));
		}

		return sb.toString();
	}

	public static String getindent(int count) {
		return indent.repeat(count);
	}

}
