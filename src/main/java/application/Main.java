package application;

import org.jsoup.select.Elements;
import util.WebsiteUtil;

import javax.swing.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Boolean websiteAnalyzerActive = true;
        while (websiteAnalyzerActive) {
            String url = JOptionPane.showInputDialog("Please Input Url of the Page you want to analyze");
            WebsiteUtil websiteUtil = new WebsiteUtil();
            Elements foundTags = websiteUtil.getAllOccurrencesOfTagInPage(url, "a");
            String[] hrefValues = websiteUtil.getAttributeValuesOfElements(foundTags, "abs:href");
            HashMap<String, Integer> domainsAndOccurrences = websiteUtil.getDomainsAndAmountsOfOccurrencesFromStringArray(hrefValues);
            domainsAndOccurrences.forEach((key, value) -> System.out.println("\u2022 " + key + " - " + value));
            int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to analyze another url?",
                    null, JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                websiteAnalyzerActive = false;
            }
        }
    }
}
