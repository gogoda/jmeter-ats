package org.ats.jmeter.report;

import org.xml.sax.Attributes;

public class HttpSamplerObj {
 
/*
  by  Bytes
  de  Data encoding
  dt  Data type
  ec  Error count (0 or 1, unless multiple samples are aggregated)
  hn  Hostname where the sample was generated
  it  Idle Time = time not spent sampling (milliseconds) (generally 0)
  lb  Label
  lt  Latency = time to initial response (milliseconds) - not all samplers support this
  ct  Connect Time = time to establish the connection (milliseconds) - not all samplers support this
  na  Number of active threads for all thread groups
  ng  Number of active threads in this group
  rc  Response Code (e.g. 200)
  rm  Response Message (e.g. OK)
  s Success flag (true/false)
  sc  Sample count (1, unless multiple samples are aggregated)
  t Elapsed time (milliseconds)
  tn  Thread Name
  ts  timeStamp (milliseconds since midnight Jan 1, 1970 UTC)
  */
 
  private  int group_t =  0;
  private  int group_lt = 0; 
  private  long group_ts = 0; 
  private  String group_s =  ""; 
  private  String group_lb = "";
  private  String group_rc = "";
  private  String group_rm = "";
  private  String group_tn = "";
  private  String group_dt = "";
  private  String group_de = "";
  private  int group_by = 0;
  private  int group_sc = 0;
  private  int group_ec = 0;
  private  int group_ng = 0;
  private  int group_na = 0;
 
  

  
  public HttpSamplerObj() {
    
  }
  public HttpSamplerObj(Attributes attributes) {
    this.group_t = Integer.parseInt(attributes.getValue("t"));
 //   this.group_it = Integer.parseInt(attributes.getValue("it"));
    this.group_lt = Integer.parseInt(attributes.getValue("lt"));
   // this.group_ct = Integer.parseInt(attributes.getValue("ct"));
    this.group_ts = Long.parseLong(attributes.getValue("ts"));
    
    
    this.group_by = Integer.parseInt(attributes.getValue("by"));
    this.group_sc = Integer.parseInt(attributes.getValue("sc"));
    this.group_ec = Integer.parseInt(attributes.getValue("ec"));
    this.group_ng = Integer.parseInt(attributes.getValue("ng"));
    this.group_na = Integer.parseInt(attributes.getValue("na"));
    
    
    this.group_rc = attributes.getValue("rc");
    this.group_s = attributes.getValue("s");
    this.group_lb = attributes.getValue("lb");
    this.group_rm = attributes.getValue("rm");
    this.group_tn = attributes.getValue("tn");
    this.group_dt = attributes.getValue("dt");
    this.group_de = attributes.getValue("de");
   
    
    
  }
  public int getGroup_t() {
    return group_t;
  }
  public void setGroup_t(int group_t) {
    this.group_t = group_t;
  }
  
  public int getGroup_lt() {
    return group_lt;
  }
  public void setGroup_lt(int group_lt) {
    this.group_lt = group_lt;
  }
  
  public long getGroup_ts() {
    return group_ts;
  }
  public void setGroup_ts(long group_ts) {
    this.group_ts = group_ts;
  }
  public String getGroup_s() {
    return group_s;
  }
  public void setGroup_s(String group_s) {
    this.group_s = group_s;
  }
  public String getGroup_lb() {
    return group_lb;
  }
  public void setGroup_lb(String group_lb) {
    this.group_lb = group_lb;
  }
  public String getGroup_rc() {
    return group_rc;
  }
  public void setGroup_rc(String group_rc) {
    this.group_rc = group_rc;
  }
  public String getGroup_rm() {
    return group_rm;
  }
  public void setGroup_rm(String group_rm) {
    this.group_rm = group_rm;
  }
  public String getGroup_tn() {
    return group_tn;
  }
  public void setGroup_tn(String group_tn) {
    this.group_tn = group_tn;
  }
  public String getGroup_dt() {
    return group_dt;
  }
  public void setGroup_dt(String group_dt) {
    this.group_dt = group_dt;
  }
  public String getGroup_de() {
    return group_de;
  }
  public void setGroup_de(String group_de) {
    this.group_de = group_de;
  }
  public int getGroup_by() {
    return group_by;
  }
  public void setGroup_by(int group_by) {
    this.group_by = group_by;
  }
  public int getGroup_sc() {
    return group_sc;
  }
  public void setGroup_sc(int group_sc) {
    this.group_sc = group_sc;
  }
  public int getGroup_ec() {
    return group_ec;
  }
  public void setGroup_ec(int group_ec) {
    this.group_ec = group_ec;
  }
  public int getGroup_ng() {
    return group_ng;
  }
  public void setGroup_ng(int group_ng) {
    this.group_ng = group_ng;
  }
  public int getGroup_na() {
    return group_na;
  }
  public void setGroup_na(int group_na) {
    this.group_na = group_na;
  }
 
  
    
}
