package com.alexnesterov.pages

import org.fluentlenium.adapter.FluentStandalone
import org.fluentlenium.core.hook.wait.Wait
import org.openqa.selenium.Capabilities
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.concurrent.TimeUnit

@Wait
class PasswordPage : FluentStandalone() {

    override fun getWebDriver(): String {
        return "chrome"
    }

    override fun getCapabilities(): Capabilities {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("chromeOptions", options)
        return capabilities
    }

    private lateinit var oldPassword: String

    fun navigateTo(url: String) {
        goTo(url)
    }

    fun clickRefresh() {
        oldPassword = el("h1").text()
        el("#refresh").click()
    }

    fun assertPasswordChanged() {
        await().atMost(5, TimeUnit.SECONDS).until {
            el("h1").text() != oldPassword
        }
    }
}