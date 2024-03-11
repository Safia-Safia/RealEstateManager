package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context context;
    @Before
    public void setUp()  {
        context = ApplicationProvider.getApplicationContext();
    }


    @Test
    public void isWifiAvailable() {
        assertTrue(Utils.isInternet_Available(context));
    }

    @Test
    public void isWifiUnavailable() {
        assertFalse(Utils.isInternet_Available(context));

    }

    @Test
    public void testWifiDisabled() {
        try {
            grantWifiPermission();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Get WifiManager
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);

        // Disable WiFi
        wifiManager.setWifiEnabled(false);

        // Check WiFi state after a delay (adjust delay as needed)
        try {
            Thread.sleep(5000);  // 2 seconds delay
            assertTrue("WiFi is still enabled", !wifiManager.isWifiEnabled());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if WiFi is disabled
        assertTrue("WiFi is still enabled", !wifiManager.isWifiEnabled());
    }

    private void grantWifiPermission() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            InstrumentationRegistry.getInstrumentation().getUiAutomation()
                    .executeShellCommand("appops set " +
                            InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageName() +
                            " CHANGE_WIFI_STATE allow");
        } else {
            InstrumentationRegistry.getInstrumentation().getUiAutomation()
                    .executeShellCommand("pm grant " +
                            InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageName() +
                            " android.permission.CHANGE_WIFI_STATE");
        }
    }
}
