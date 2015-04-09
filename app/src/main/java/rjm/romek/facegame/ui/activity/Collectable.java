package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import rjm.romek.facegame.R;
import rjm.romek.facegame.ui.adapter.ImageAdapter;

public class Collectable extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collectables);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

    }
}
