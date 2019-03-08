package com.riskval.webcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.riskval.app.ApplicationLog;
import com.riskval.app.LocalSettings;

public class WebCrawler {

	public static void main(String[] args) {
		WebCrawler wc = new WebCrawler();
		List<List<HashMap<String, String>>> mapResult = wc.processWebcrawler();
		wc.printHashMapData(mapResult);
		
//		test();
	}
	
	private static void test() {
//		String test = wc.readSymbol2("https://www.google.com/search?num=10&site=finance&tbm=fin&q=NASDAQ:+AAPL#scso=_UJaCXJKhA8Hj_Aaxt5ngDQ2:0");
//		System.out.println(test);
	}

	/**
	 * This function should read from properties.company Note that new JCE is
	 * installed at C:\Program Files (x86)\Java\jre6\lib\security
	 * @return data read from WebSite
	 */
	public List<List<HashMap<String, String>>> processWebcrawler() {
		List<List<HashMap<String, String>>> listOfEachWebData = new ArrayList<List<HashMap<String, String>>>();
		List<String[]> validWebs = getProperties();

		if (validWebs == null || validWebs.size() <= 0) {
			System.err.println("No valid crawler to start");
			return listOfEachWebData;
		}

		for (int i = 0; i < validWebs.size(); i++) {
			String[] oneWeb = validWebs.get(i);
			List<HashMap<String, String>> oneList = new ArrayList<HashMap<String, String>>();

			if (oneWeb[0].equals("webcrawler.properties.yahoo")) {
				// This could use JCE 1.6
				String[] symbols = oneWeb[1].split(",");
				String queryURL = oneWeb[2];
				String[] fields = oneWeb[3].split(",");
				String anchor = oneWeb[4];

				for (int j = 0; j < symbols.length; j++) {
					oneList.add(getData(readSymbol(queryURL + symbols[j]), anchor, fields));
				}
			} else if (oneWeb[0].equals("webcrawler.properties.morningstar")) {
				// This has to use JCE 1.8
				String[] symbols = oneWeb[1].split(",");
				String queryURL = oneWeb[2];
				String[] fields = oneWeb[3].split(",");
				String anchor = oneWeb[4];

				for (int j = 0; j < symbols.length; j++) {
					oneList.add(getData(readSymbol(queryURL + symbols[j].toLowerCase() + "/quote.html"), anchor, fields));
				}
			} // end of if-else if
			listOfEachWebData.add(oneList);
		} // end of for loop
		return listOfEachWebData;
	}

	/**
	 * Search all supported properties files
	 * @return List of values for each WebSite
	 */
	private List<String[]> getProperties() {
		List<String[]> validQueries = new ArrayList<String[]>();
		Properties properties = new Properties();
//		String[] supportedWebsites = { "webcrawler.properties.yahoo", "webcrawler.properties.morningstar" };
		String[] supportedWebsites = { "webcrawler.properties.yahoo" };
		File file = null;

		find: for (int i = 0; i < supportedWebsites.length; i++) {
			file = new File(LocalSettings.getInstance().getConfigurationFolder(), supportedWebsites[i]);
			if (file.exists() && file.canRead()) {
				try {
					properties.load(new FileInputStream(file));
					validQueries.add(new String[] { supportedWebsites[i], properties.getProperty("symbolList"),
							properties.getProperty("baseurl"), properties.getProperty("fieldsList"),
							properties.getProperty("anchor") });
				} catch (Exception ex) {
					ex.printStackTrace();
					ApplicationLog.getInstance().logError(ex.getMessage());
					continue find;
				}
			}
		} // End of for
		return validQueries;
	}

	/**
	 * Print all data for WebCrawler
	 * @param listoflistofmap
	 */
	public void printHashMapData(List<List<HashMap<String, String>>> listoflistofmap) {
		if (listoflistofmap == null || listoflistofmap.size() <= 0) {
			System.err.println("Map is null or empty");
			return;
		}

		for (int i = 0; i < listoflistofmap.size(); i++) {
			System.out.println("++++++++++++++++++++ Webs ++++++++++++++++++++");
			for (int j = 0; j < listoflistofmap.get(i).size(); j++) {
				System.out.println("==================== Symbols =================");
				HashMap<String, String> map = listoflistofmap.get(i).get(j);
				int count = 0;
				System.out.println("-------------------- Maps --------------------");
				for (Entry<String, String> entry : map.entrySet()) {
					System.out.print(++count + ". ");
					System.out.println(entry.getKey()/* .replaceAll(" ", "O") */ + " = "
							+ entry.getValue()/* .replaceAll(" ", "O") */);
					System.out.println("--------------------------------------------");
				}
			}
		} // End of outer for loop
	}

	private String readSymbol(String fullURL) {
		String page = "";
		try {
			Document doc = Jsoup.connect(fullURL).get();
			page = doc.body().text();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return page;
	}

	/**
	 * @param input  Data separated with one space between key and value. Key and
	 *               value may also contain spaces
	 * @param anchor Whatever string after the end of last value
	 * @return HashMap in String, String. Should have no trailing spaces. If so, add
	 *         .trim() inside map.put
	 */
	private HashMap<String, String> getData(String input, String anchor, String[] keys) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (anchor == null || anchor.length() <= 0) {
				anchor = Character.toString(input.charAt(input.length() - 1));
			}
			if (input == null || input.length() <= 0 || anchor == null || anchor.length() <= 0 || keys == null
					|| keys.length < 1) {
				throw new Exception("some params are wrong");
			} // at least one key

			int start = 0, end = 0;
			String key = "", value = "";
			// substring may be O(1) before java 7
			start = input.indexOf(keys[0], start); // first start
			if (start == -1) {
				throw new Exception("first field not found");
			}

			for (int i = 0; i < keys.length; i++) {
				end = start + keys[i].length(); // new end
				key = input.substring(start, end);
//                start = i + 1 >= keys.length ? input.indexOf(anchor, start) : input.indexOf(keys[i + 1], start);
				if (i + 1 >= keys.length) {
					start = input.indexOf(anchor, start); // for special case, might + 1 here
				} else {
					start = input.indexOf(keys[i + 1], start);
				}
				// By default next line have two extra space
				value = input.substring(end, start).trim(); // inclusive, exclusive
				map.put(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return map;
	}

}