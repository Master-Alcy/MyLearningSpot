package com.riskval.webcrawler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.riskval.app.ApplicationLog;
import com.riskval.app.LocalSettings;

/**
 * @author Jingxuan Ai
 * @director Jingfeng
 * Mar/10/2019
 */
public class WebCrawler {

    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler();
        wc.printAllData(wc.processWebcrawler());

//		wc.getCompanyLogo("Apple_Inc.");
//		wc.getCompanyLogo("Google");

//		String test = wc.readSymbol2("https://www.google.com/search?num=10&site=finance&tbm=fin&q=NASDAQ:+AAPL#scso=_UJaCXJKhA8Hj_Aaxt5ngDQ2:0");
//		System.out.println(test);
    }

    private void getCompanyLogo(String company) {
        try {
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + company).get();
            Element img = doc.select("td.logo").get(0).getElementsByTag("img").get(0);
            String src = img.absUrl("src");
            System.out.println("Image Found! Its src is: " + src);
            getLogo(src, company);
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Image not found...");
    }

    private void getLogo(String src, String name) {
        try {
            URL url = new URL(src);
            InputStream in = url.openStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\riskval\\Client\\config\\" + name + ".png"));
            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function should read from properties.company Note that new JCE is
     * installed at C:\Program Files (x86)\Java\jre6\lib\security
     *
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
                String[] companies = oneWeb[1].split(";;");
                String queryURL = oneWeb[2];
                String[] fields = oneWeb[3].split(",");
                String anchor = oneWeb[4];

                for (int j = 0; j < companies.length; j++) {
                    String[] company = companies[j].split(",");
                    System.out.println(Arrays.toString(company));
                    getCompanyLogo(company[1]);
                    oneList.add(getData(readSymbol(queryURL + company[0]), anchor, fields));
                }
            } else if (oneWeb[0].equals("webcrawler.properties.morningstar")) {
                // This has to use JCE 1.8
                String[] companies = oneWeb[1].split(";;");
                String queryURL = oneWeb[2];
                String[] fields = oneWeb[3].split(",");
                String anchor = oneWeb[4];

                for (int j = 0; j < companies.length; j++) {
                    String[] company = companies[j].split(",");
                    getCompanyLogo(company[1]);
                    oneList.add(getData(readSymbol(queryURL + company[0]), anchor, fields));
                }
            } // end of if-else if
            listOfEachWebData.add(oneList);
        } // end of for loop
        return listOfEachWebData;
    }

    /**
     * Search all supported properties files
     *
     * @return List of values for each WebSite
     */
    private List<String[]> getProperties() {
        List<String[]> validQueries = new ArrayList<String[]>();
        Properties properties = new Properties();
//		String[] supportedWebsites = { "webcrawler.properties.yahoo", "webcrawler.properties.morningstar" };
        String[] supportedWebsites = {"webcrawler.properties.yahoo"};
        File file = null;

        find:
        for (int i = 0; i < supportedWebsites.length; i++) {
            file = new File(LocalSettings.getInstance().getConfigurationFolder(), supportedWebsites[i]);
            if (file.exists() && file.canRead()) {
                try {
                    properties.load(new FileInputStream(file));
                    validQueries.add(new String[]{supportedWebsites[i], properties.getProperty("symbolList"),
                            properties.getProperty("baseurl"), properties.getProperty("fieldsList"),
                            properties.getProperty("anchor")});
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
     *
     * @param listoflistofmap
     */
    public void printAllData(List<List<HashMap<String, String>>> listoflistofmap) {
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
     * .trim() inside map.put
     */
    private HashMap<String, String> getData(String input, String anchor, String[] keys) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            Boolean noAnchor = false;
            if (anchor == null || anchor.length() <= 0) {
                noAnchor = true;
            }
            if (input == null || input.length() <= 0 || keys == null || keys.length < 1) {
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

                if (i + 1 >= keys.length) { // last key recorded
                    if (noAnchor) {
                        start = input.length(); // no anchor and read the rest of the string
                    } else {
                        start = input.indexOf(anchor, start);
                    }
                } else {
                    start = input.indexOf(keys[i + 1], start);
                }
                // By default next line have two extra space
                value = input.substring(end, start).trim(); // (inclusive, exclusive)
                map.put(key, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return map;
    }

}