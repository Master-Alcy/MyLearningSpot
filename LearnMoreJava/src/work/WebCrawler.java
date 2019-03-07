package work;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler wc = new WebCrawler();
		// TODO symbol from properties
		String result = wc.readSymbol("GOOG");
		System.out.println(result);

		HashMap<String, String> mapResult = wc.washData(result);
		int count = 0;
		System.out.println("------------------------------------------");
		for (Entry<String, String> entry : mapResult.entrySet()) {
			System.out.print(++count + ". ");
			System.out.println("Key: " + entry.getKey()/* .replaceAll(" ", "O") */ + " Value: "
					+ entry.getValue()/* .replaceAll(" ", "O") */);
			System.out.println("------------------------------------------");
		}
	}

	private String readSymbol(String symbol) {
		String page = "";
		try {
			// TODO url from properties
			Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + symbol).get();
//			page = doc.getElementById("quote-summary").text();
			page = doc.body().text();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return page;
	}

	/**
	 * @param input Data separated with one space between key and value. Key and
	 *              value may also contain spaces
	 * @return HashMap in String, String. Should have no trailing spaces. If so, add
	 *         .trim() inside map.put
	 */
	private HashMap<String, String> washData(String input) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			// TODO move the fields to dynamic read from properties
			// If any field changed, we can edit here
			String[] keys = { "Previous Close", "Open", "Bid", "Ask", "Day's Range", "52 Week Range", "Volume",
					"Avg. Volume", "Market Cap", "Beta (3Y Monthly)", "PE Ratio (TTM)", "EPS (TTM)", "Earnings Date",
					"Forward Dividend & Yield", "Ex-Dividend Date", "1y Target Est" };
			if (keys.length <= 1) {
				// throw
				System.out.println("Wrong");
			}
			int start = 0, end = 0;
			String key = "", value = "";
			// substring may be O(1) before java 7
//			start = input.indexOf(keys[0], start); // new start
//			end = start + keys[0].length(); // new end

			for (int i = 0; i < keys.length; i++) {
				start = input.indexOf(keys[i], start); // new start
				if (start - 1 >= 0) {
					value = input.substring(end + 1, start - 1); // old end new start, old value
					map.put(key, value); // old key and old value
				}
				end = start + keys[i].length(); // new end
				key = input.substring(start, end); // new start new end, new key
			} // End of for loop
			start = input.indexOf("Trade prices", start); // new start, read from words after Table
			value = input.substring(end + 1, start - 1); // old end new start, last value
			map.put(key, value); // add the last one
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

}
