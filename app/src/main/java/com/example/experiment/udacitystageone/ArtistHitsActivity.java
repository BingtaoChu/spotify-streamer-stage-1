package com.example.experiment.udacitystageone;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by Bingtao on 5/30/2015.
 */
public class ArtistHitsActivity extends AppCompatActivity {

    private Context ctx = this;
    private String ArtistName = "";
    private String ArtistID = "";
    private ListView trackResults = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_hits);
        Initialize();
    }

    private void Initialize() {
        Intent intent = getIntent();
        ArtistName = intent.getStringExtra(getString(R.string.intentMsgArtistName));
        ArtistID = intent.getStringExtra(getString(R.string.intentMsgArtistID));

        trackResults = (ListView)findViewById(R.id.listTracks);

        // Reference: http://stackoverflow.com/questions/14297178/setting-action-bar-title-and-subtitle
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle(ArtistName);
        }

        // This code is used to get tracks
        TopTracksForArtistTask trackTask = new TopTracksForArtistTask(this, getString(R.string.topTracksCountryCode));
        trackTask.execute(ArtistID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setArtistTracks(Tracks results) {
        TrackResultAdapter resultAdapter = new TrackResultAdapter(ctx, results.tracks);
        trackResults.setAdapter(resultAdapter);
    }
}