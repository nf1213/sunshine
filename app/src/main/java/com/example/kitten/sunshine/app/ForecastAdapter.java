package com.example.kitten.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {
    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        boolean isMetric = Utility.isMetric(mContext);
        String highLowStr = Utility.formatTemperature(high, isMetric) + "/" + Utility.formatTemperature(low, isMetric);
        return highLowStr;
    }

    /*
        This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
        string.
     */
    private String convertCursorRowToUXFormat(Cursor cursor) {
        String highAndLow = formatHighLows(
                cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
                cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));

        return Utility.formatDate(cursor.getLong(ForecastFragment.COL_WEATHER_DATE)) +
                " - " + cursor.getString(ForecastFragment.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);

        ImageView iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        iconView.setImageResource(R.drawable.ic_launcher);

        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        TextView dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        TextView descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        descriptionView.setText(description);

        boolean isMetric = Utility.isMetric(context);

        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(high, isMetric));

        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
        lowView.setText(Utility.formatTemperature(low, isMetric));
    }
}
