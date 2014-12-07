package rjm.romek.facegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenu extends Activity implements OnClickListener{
	
	public static String QUIT_FLAG = "quit";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		findViewById(R.id.new_game_button).setOnClickListener(this);
		findViewById(R.id.records_button).setOnClickListener(this);
		findViewById(R.id.about_the_game_button).setOnClickListener(this);
		findViewById(R.id.quit_button).setOnClickListener(this);
		
		if( getIntent().getBooleanExtra(QUIT_FLAG, false)){
	        finish();
	        return;
	    }
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.new_game_button:
				
				break;
				
			case R.id.records_button:
				
				break;
				
			case R.id.about_the_game_button:
				
				break;
				
			case R.id.quit_button:
				Intent intent = new Intent(this, MainMenu.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    intent.putExtra(QUIT_FLAG, true);
			    startActivity(intent);
			    finish();
				break;
		}
		
	}
	
}
