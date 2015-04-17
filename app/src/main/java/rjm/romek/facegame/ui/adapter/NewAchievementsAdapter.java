package rjm.romek.facegame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.ui.views.CollectableRowPopulator;

public class NewAchievementsAdapter extends ArrayAdapter<Achievement> {

    private final CollectableRowPopulator collectableRowPopulator;

    public NewAchievementsAdapter(Context context, int resource) {
        super(context, resource);
        this.collectableRowPopulator = new CollectableRowPopulator();
    }

    public NewAchievementsAdapter(Context context, int resource, Achievement[] achievement) {
        super(context, resource, achievement);
        this.collectableRowPopulator = new CollectableRowPopulator();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.collectable_row, null);
        }

        collectableRowPopulator.populate(getItem(position), convertView, getContext());
        return convertView;

    }
}
