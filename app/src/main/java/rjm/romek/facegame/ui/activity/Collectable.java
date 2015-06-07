package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.achievement.Achievements;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.GooglePlayable;
import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.ui.manager.GooglePlayManager;
import rjm.romek.facegame.ui.views.CollectableRowPopulator;

import static rjm.romek.facegame.data.AchievementContract.AchievementEntry;

public class Collectable extends Activity implements View.OnClickListener, GooglePlayable {

    private SimpleCursorAdapter adapter;
    private static final String[] FROM = new String[]{AchievementEntry.PRIZE};
    private static final int[] TO = new int[]{R.id.imageViewCollectable};
    private final Activity _this = this;
    private GooglePlayManager googlePlayManager;
    private AchievementUpdateCallback achievementUpdateCallback;
    private Button shareButton;
    private AchievementContract achievementContract;
    private Map<String, Achievement> achievementCache;

    private class AchievementUpdateCallback implements ResultCallback<Achievements.UpdateAchievementResult> {
        @Override
        public void onResult(Achievements.UpdateAchievementResult res) {
            if (res.getAchievementId() != null) {
                Achievement achievement = achievementCache.get(res.getAchievementId());
                achievement.setPublished(true);
                achievementContract.updateAchievement(achievement);
            }
        }
    }

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

        final AchievementContract achievementContract = new AchievementContract(this);
        final CollectableRowPopulator collectableRowPopulator = new CollectableRowPopulator();
        final CollectableViewBinder collectableViewBinder = new CollectableViewBinder(this);

        adapter = new SimpleCursorAdapter(this, R.layout.collectables_cell,
                achievementContract.getAchievementsCursor(), FROM, TO);
        adapter.setViewBinder(collectableViewBinder);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            private View lastSelected;

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Achievement selected = achievementContract.findById(position + 1);

                if (selected.isUnlocked()) {
                    collectableRowPopulator.populate(selected,
                            findViewById(R.id.collectable_row_top), _this);
                }

                if (lastSelected != null) {
                    lastSelected.setBackgroundResource(R.drawable.grid_border);
                }

                v.setSelected(true);
                v.setBackgroundResource(R.drawable.grid_border_selected);
                lastSelected = v;
            }
        });

        init();
    }

    public void init() {
        achievementContract = new AchievementContract(this);
        shareButton = (Button) findViewById(R.id.shareScoreButton);
        shareButton.setOnClickListener(this);

        googlePlayManager = new GooglePlayManager(this);
        googlePlayManager.init();
        googlePlayManager.updateShareButton();

        achievementUpdateCallback = new AchievementUpdateCallback();
        achievementCache = new HashMap<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googlePlayManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googlePlayManager.stop();
    }

    @Override
    public void onClick(View v) {
        googlePlayManager.setConnectionFailed(false);

        if (!googlePlayManager.isShareButtonActive() || !googlePlayManager.isSignedIn()) return;

        List<Achievement> achievements = achievementContract.getAchievements();

        for(Achievement achievement : achievements) {
            if(achievement.isUnlocked() && !achievement.isPublished()) {
                String playAchievementCode = getPlayAchievementCode(achievement);
                achievementCache.put(playAchievementCode, achievement);

                Games.Achievements.unlockImmediate(googlePlayManager.getGoogleApiClient(),
                        playAchievementCode).setResultCallback(achievementUpdateCallback);
            }
        }

        startActivityForResult(Games.Achievements.getAchievementsIntent(
                        googlePlayManager.getGoogleApiClient()),
                GooglePlayManager.CODE_OK);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Button getShareButton() {
        return shareButton;
    }

    @Override
    public boolean needsPublishing() {
        List<Achievement> achievements = achievementContract.getAchievements();

        for(Achievement achievement : achievements) {
            if(achievement.isUnlocked() && !achievement.isPublished()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String getPlayAchievementName(Achievement achievement) {
        return "achievement_" + achievement.getName().toLowerCase();
    }

    private String getPlayAchievementCode(Achievement achievement) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(getPlayAchievementName(achievement),
                "string", packageName);
        return getString(resId);
    }
}
