package com.ac_companion.wegner;

public interface Catchable {
    boolean isCatchable(int hour, int month, boolean southActive);
    boolean isCatchableAtMonth(int month, boolean southActive);
    String getName();
    int getId();
}
