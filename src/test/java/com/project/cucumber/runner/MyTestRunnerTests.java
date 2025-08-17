package com.project.cucumber.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/feature")
@ConfigurationParameter(
        key="cucumber.plugin",
        value="io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm,"+
                "pretty,"+
                "html:target/cucumber-reports/cucumber.html"
)
public class MyTestRunnerTests {

}
