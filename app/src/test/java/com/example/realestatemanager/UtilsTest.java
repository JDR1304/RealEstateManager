package com.example.realestatemanager;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Mock
    Context mockContext;

    @Mock
    ConnectivityManager mockConnectivityManager;
    @Mock
    NetworkInfo mockNetworkInfo;

    @Mock
    Network mockNetwork;

    @Test
    public void getTodayDateTest() {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        assertEquals(currentDate, Utils.getTodayDate());
    }

    @Test
    public void conversionDollarToEuroTest() {
        int priceInDollar = 200_000;
        assertEquals(198_000, Utils.convertDollarToEuro(priceInDollar));
    }

    @Test
    public void conversionEuroToDollarTest() {
        int priceInEuro = 200_000;
        assertEquals(202_020, Utils.convertEuroToDollar(priceInEuro));
    }

    @Test
    public void isInternetAvailableTest() throws IOException {
        Network [] allNetworks = new Network[1];
        allNetworks[0] = mockNetwork;

        when(mockContext.getSystemService(anyString())).thenReturn(mockConnectivityManager);
        when(mockConnectivityManager.getAllNetworks()).thenReturn(allNetworks);
        when(mockConnectivityManager.getNetworkInfo(mockNetwork)).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        when(mockNetworkInfo.isConnected()).thenReturn(true);

        boolean value = Utils.isInternetAvailable(mockContext);

        assertTrue(value);

        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
        when(mockNetworkInfo.isConnected()).thenReturn(true);

        boolean valueMobile = Utils.isInternetAvailable(mockContext);

        assertTrue(valueMobile);

        assertTrue(isOnline());

    }

    public boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(socketAddress, timeoutMs);
            sock.close();
            return true;

        } catch (IOException e) { return false; }
    }
}