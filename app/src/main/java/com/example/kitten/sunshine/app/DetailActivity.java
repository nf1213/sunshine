package com.example.kitten.sunshine.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ShareActionProvider mShareActionProvider;
        private String mForecastString;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @SuppressLint("NewApi")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.detail_text_view);
            Intent intent = getActivity().getIntent();
            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastString = intent.getStringExtra(Intent.EXTRA_TEXT);
                textView.setText(mForecastString);
            }
            return rootView;
        }

        @SuppressLint("NewApi")
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detail_fragment, menu);
            // Locate MenuItem with ShareActionProvider
            MenuItem item = menu.findItem(R.id.action_share);
            // Fetch and store ShareActionProvider
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            if(mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(shareIntent(mForecastString));
            } else {
                Log.v("DetailActivity", "mShareActionProvider is null!!");
            }
        }

        private Intent shareIntent(String text) {
            String hashtag = " #sunshineapp";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text + hashtag);
            return intent;
        }
    }
}
