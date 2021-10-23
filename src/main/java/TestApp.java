import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefBrowserHeadless;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class TestApp {
    public static class MyHeadlessBrowser extends CefBrowserHeadless {
        private final JFrame frame = new JFrame();

        public MyHeadlessBrowser(CefClient client) {
            super(client, "https://webglsamples.org/aquarium/aquarium.html", false, null);
            browser_rect_.setSize(500, 500);
            frame.setSize(500, 500);
            frame.setVisible(true);
        }

        @Override
        public void onPaint(CefBrowser browser, boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width, int height) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            IntBuffer intBuf = buffer
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .asIntBuffer();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    image.setRGB(x, y, intBuf.get());
                }
            }
//            try {
//                ImageIO.write(image, "png", new File("image.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            frame.getGraphics().drawImage(image, 0, 0, width, height, null);
            System.out.println(String.format("onPaint %dx%d", width, height));
        }
    }
    public static void main(String[] args) throws InterruptedException {
        // Perform startup initialization on platforms that require it.
        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            return;
        }

        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = true;
        CefApp cefApp = CefApp.getInstance(settings);
        CefClient cefClient = cefApp.createClient();
        CefBrowser cefBrowser = new MyHeadlessBrowser(cefClient);
        cefBrowser.createImmediately();
        while(true) {
            System.out.println("loading = " + cefBrowser.isLoading());
            Thread.sleep(1000);
        }
    }
}
