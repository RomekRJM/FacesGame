package rjm.romek.facegame.ui.activity;

import rjm.romek.facegame.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Game extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
