package org.ats.jmeter.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



public class ReportParsers {

  public static void main(String argv[]) {
    int _millisPerBucket = 1000;    
    Map<String, Summary> totalUrlMap = new HashMap<String, Summary>();
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      JtlHandler jtlhandler = new JtlHandler(_millisPerBucket, totalUrlMap);
      saxParser.parse("C:\\tmp\\vnexpress.jtl", jtlhandler);
      Summary summaryAll = totalUrlMap.get(jtlhandler.getUrlTotal());
      if(summaryAll.count == 0) {
        System.out.println("No results found!");
        return;
    }
    
    Iterator<Entry<String, Summary>> iter = totalUrlMap.entrySet().iterator();        
    while(iter.hasNext()) {
        Map.Entry<String, Summary> entry = iter.next();
        String url = entry.getKey();
        Summary totals = entry.getValue();        
        System.out.println("URL: " + url);
        System.out.println(totals.toBasicString());
        System.err.println("ADVANCED\n");
        System.out.println(summaryAll.toAdvancedString());
        System.out.println("");
    }
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}