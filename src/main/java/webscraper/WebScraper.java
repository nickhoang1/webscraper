package webscraper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.Wiki;

public class WebScraper {

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(WebScraper.class);
	// The url of the website. This is just an example
	static final String webSiteURL = "https://en.wikipedia.org/wiki/Albert_Einstein"; //testing

	// The path of the folder that you want to save the images to
	static final String folderPath = "c:\\work\\demo";

	private static Wiki wiki = new Wiki();

	static void test1() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Forrest_Gump";
		// LOGGER.info("Fetching %s..." + url);

		Document doc = Jsoup.connect(url).get();

		wiki.setTitle("Star_Wars");

		// get summary
		Elements els = doc.select(".mw-parser-output > p:not(h2 ~ p)");
		System.out.println("---len---\n" + Jsoup.parse(els.text()).text().length());
		wiki.setSummary(Jsoup.parse(els.text()).text());

		// infobox

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---info box---\n");
		Elements els1 = doc.select("table.infobox >tbody > tr, table.vevent >tbody > tr");

		for (Element el : els1) {
			System.out.println(el.text());
			Elements links = el.getElementsByTag("IMG");

			for (Element e : links) {

				// for each element get the srs url
				String src = e.attr("abs:src");

				System.out.println("Image Found!");
				System.out.println("src attribute is : " + src);
			}
		}

		// headline info
		Elements elements = doc.select("span.mw-headline, h2 ~ div, h2 ~ p");
		// Elements elements = doc.select("span.mw-parser-output, h2 ~ div, h2 ~
		// p");
		String elemId = null;
		for (Element elem : elements) {
			if (elem.hasClass("mw-headline")) {
				System.out.println("***" + elem.id());
				elemId = elem.id().trim();
			} else {
				// System.out.println(elemId + " ----" + elem.text());
				switch (elemId) {
				case "Plot":
					wiki.setPlot(elem.text());
					break;

				// case "Cast":
				// wiki.setCast(elem.text());
				// break;
				//
				// case "Development":
				// wiki.setDevelopment(elem.text());
				// break;
				//
				// case "External_links":
				// wiki.setExternalLinks(elem.text());
				// break;

				}
			}
			if (elem.text().equalsIgnoreCase("Cast"))
				break;
			// if (elem.text().contains("Production"))
			// continue;
		}

		String wikiStr = wiki.toString();
		// System.out.println("before: " + wikiStr);
		// System.out.println( wikiStr.replaceAll("[\\[^0-9\\]]+", ""));
		// LOGGER.info(JsonUtils.objToJsonStr(wiki));

		Response res = Jsoup.connect("http://en.wikipedia.org/wiki/Tom_Cruise").execute();
		String html = res.body();
		Document doc2 = Jsoup.parseBodyFragment(html);
		Element body = doc2.body();
		Elements tables = body.getElementsByTag("table");
		for (Element table : tables) {
			if (table.className().contains("infobox") == true) {
				System.out.println(table.outerHtml());
				break;
			}
		}

		// ----
		// Element content = doc.getElementById("content");
		// Elements links = content.getElementsByTag("a");
		// for (Element link : links) {
		// System.out.println("===== linkHref ="+ link.attr("href"));
		// System.out.println("------ linkText ="+ link.text());
		// }
	}

	//// ===================

	private static void downloadImages() {

		try {

			// Connect to the website and get the html
			Document doc = Jsoup.connect(webSiteURL).get();

			// Get all elements with img tag ,
			Elements img = doc.getElementsByTag("img");

			for (Element el : img) {

				// for each element get the srs url
				String src = el.absUrl("src");

				System.out.println("Image Found!");
				System.out.println("src attribute is : " + src);

				if (src.contains("Toy_Story"))

					getImages(src);
				/// return

			}

		} catch (IOException ex) {
			System.err.println("There was an error");
			// Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE,
			// null, ex);
		}
	}

	private static void getImages(String src) throws IOException {

		String folder = null;

		// Exctract the name of the image from the src attribute
		int indexname = src.lastIndexOf("/");

		if (indexname == src.length()) {
			src = src.substring(1, indexname);
		}

		indexname = src.lastIndexOf("/");
		String name = src.substring(indexname, src.length());

		System.out.println(name);

		// Open a URL Stream
		URL url = new URL(src);
		InputStream in = url.openStream();

		OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));

		for (int b; (b = in.read()) != -1;) {
			out.write(b);
		}
		out.close();
		in.close();

	}

	public static void scrapWikipediaMoviesImages() {
		try {
			// Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/His
			// Musical Career").get();
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Laburnum_Grove").get();
			Elements elements = doc
					.select("table.infobox > tbody > tr, table.vevent > tbody > tr > td > a[href].image");
			String imgUrl = null;
			for (Element e : elements) {
				Elements links = e.getElementsByTag("IMG");
				for (Element src : links) {
					String imageSrc = src.attr("abs:src");
					if (imageSrc.contains("%28") || imageSrc.contains("%29")) {
						String img = imageSrc.replaceAll("%28", "(");
						imgUrl = img.replaceAll("%29", ")");

						System.out.println(imgUrl);
					} else {
						imgUrl = src.attr("abs:src");
						System.out.println(src.attr("imgUrl"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ----------------------------

	public static void main(String[] args) throws Exception {

		// String url = "http://en.wikipedia.org/wiki/Albert_Einstein";
		// Document doc;
		//
		// try {
		// doc = Jsoup.connect(url).get();
		// doc = Jsoup.parse(doc.toString());
		//
		// Elements titles = doc.select(".mw-headline");
		//// PrintStream out = new PrintStream(new
		// FileOutputStream("output.txt"));
		//// System.setOut(out);
		//
		// for(Element h3 : doc.select(".mw-headline"))
		// {
		// String title = h3.text();
		//
		// String titleID = h3.id();
		//
		// if (titleID.contains("1921")) {
		// break;
		// }
		// Elements paragraphs = doc.select("p#"+titleID);
		//
		// //Element nextEle=h3.nextElementSibling();
		//
		// System.out.println(title);
		// System.out.println("----------------------------------------");
		// System.out.println(titleID);
		// System.out.print("\n");
		// System.out.println(paragraphs.text());
		// System.out.print("\n");
		// }
		//
		// } catch (IOException e) {
		// System.out.println("deu merda");
		// e.printStackTrace();
		// }
		//
		 test1();
		//scrapWikipediaMoviesImages();

		//downloadImages();
		//
		String url = "https://en.wikipedia.org/wiki/Star_Wars_(film)";
		Document document = Jsoup.connect(url).timeout(100000).get();
		Elements paragraphs = document.select(".mw-headline p");
		Elements titles = document.select(".mw-headline");
		// Elements paragraphs = document.select(".mw-content-ltr p");

		// Element firstParagraph = paragraphs.first();
		// Element lastParagraph = paragraphs.last();
		// Element p;
		// int i = 1;
		// p = firstParagraph;
		//
		// StringBuilder sb = new StringBuilder();
		// sb.append(p.text());
		//
		// while (p != lastParagraph) {
		// p = paragraphs.get(i);
		//
		// //System.out.println("*** " + p.text());
		// if (!p.text().equalsIgnoreCase("Footnotes")
		// && !p.text().contains("externalLinks")
		// && !p.text().equalsIgnoreCase("Citations")) {
		// sb.append(p.text());
		// }
		// i++;
		// }
		//
		// Wiki wiki = new Wiki();
		// wiki.setTitle("Star War");
		// wiki.setSummary(sb.toString());

		// System.out.println(wiki.toString());
		//
		// // ------------------ Actor/Actress
		//// Response res =
		// Jsoup.connect("http://en.wikipedia.org/wiki/Tom_Cruise").execute();
		//// String html = res.body();
		//// String occupation;
		//// Document doc2 = Jsoup.parseBodyFragment(html);
		//// Element body = doc2.body();
		//// Elements tables = body.getElementsByTag("table");
		//// occupation = doc2.select("td[class=role]").first().text();
		//// System.out.println("----Occupation:" + occupation);
		//// for (Element table : tables) {
		//// if (table.className().contains("infobox") == true) {
		//// System.out.println(table.outerHtml());
		//// break;
		//// }
		//// }
		////
		//// // film ratings
		////
		//// for (Element row : document.select("table.chart.full-width tr")) {
		////
		//// final String title = row.select(".titleColumn a").text();
		//// final String rating = row.select(".imdbRating").text();
		////
		//// System.out.println(title + " -> Rating: " + rating);
		//// }
		////
		//// Document doc =
		// Jsoup.connect("https://en.wikipedia.org/wiki/Star_Wars_(film)#Cast").timeout(10000).get();
		//// for (Element row : doc.select("list.mw-parser-output li")) {
		////
		//// final String title = row.select(".title").text();
		////
		//// System.out.println(title);
		//// }
		//
		// // Element sampleDiv = document.getElementById("mw-content-text");
		// // System.out.println("Data: " + sampleDiv.toString());
		// // Elements links = sampleDiv.getElementsByTag("a");
		// //
		// // for (Element link : links) {
		// // System.out.println("Href: " + link.attr("href"));
		// // System.out.println("Text: " + link.text());
		// // }
		//
	}

}
