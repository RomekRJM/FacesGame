package rjm.romek.facegame.ui.views;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Achievement;

public class CollectableRowPopulator {
    public void populate(Achievement achievement, View view, Context context) {

        if (achievement != null) {

            TextView textFaceName = (TextView) view.findViewById(R.id.text_face_name);
            TextView textFor = (TextView) view.findViewById(R.id.text_for);
            TextView textDate = (TextView) view.findViewById(R.id.text_date);
            ImageView imageViewFaceRow = (ImageView) view.findViewById(R.id.imageViewFaceRow);

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
                int imageResource = context.getResources()
                        .getIdentifier(imageBaseName, "drawable", context.getPackageName());
                imageViewFaceRow.setImageResource(imageResource);
            }
        }
    }
}
