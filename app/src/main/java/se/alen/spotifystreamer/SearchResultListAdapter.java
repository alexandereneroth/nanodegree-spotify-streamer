package se.alen.spotifystreamer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

public class SearchResultListAdapter extends RecyclerView.Adapter<SearchResultListAdapter.SearchResultViewHolder> {

    public List<Artist> mArtists;
    private final Context mContext;

    public SearchResultListAdapter(Context context) {
        mContext = context;
    }

    public SearchResultListAdapter(Context context, List<Artist> artists) {
        mContext = context;
        mArtists = artists;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View searchResult = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_result, viewGroup, false);

        return new SearchResultViewHolder(searchResult);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder viewHolder, int i) {
        viewHolder.mSearchResultName.setText(mArtists.get(i).name);
        List<Image> searchResultImages = mArtists.get(i).images;

        if (searchResultImages.size() > 1) {
            Picasso.with(mContext).load(mArtists.get(i).images.get(1).url).into(viewHolder.mSearchResultImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mArtists == null) {
            return 0;
        }
        return mArtists.size();
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        protected ImageView mSearchResultImage;
        protected TextView mSearchResultName;

        public SearchResultViewHolder(View itemView) {
            super(itemView);

            mSearchResultImage = (ImageView) itemView.findViewById(R.id.search_result_image);
            mSearchResultName = (TextView) itemView.findViewById(R.id.search_result_name);
        }
    }
}
