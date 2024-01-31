package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

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
}
