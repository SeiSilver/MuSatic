package com.viostaticapp.present._common;

import java.util.ArrayList;

public interface BaseOnClickedEvent<T> {

    void onClicked(double value);

    void onClicked(int value);

    void onClicked(float value);

    void onClicked(String value);

    void onClicked(T value);

    void onClicked(ArrayList<T> values);

}
