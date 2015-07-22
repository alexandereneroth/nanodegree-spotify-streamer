package se.alen.spotifystreamer;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Made from SergeyA's example on
 * http://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview
 * (Modified Dec 27 '14 at 3:08)
 */
public class SpacerVerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public SpacerVerticalItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mSpace;

        if (parent.getChildPosition(view) == 0) {
            outRect.top = mSpace;
        }
    }
}
