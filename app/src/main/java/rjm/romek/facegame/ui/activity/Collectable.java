package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.data.AchievementContract;

import static rjm.romek.facegame.data.AchievementContract.*;

public class Collectable extends Activity {
    private SimpleCursorAdapter adapter;
    private static final String [] FROM = new String[] {AchievementEntry.PRIZE};
    private static final int [] TO = new int[] {R.id.imageViewCollectable};

    private class CollectableViewBinder implements ViewBinder {

        private Context context;

        public CollectableViewBinder(Context context) {
            this.context = context;
        }

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() != R.id.imageViewCollectable)
                return false;

            if (cursor.getInt(cursor.getColumnIndex(AchievementEntry.UNLOCKED)) == 0) {
                return false;
            }

            String prizeName = cursor.getString(cursor
                    .getColumnIndex(AchievementEntry.PRIZE));
            String imageBaseName = StringUtils.substringBefore(prizeName, ".");
            int imageResource = context.getResources()
                    .getIdentifier(imageBaseName, "drawable", context.getPackageName());
            ((ImageView) view).setImageResource(imageResource);
            return true;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collectables);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        AchievementContract achievementContract = new AchievementContract(this);
        adapter = new SimpleCursorAdapter(this, R.layout.collectables_cell,
                achievementContract.getAchievements(), FROM, TO);
        adapter.setViewBinder(new CollectableViewBinder(this));
        gridview.setAdapter(adapter);
    }

}
