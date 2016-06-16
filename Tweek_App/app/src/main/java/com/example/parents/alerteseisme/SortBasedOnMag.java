package com.example.parents.alerteseisme;

import java.util.Comparator;

/**
 * Created by Diane on 12/06/2016.
 */
public class SortBasedOnMag implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        Seisme dd1 = (Seisme)o1;
        Seisme dd2 = (Seisme)o2;
        return dd1.getMag().compareToIgnoreCase(dd2.getMag());
    }
}