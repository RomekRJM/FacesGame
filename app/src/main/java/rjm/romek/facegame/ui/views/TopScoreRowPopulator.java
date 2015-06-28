package rjm.romek.facegame.ui.views;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Score;

public class TopScoreRowPopulator {
    public void populate(int positionCounter, Score score, View view) {

        TextView date = (TextView)view.findViewById(R.id.text_date);
        TextView correctAnswers = (TextView)view.findViewById(R.id.text_correct);
        TextView position = (TextView)view.findViewById(R.id.text_position);
        TextView scoreValue = (TextView)view.findViewById(R.id.text_score);
        TextView player = (TextView)view.findViewById(R.id.text_player);

        if(date != null) {
            long time = score.getDate().getTime();
            date.setText(DateUtils.getRelativeTimeSpanString(time));
        }

        if(correctAnswers != null) {
            correctAnswers.setText(score.getCorrectAnswers() + " correct answers");
        }

        if (position != null) {
            positionCounter++;
            position.setText(positionCounter + ".");
        }

        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score.getScore()));
        }

        if (player != null) {
            String playerText = score.getPlayer();
            playerText = StringUtils.defaultString(playerText, "You");
            player.setText(playerText);
        }
    }
}
