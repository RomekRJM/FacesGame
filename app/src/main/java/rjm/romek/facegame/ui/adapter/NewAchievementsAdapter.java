package rjm.romek.facegame.ui.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Achievement;

public class NewAchievementsAdapter extends ArrayAdapter<Achievement> {

    public NewAchievementsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public NewAchievementsAdapter(Context context, int resource, Achievement[] achievement) {
        super(context, resource, achievement);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.collectable_row, null);
        }

        Achievement achievement = getItem(position);

        if (achievement != null) {

            TextView textFaceName = (TextView) v.findViewById(R.id.text_face_name);
            TextView textFor = (TextView) v.findViewById(R.id.text_for);
            TextView textDate = (TextView) v.findViewById(R.id.text_date);
            ImageView imageViewFaceRow = (ImageView) v.findViewById(R.id.imageViewFaceRow);

            if (textFaceName != null) {
                textFaceName.setText(achievement.getName());
            }
            if (textFor != null) {
                textFor.setText(achievement.getDescription());
            }
            if (textDate != null) {
                textDate.setText(DateUtils
                        .getRelativeTimeSpanString(achievement.getLastModified().getTime()));
            }
            if (imageViewFaceRow != null) {
                String imageBaseName = StringUtils.substringBefore(achievement.getPrize(), ".");
                int imageResource = getContext().getResources()
                        .getIdentifier(imageBaseName, "drawable", getContext().getPackageName());
                imageViewFaceRow.setImageResource(imageResource);
            }
        }

        return v;

    }
}
