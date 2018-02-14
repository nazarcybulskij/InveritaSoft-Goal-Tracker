package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.NonNull;

/**
 * The callback used to indicate a date has been selected or deselected
 */
public interface OnDateLongSelectedListener {

    /**
     * Called when a user clicks on a day.
     * There is no logic to prevent multiple calls for the same date and state.
     *
     * @param widget   the view associated with this listener
     * @param date     the date that was selected or unselected
     */
    void onDateLongSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date);
}
