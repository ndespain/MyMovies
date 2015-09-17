package com.ndes.mymovies.model;

import com.ndes.mymovies.ServiceUtils;

/**
 * Created by neildespain on 9/16/15.
 */
public class SortBy {

    String displayValue;
    String dataValue;

    public SortBy(String displayValue, String dataValue) {
        if (ServiceUtils.isEmpty(displayValue) || ServiceUtils.isEmpty(dataValue)) {
            throw new IllegalArgumentException();
        }
        this.displayValue = displayValue;
        this.dataValue = dataValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

//    public void setValues(String display, String values) {
//        if (ServiceUtils.isEmpty(display) || ServiceUtils.isEmpty(values)) {
//            throw new IllegalArgumentException();
//        }
//
//        displayValue = display;
//        dataValue = values;
//    }

    @Override
    public String toString() {
        return displayValue;
    }
}
