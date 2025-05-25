package com.project;

import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class ChromiumCustomOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
        return new Options().setBrowserName("chromium")
                .setHeadless(false)
                .setTestIdAttribute("data-test")
                .setLaunchOptions(
                        new com.microsoft.playwright.BrowserType.LaunchOptions()
                                .setEnv(System.getenv())
                                .setDownloadsPath(java.nio.file.Paths.get("downloads"))
                                //.setProxy("http://proxy.example.com:8080")
                                //.setSlowMo(50)
                                .setArgs(java.util.Arrays.asList(
                                        "--no-sandbox",
                                        "--disable-gpu",
                                        "--disable-extensions"
                                        )
                                )
                );
    }
}
