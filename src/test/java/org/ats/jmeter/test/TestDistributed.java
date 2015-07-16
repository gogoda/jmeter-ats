package org.ats.jmeter.test;


import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.Test;

public class TestDistributed  {
	
	@Test
	public void test(){

        // Engine        
        // jmeter.properties
        JMeterUtils.loadJMeterProperties("./config/bin/jmeter.properties");
        JMeterUtils.setJMeterHome("./config");
        HashTree hashTree = new HashTree();     
        // HTTP Sampler
        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setName("TestGoogle");
        httpSampler.setDomain("www.google.com");
        httpSampler.setPort(80);
        httpSampler.setPath("/");
        httpSampler.setMethod("GET");
        
         // Loop Controller
        TestElement loopCtrl = new LoopController();
        ((LoopController)loopCtrl).setLoops(1);
        ((LoopController)loopCtrl).addTestElement(httpSampler);
        ((LoopController)loopCtrl).setFirst(true);

        // Thread Group
        SetupThreadGroup threadGroup = new SetupThreadGroup();
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController((LoopController)loopCtrl);

        // Test plan
        TestPlan testPlan = new TestPlan("MY TEST PLAN");
        
        
        
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");//$NON-NLS-1$
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        String logFile = "./config/file.jtl";
        ResultCollector logger = new ResultCollector(summer);
        
        SampleSaveConfiguration conf = new SampleSaveConfiguration(true);
//        conf.setResponseData(false);
        logger.setSaveConfig(conf);
        logger.setFilename(logFile);        
        
        
        hashTree.add("testPlan", testPlan);
        hashTree.add("loopCtrl", loopCtrl);
        hashTree.add("threadGroup", threadGroup);
        hashTree.add("httpSampler", httpSampler);        
        //httpSampler.add("httpSampler",logger);
        //hashTree.add(hashTree.getArray()[0], logger);
        //hashTree.add("ctrlogger", logger);
         hashTree.add(hashTree.getArray()[0], logger);
        System.err.println(hashTree.getArray()[0]);
        
		 // run distribute
        JMeterUtils.setProperty(DistributedRunner.RETRIES_NUMBER, "1");
        JMeterUtils.setProperty(DistributedRunner.CONTINUE_ON_FAIL, "true");
//        
        
		    DistributedRunner distributedRunner = new DistributedRunner();
		 
  		 List<String> hosts = new ArrayList<>();
  		 hosts.add("192.168.1.4");
//		 hosts.add("192.168.184.4");
//		 hosts.add("192.168.184.5");
//		 
		  distributedRunner.init(hosts, hashTree);
		
//		 List<String> hosts1 = Arrays.asList("192.168.184.4");
		  distributedRunner.start(hosts);
		
		
//		 distributedRunner.shutdown(hosts);
//		 distributedRunner.stop();
//		 distributedRunner.exit(hosts);
//		 
////		 distributedRunner.stop();
// 	
//		
//		//
//
////        jm.configure(hashTree);
////        jm.run();
//		 System.exit(0);
    
	}

}
