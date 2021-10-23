import kr.jclab.wmbs.cef.VirtualScreenCefClient;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestApp {
    public static void main(String[] args) throws InterruptedException {
        // Perform startup initialization on platforms that require it.
        List<String> argList = new ArrayList<>();
        for (String a : args) argList.add(a);
        argList.addAll(Arrays.asList(
                "--off-screen-rendering-enabled",
                "--disable-gpu",
                "--enable-logging=stderr"
        ));
        if (!CefApp.startup(argList.toArray(new String[0]))) {
            System.out.println("Startup initialization failed!");
            return;
        }

        CefSettings settings = new CefSettings();
        settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_VERBOSE;
//        settings.log_file = ""
        settings.windowless_rendering_enabled = true;
        settings.remote_debugging_port = 9987;
        settings.log_file = "aa.txt";
        CefApp cefApp = CefApp.getInstance(settings);
        VirtualScreenCefClient cefClient = cefApp.createClient(new VirtualScreenCefClient());
        CefBrowser cefBrowser = cefClient.createBrowser("https://google.com", true, false);
        cefBrowser.createImmediately();
        while(true) {
            System.out.println("loading = " + cefBrowser.isLoading());
            Thread.sleep(1000);
        }

    }
}
