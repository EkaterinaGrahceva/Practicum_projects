package praktikum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WebDriverFactory {

    public static WebDriver getDriver() {
        String browserName = System.getProperty("browser", "chrome");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equals(browserName)) {
            String yandexBinaryPath = null;
            String manualPath = System.getProperty("yandex.path");

            if (manualPath != null && new File(manualPath).exists()) {
                yandexBinaryPath = manualPath;
            } else {
                List<String> potentialPaths = Arrays.asList(
                        "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe",
                        "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe",
                        System.getProperty("user.home") + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe"
                );
                for (String path : potentialPaths) {
                    if (new File(path).exists()) {
                        yandexBinaryPath = path;
                        break;
                    }
                }
            }

            if (yandexBinaryPath == null) {
                throw new RuntimeException("Yandex Browser executable not found.");
            }

            options.setBinary(yandexBinaryPath);
        }

        return new ChromeDriver(options);
    }
}