package rjm.romek.facegame.ui.views;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Score;

public class TopScoreRowPopulator {
    public void populate(int positionCounter, Score score, View view, Context context) {

        TextView date = (TextView) view.findViewById(R.id.text_date);
        TextView correctAnswers = (TextView) view.findViewById(R.id.text_correct);
        TextView position = (TextView) view.findViewById(R.id.text_position);
        TextView scoreValue = (TextView) view.findViewById(R.id.text_score);
        TextView player = (TextView) view.findViewById(R.id.text_player);


        long time = score.getDate().getTime();
        date.setText(DateUtils.getRelativeTimeSpanString(time));

        correctAnswers.setText(score.getCorrectAnswers() + " "
                + context.getString(R.string.top_score_correct_answers));

        positionCounter++;
        position.setText(positionCounter + ".");

        scoreValue.setText(String.valueOf(score.getScore()));

        String playerText = score.getPlayer();
        playerText = StringUtils.defaultString(playerText,
                context.getString(R.string.top_score_default_player));
        player.setText(playerText);
    }
}
