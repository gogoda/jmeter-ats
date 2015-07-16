package org.ats.jmeter.report;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JtlHandler extends DefaultHandler {
  
  private int _millisPerBucket;
  private String urlTotal = "*SummaryReport*";
  public String getUrlTotal() {
    return urlTotal;
  }


  public void setUrlTotal(String urlTotal) {
    this.urlTotal = urlTotal;
  }
  private Map<String, Summary> totalUrlMap;
  
  public JtlHandler(int millisPerBucket,Map<String, Summary> totalUrlMap ) {
    this._millisPerBucket = millisPerBucket;    
    this.totalUrlMap = totalUrlMap;
  }
  
  
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)   throws SAXException {
    if(qName.equalsIgnoreCase("httpSample")){
      HttpSamplerObj httpSamplerObj = new HttpSamplerObj(attributes);
      Summary summaryAll = totalUrlMap.get(urlTotal);
      if(summaryAll == null){
        summaryAll = new Summary(_millisPerBucket);
        totalUrlMap.put(urlTotal, summaryAll);
      }
      caculate(summaryAll, httpSamplerObj);
      String url = httpSamplerObj.getGroup_lb();
      Summary summaryUrl = totalUrlMap.get(url);
      if(summaryUrl == null) {
        summaryUrl = new Summary(_millisPerBucket);
        totalUrlMap.put(url, summaryUrl);
      }
      caculate(summaryUrl, httpSamplerObj);
    }
  }

  @Override
  public void endElement(String uri, 
     String localName, String qName) throws SAXException {
     
  }

  @Override
  public void characters(char ch[], 
     int start, int length) throws SAXException {
    
  }
  
 public void caculate(Summary sumary, HttpSamplerObj httpSamplerObj){
   
   sumary.count ++;
   long timeStamp = httpSamplerObj.getGroup_ts();   
   sumary.last_ts = Math.max(sumary.last_ts, timeStamp);
   sumary.first_ts = Math.min(sumary.first_ts, timeStamp);
   
   int time = httpSamplerObj.getGroup_t();
   sumary.total_t += time;
   sumary.max_t = Math.max(sumary.max_t, time);
   sumary.min_t = Math.min(sumary.min_t, time);
   
   int conn = time - httpSamplerObj.getGroup_lt();
   sumary.total_conn += conn;
   sumary.max_conn = Math.max(sumary.max_conn, conn);
   sumary.min_conn = Math.min(sumary.min_conn, conn);
   
   sumary.last_time_fn = Math.max(sumary.last_time_fn, timeStamp+time);
   sumary.total_by += httpSamplerObj.getGroup_by();
   
   
  //response code
   
   String rc = httpSamplerObj.getGroup_rc();
   Integer count = sumary.rcMap.get(rc);
   if(count == null) {
       count = new Integer(0);
   }   
   sumary.rcMap.put(rc, new Integer(count.intValue() + 1));
   
   
   //His per second
   Long bucketHis =  new Long(timeStamp/_millisPerBucket);
   count = sumary.hitsPersecond.get(bucketHis);
   if(count == null){
     count = new Integer(0);
   }
   sumary.hitsPersecond.put(bucketHis, new Integer(count.intValue() + 1));
   
   //tran per second
   
   long responeTime = timeStamp+time;
   Long bucketTrans = new Long(responeTime/_millisPerBucket);
   count = sumary.transPersecond.get(bucketTrans);
   if(count == null){
     count = new Integer(0);
   }
   
   //standard deviation
   
   double prev_avg = sumary.avg;
   sumary.avg = (double) sumary.total_t/sumary.count;
   sumary.transPersecond.put(bucketTrans, new Integer(count.intValue()+1));   
   
   sumary.std_dev = caculateStDev(sumary.count-1, time, prev_avg, sumary.std_dev, sumary.avg);   
   
   //count false
   if(!httpSamplerObj.getGroup_s().equalsIgnoreCase("true")) {
       sumary.failures ++;
   }

 }
 private double caculateStDev(int number, int elementN1, double prev_avg ,double pre_std_dev, double current_avg){
   double delta = current_avg - prev_avg;
   double variance = (number*delta*delta +
       (elementN1-current_avg)*(elementN1-current_avg) +
       number*pre_std_dev*pre_std_dev)/(number+1);
   return Math.sqrt(variance);
 }
  

}
