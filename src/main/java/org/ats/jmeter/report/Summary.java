package org.ats.jmeter.report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Summary {
  private int _millisPerBucket;

  private static final String DECIMAL_PATTERN = "#,##0.0##";
  private static final double MILLIS_PER_SECOND = 1000.0;

  public int count = 0;
  public int total_t = 0;
  public int max_t = 0;
  public int min_t = Integer.MAX_VALUE;
  public int total_conn = 0;
  public int max_conn = 0;
  public int min_conn = Integer.MAX_VALUE;
  public int failures = 0;
  public long first_ts = Long.MAX_VALUE;
  public long last_time_fn = 0;
  public long last_ts = 0;
  public double std_dev = 0;
  public double avg = 0;
  public long total_by = 0;
  public double throughtput = 0;
  public Map<String, Integer> rcMap = new HashMap<String, Integer>();  
  public Map<Long, Integer> hitsPersecond = new HashMap<Long, Integer>();
  public Map<Long, Integer> transPersecond = new HashMap<Long, Integer>();

  public Summary() {
  }

  public Summary(int _millisPerBucket) {
    this._millisPerBucket = _millisPerBucket;
  }
  
  public String toBasicString() {

    DecimalFormat df = new DecimalFormat(DECIMAL_PATTERN);

    
    List<String> millisStr1 = new ArrayList<String>();
    List<String> millisStr2 = new ArrayList<String>();
    
    
    
    Iterator iter1 = hitsPersecond.entrySet().iterator();
    while (iter1.hasNext()) {
      Map.Entry millisEntry = (Map.Entry) iter1.next();
      Long bucket = (Long) millisEntry.getKey();
      Integer bucketCount = (Integer) millisEntry.getValue();

      long minMillis = bucket.longValue() * _millisPerBucket;
      long maxMillis = (bucket.longValue() + 1) * _millisPerBucket;

      Date date = new Date(minMillis);
      String strDate = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(date);

      Date date2 = new Date(maxMillis);
      String strDate2 = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(date2);

      millisStr1.add(strDate + "- " + strDate2 + "= " + bucketCount);
    }
    
    

    Iterator iter2 = transPersecond.entrySet().iterator();

    while (iter2.hasNext()) {
      Map.Entry millisEntry = (Map.Entry) iter2.next();
      Long bucket = (Long) millisEntry.getKey();
      Integer bucketCount = (Integer) millisEntry.getValue();

      long minMillis = bucket.longValue() * _millisPerBucket;
      long maxMillis = (bucket.longValue() + 1) * _millisPerBucket;

      Date date = new Date(minMillis);
      String strDate = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(date);

      Date date2 = new Date(maxMillis);
      String strDate2 = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(date2);

      millisStr2.add(strDate + "- " + strDate2 +  "= " + bucketCount);
    }
     
    Collections.sort(millisStr1);
    Collections.sort(millisStr2);
    return "cnt: " + count + ", " + "avg t: " + (total_t / count) + " ms, "
        + "max t: " + max_t + " ms, " + "min t: " + min_t + " ms, " + " standard deviation: " + std_dev+ ","
        + "result codes: " + rcMap + ", " + "failures: " + failures + ", "
        + "cnt by time: " + " \n Hit person second " + millisStr1 + " \n Tran per sencond" +millisStr2 ;

  }

  public String toAdvancedString() {
    double secondsElaspsed = (last_ts - first_ts) / MILLIS_PER_SECOND;
    long countPerSecond = Math.round(count / secondsElaspsed);
    return "kb/second:" + (double)total_by/(last_time_fn -first_ts)/1.024 + "kb/s"
        + " thoughtput : " + (double)count/(last_time_fn -first_ts)*1000 + "/second"
        + " avg conn: " + (total_conn / count) + " ms, " + "max conn: "
        + max_conn + " ms, " + "min conn: " + min_conn + " ms, "
        + "elapsed seconds: " + Math.round(secondsElaspsed) + " s, "
        + "cnt per second: " + countPerSecond;
  }

}
