package util;

import com.google.common.net.InternetDomainName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class WebsiteUtil {
    /**
     * Fetches content of URL and extracts all occurrences of tag
     *
     * @param url URL to be analyzed
     * @param tag HTML-Tag to search in corresponding URL
     * @return Jsoup.Elements containing the extracted HTML-Elements or new Elements if fetching of page was unsuccessful
     */
    public Elements getAllOccurrencesOfTagInPage(String url, String tag) {
        Document htmlPage;
        try {
            htmlPage = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connecting to url failed, please try another url or check your internet connection.");
            System.out.println("Entered URL needs to be of format protocol://domain");
            return new Elements();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Connecting to url failed, please try another url or check your internet connection.");
            System.out.println("Entered URL needs to be of format protocol://domain");
            return new Elements();
        }
        Element htmlBody = htmlPage.body();
        return htmlBody.getElementsByTag(tag);
    }

    /**
     * Extracts the values of occurrences of attribute in elements and returns the values as String Array
     *
     * @param elements  Jsoup.Elements containing HTML-Tags
     * @param attribute Attribute to extract
     * @return String Array containing the values of all occurrences of attribute in elements
     */
    public String[] getAttributeValuesOfElements(Elements elements, String attribute) {
        List<String> attributeList = elements.eachAttr(attribute);
        return attributeList.toArray(new String[attributeList.size()]);
    }

    /**
     * Extracts occurrences of domains and the number of occurrences for each domain from domains
     *
     * @param domains String Array containing URLs
     * @return HashMap containing items with domain as key and the corresponding amount of occurrences as value
     */
    public HashMap<String, Integer> getDomainsAndAmountsOfOccurrencesFromStringArray(String[] domains) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String element : domains) {
            URL url = null;
            try {
                url = new URL(element);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("List of urls contains URL of incorrect format");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("List of urls contains URL of incorrect format");
            }
            String host = url == null ? "" : url.getHost();
            if(host == "") {
                continue;
            }
            String domain = InternetDomainName.from(host).topPrivateDomain().toString();
            if (map.containsKey(domain)) {
                Integer occurrences = map.get(domain);
                occurrences++;
                map.put(domain, occurrences);
            } else {
                map.put(domain, 1);
            }
        }
        return map;
    }
}
