package com.example.realestatemanager;

import static android.content.ContentValues.TAG;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
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
}