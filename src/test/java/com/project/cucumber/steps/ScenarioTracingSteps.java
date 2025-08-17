package com.project.cucumber.steps;

import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Paths;

public class ScenarioTracingSteps {

    @Before
    public void startTracing() {
        PlaywrightCucumberFixture
                .getBrowserContext()
                .tracing()
                .start(new Tracing
                        .StartOptions()
                        .setScreenshots(true)
                        .setSources(true)
                        .setSnapshots(true)
                );
    }

    @After
    public void stopTracing(Scenario scenario) {
        String testName = scenario.getName().replace(" ","-").toLowerCase();
        PlaywrightCucumberFixture
                .getBrowserContext().tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("target/traces/traces-"+testName+".zip")));
    }
}
