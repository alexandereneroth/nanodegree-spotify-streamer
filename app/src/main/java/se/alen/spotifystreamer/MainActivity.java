package se.alen.spotifystreamer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private final String TAG = getClass().getName();
    private SpotifyService mSpotifyService;
    private RecyclerView mResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultsList = (RecyclerView) findViewById(R.id.results_list);
        mResultsList.setHasFixedSize(true);//TODO try without
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mResultsList.setLayoutManager(llm);
        mResultsList.setAdapter(new SearchResultListAdapter(getApplicationContext()));
        mResultsList.addItemDecoration(new SpacerVerticalItemDecoration(getResources().getDimensionPixelSize(R.dimen.list_vertical_spacing)));

        final SpotifyService spotifyService = new SpotifyApi().getService();

        TextView searchField = (TextView) findViewById(R.id.search_field);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                String searchedString = textView.getText().toString();
                spotifyService.searchArtists(searchedString, new Callback<ArtistsPager>() {
                            @Override
                            public void success(final ArtistsPager artistsPager, Response response) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mResultsList.setAdapter(new SearchResultListAdapter(getApplicationContext(), artistsPager.artists.items));
                                        if (artistsPager.artists.total < 1) {
                                            Toast.makeText(getApplicationContext(), "No artists found.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void failure(final RetrofitError error) {
                                Log.d(TAG, error.toString());
                                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {

                                      Toast.makeText(getApplicationContext(), "Error when searching.\nHTTP status code " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                                  }
                              });
                            }
                        }
                );
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSearchResultsAdapter(List<Artist> artists) {
    }

    private class SearchFieldOnEditorActionListener implements TextView.OnEditorActionListener {
        private final String TAG = getClass().getName();

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String searchedString = v.getText().toString();
            Toast.makeText(getApplicationContext(), searchedString, Toast.LENGTH_SHORT).show();


            return true;
        }
    }
}
