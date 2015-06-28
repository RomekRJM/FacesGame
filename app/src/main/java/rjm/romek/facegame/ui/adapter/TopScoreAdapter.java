package rjm.romek.facegame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Score;
import rjm.romek.facegame.ui.views.TopScoreRowPopulator;

public class TopScoreAdapter extends ArrayAdapter<Score> {

    private final TopScoreRowPopulator topScoreRowPopulator;

    public TopScoreAdapter(Context context, int resource) {
        super(context, resource);
        this.topScoreRowPopulator = new TopScoreRowPopulator();
    }

    public TopScoreAdapter(Context context, int resource, Score[] scores) {
        super(context, resource, scores);
        this.topScoreRowPopulator = new TopScoreRowPopulator();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.top_score_row, null);
        }

        topScoreRowPopulator.populate(position, getItem(position), convertView, getContext());
        return convertView;

    }
}