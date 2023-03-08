package FeatureFiles;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.gherkin.model.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.intuit.karate.Results;
import com.intuit.karate.core.Result;
import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.Step;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reporter {

    private ExtentReports extentReport;
    private String reportDir;
    private String reportTitle = "Loan App Test Report";
    private ExtentSparkReporter extentSparkReporter;
    private ExtentTest featureNode;
    private Results testResults;
    private ExtentTest scenarioNode;
    private String scenarioTitle="";
    private String featureTitle = "";

    public Reporter(String reportDirectory)
    {
        extentReport = new ExtentReports();
        this.reportDir = reportDirectory;
    }
    public Reporter withKarateResult(Results results)
    {
        this.testResults = results;
        return  this;
    }

    private Stream<ScenarioResult> getScenarioResults() {
        return this.testResults.getScenarioResults();
    }
    private  String getFeatrueName(ScenarioResult scenarioResult){
        return scenarioResult.getScenario().getFeature().getName();
    }

    private String getFeatureDescripton(ScenarioResult scenarioResult){
        return  scenarioResult.getScenario().getFeature().getDescription();
    }
    private String getScenarioTitle(ScenarioResult scenarioResult) {
        String title =  scenarioResult.getScenario().getNameAndDescription().toString();
        System.out.println(title);
        return  title;
    }

    private ExtentTest createFeatureNode(String featureName, String featureDesc){
        if(this.featureTitle.equalsIgnoreCase(featureName)){
            return featureNode;
        }
        this.featureTitle = featureName;
        featureNode = extentReport.createTest(Feature.class,featureName,featureDesc);
        return featureNode;
    }
    private ExtentTest createScenarioNode(ExtentTest featureNode, String scenarioTitle) {
        // if the title of scenario is same, I will return same instance of extent test
        // else I will create a new instance and then return it

        if (this.scenarioTitle.equalsIgnoreCase(scenarioTitle)) {
            return scenarioNode;
        }
        this.scenarioTitle = scenarioTitle;
        scenarioNode = featureNode.createNode(Scenario.class, scenarioTitle);
        return scenarioNode;
    }
    private String getStepTitle(String type, String stepText) {
        return type.startsWith("*") ? stepText : type + " " + stepText;
    }

    private void addStatus(ExtentTest stepNode, String status, Throwable error, String stepLogs) {
        switch (status) {
            case "passed":
                stepNode.pass("");
                break;
            case "failed":
                stepNode.fail(error);
                break;
            default:
                stepNode.skip("");
                break;
        }
        if (stepLogs != null && !stepLogs.isEmpty())
            stepNode.info(String.format("[print] %s", stepLogs));
    }

    private  void addScenarioStep(ExtentTest scenarioNode, Step step, Result result, String stepLogs)
    {
        String actionType =  step.getPrefix();
        String stepTitle =  step.getText();
        String executionStatus = result.getStatus();
        Throwable error = result.getError();
        ExtentTest stepNode;

        switch (actionType){
            case "Given":
                stepNode = scenarioNode.createNode(Given.class,getStepTitle(actionType,stepTitle));
                addStatus(stepNode,executionStatus,error,stepLogs);
                break;
            case "When":
                stepNode = scenarioNode.createNode(When.class,actionType,stepTitle);
                addStatus(stepNode,executionStatus,error,stepLogs);
                break;
            case "Then":
                stepNode = scenarioNode.createNode(Then.class,actionType,stepTitle);
                addStatus(stepNode,executionStatus,error,stepLogs);
                break;
            case "And":
                stepNode = scenarioNode.createNode(And.class,actionType,stepTitle);
                addStatus(stepNode,executionStatus,error,stepLogs);
                break;
            default:
                stepNode = scenarioNode.createNode(actionType,getStepTitle(actionType,stepTitle));
                addStatus(stepNode,executionStatus,error,stepLogs);
                break;
        }
    }

    private void setConfig() {
        extentSparkReporter.config().enableOfflineMode(true);
        extentSparkReporter.config().setDocumentTitle(reportTitle);
        extentSparkReporter.config().setTimelineEnabled(true);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
    }

    public void generateTestReport()
    {
        try{

            if(this.reportDir!=null && this.testResults!=null)
            {
                extentSparkReporter = new ExtentSparkReporter(reportDir);
                extentReport.attachReporter(extentSparkReporter);
                setConfig();
                List<ScenarioResult> scenarioResults = getScenarioResults().collect(Collectors.toList());
                if(scenarioResults.stream().count()>0)
                {
                   scenarioResults = scenarioResults.stream().filter(x->{

                      return (x.getScenario().getName()!=null) && !(x.getScenario().getName().isEmpty());
                   }).collect(Collectors.toList());
                }
                scenarioResults.forEach((scenarioResult) ->{
                    String featureName = getFeatrueName(scenarioResult);
                    String featureDesc = getFeatureDescripton(scenarioResult);
                    ExtentTest featureNode = createFeatureNode(featureName,featureDesc);
                    String scenarioTitle = getScenarioTitle(scenarioResult);
                    ExtentTest scenarioNode = createScenarioNode(featureNode, scenarioTitle);
                    scenarioResult.getStepResults().forEach((steps) ->{

                        addScenarioStep(scenarioNode,steps.getStep(),steps.getResult(),steps.getStepLog());
                    });
                });
                extentReport.flush();
            }
        }
        catch (Exception ex) {

        }

    }

}
