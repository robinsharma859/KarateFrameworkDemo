package FeatureFiles;
import ch.qos.logback.core.util.FileUtil;
import com.intuit.karate.*;
import com.intuit.karate.junit5.Karate;
import com.intuit.karate.Runner.Builder;
import com.intuit.karate.junit5.*;
import com.intuit.karate.junit5.Karate.Test;

import java.io.File;
import java.io.File.*;


public class TestRunner {

    @org.junit.jupiter.api.Test
    public void RunTest() {
        Builder aRunner = new Builder();

        // aRunner.path("classpath:FeatureFiles");
        System.out.println("classpath");
        aRunner.path("classpath:FeatureFiles");
        aRunner.tags("@Smoke");
        Results results = aRunner.parallel(1);
        System.out.println("Total feature " +  results.getFeaturesTotal());
         System.out.println("Total sceanio Total " + results.getScenariosTotal());
          System.out.println("Total sceanio Passed " + results.getScenariosPassed());
          Reporter reporter = new Reporter(results.getReportDir()).withKarateResult(results);
          reporter.generateTestReport();
    }

    /*
    @Test
    public Karate ExecuteTest() {
        return Karate.run("getRequest").relativeTo(getClass());
    }

    @Test
    public Karate ExecuteTest1() {
        return Karate.run("PostRequest").relativeTo(getClass());
    }
    
     */

}