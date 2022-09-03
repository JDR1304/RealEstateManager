package com.example.realestatemanager;

import static android.content.ContentValues.TAG;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

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
        assertEquals(162_400, Utils.convertDollarToEuro(priceInDollar));
    }

    @Test
    public void conversionEuroToDollarTest() {
        int priceInEuro = 200_000;
        assertEquals(246_305, Utils.convertEuroToDollar(priceInEuro));
    }

    @Test
    public void isInternetAvailableTest(){
        mockContext = mock(Context.class);
        mockConnectivityManager = mock(ConnectivityManager.class);
        mockNetworkInfo = mock(NetworkInfo.class);
        mockNetwork = mock(Network.class);
        Network [] allNetworks = mockConnectivityManager.getAllNetworks();

        when(mockContext.getSystemService(anyString())).thenReturn(mockConnectivityManager);
        when(mockConnectivityManager.getAllNetworks()).thenReturn(allNetworks);
        when(mockConnectivityManager.getNetworkInfo(mockNetwork)).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
        when(mockNetworkInfo.isConnected()).thenReturn(true);

        boolean value = Utils.isInternetAvailable(mockContext);

        assertEquals(value, true);

    }

}