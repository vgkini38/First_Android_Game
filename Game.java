package com.example.spaceprowler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends Activity {
	
	
	final static int UPDATE_SCORE = 0;
	final static int DEATH = 1;
	final static int LOSE = 2;
	
	View pausaButton;
	View PauseMenu;
	View WinDialog;
	View LoseDialog;
	
	MediaPlayer MainMusic;
	ImageView mte;
	
	RelativeLayout Rel_main_game;
	Button btn11,pbtn;
	//RelativeLayout Rel_main_game1;
	GamePanel game_panel;
	TextView txt;
	
	int get_coins=0;
int score=0;
int hscore;

	
	///final Handler handler = new Handler(){
		/*@Override
		public void handleMessage(Message msg) {
			if (msg.what==UPDATE_SCORE){
				
				i_get_coin();
			}
			if (msg.what==DEATH){
				postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Message msg = handler.obtainMessage();
					    msg.what = LOSE;
						handler.sendMessage(msg);
						
					}
				}, 3000);
			}
			if (msg.what==LOSE){
				i_lose();
			}
			
			super.handleMessage(msg);
		}
	};
	*/
	final Handler handler=new Handler(){
		public void handleMessage(Message msg){
	if (msg.what==UPDATE_SCORE){
				
				i_get_coin();}
	if (msg.what==DEATH){
		postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
			    msg.what = LOSE;
				handler.sendMessage(msg);
				
			}
		}, 3000);
	}
	if (msg.what==LOSE){
		i_lose();
	}
			super.handleMessage(msg);
		}
		
		
		
	};
	OnClickListener Continue_list = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			PauseMenu.setVisibility(View.GONE);
			pausaButton.setVisibility(View.VISIBLE);
			game_panel.Pause_game=false;
		}
	};
	
	OnClickListener To_Main_Menu_list = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			game_panel.thread.setRunning(false);
			Game.this.finish();
			
		}
	};
	
	
	OnClickListener Pausa_click =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			pausaButton.setVisibility(View.GONE);
			PauseMenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;
			
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		FullScreencall();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);
		//Rel_main_game1 = (RelativeLayout) findViewById(R.id.main_game_rl);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		final int heightS = dm.heightPixels;
		final int widthS = dm.widthPixels;
		game_panel = new GamePanel(getApplicationContext(), this,widthS, heightS);
		Rel_main_game.addView(game_panel);
		
		
		RelativeLayout RR = new RelativeLayout(this);
		RR.setBackgroundResource(R.drawable.btn);
		RR.setGravity(Gravity.CENTER);
		Rel_main_game.addView(RR,800,150);
		RR.setX(0);
		txt= new TextView(this);
		
		 Typeface Custom = Typeface.createFromAsset(getAssets(), "font.ttf");
		 txt.setTypeface(Custom);
		 txt.setTextColor(Color.YELLOW);
		
		 SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
	
		
			
			
		txt.setText("Score: " + score );
		
		RR.addView(txt);
		// txt1.setTypeface(Custom);
		 //txt1.setTextColor(Color.YELLOW);
		//txt1.setText("High Score: "+score);
		//RR.addView(txt1);
		
		LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
		pausaButton = myInflater.inflate(R.layout.pause, null, false);
		pausaButton.setX(widthS-250);
		pausaButton.setY(0);
		Rel_main_game.addView(pausaButton);
		ImageView pauseImage = (ImageView) pausaButton.findViewById(R.id.imCont);
		pausaButton.setOnTouchListener(new TochButton(pauseImage));
		pausaButton.setOnClickListener(Pausa_click);
		pausaButton.getLayoutParams().height=250;
		pausaButton.getLayoutParams().width=250;
		
		PauseMenu= myInflater.inflate(R.layout.pause_menu, null, false);
		Rel_main_game.addView(PauseMenu);
		PauseMenu.setVisibility(View.GONE);
	mte=(ImageView)findViewById(R.id.imageView1);
		ImageView Cont = (ImageView)PauseMenu.findViewById(R.id.imCont1);
		ImageView MainMenuTo = (ImageView)PauseMenu.findViewById(R.id.toMain);
		 Cont.setOnTouchListener(new TochButton(Cont));
		Cont.setOnClickListener(Continue_list);
		MainMenuTo.setOnTouchListener(new TochButton(MainMenuTo));
		MainMenuTo.setOnClickListener(To_Main_Menu_list);
	
		WinDialog= myInflater.inflate(R.layout.win, null, false);
		Rel_main_game.addView(WinDialog);
ImageView Win_to_main = (ImageView) WinDialog.findViewById(R.id.imageViel2);
	Win_to_main.setOnTouchListener(new TochButton(Win_to_main));
Win_to_main.setOnClickListener(To_Main_Menu_list);
WinDialog.setVisibility(View.GONE);
		
LoseDialog= myInflater.inflate(R.layout.lose, null, false);
Rel_main_game.addView(LoseDialog);
ImageView Lose_to_main = (ImageView) LoseDialog.findViewById(R.id.imageViel2);
Lose_to_main.setOnTouchListener(new TochButton(Lose_to_main));
Lose_to_main.setOnClickListener(To_Main_Menu_list);
btn11=(Button)findViewById(R.id.button1m);
//pbtn=(Button)findViewById(R.id.button1m);
mte.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MainMusic.setVolume(0,0);
		
	}
});

btn11.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		sharingIntent.setType("text/plain");
		String shareBody = "I scored "+score+" points!How much can you score?Install the game Space Prowler from the Play Store now!";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
		
	}
});

LoseDialog.setVisibility(View.GONE);
		
		MainMusic = MediaPlayer.create(Game.this, R.raw.music);
	MainMusic.setVolume(0.3f, 0.3f);
	MainMusic.start();

	
	
	}
	@Override
	public void onBackPressed() {
		pausaButton.setVisibility(View.GONE);
		PauseMenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;
	}
	@Override
	protected void onStop() {
		if (MainMusic.isPlaying())
			MainMusic.stop();
		super.onStop();
	
	}
	protected void s() {	SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
	hscore = prefs.getInt("key", hscore);
	Editor editor = prefs.edit();
	if(score>hscore){
		editor.putInt("key", score);

		editor.commit();}
		//Toast.makeText(Game.this, "HighScore "+hscore, 7000).show();
	
		
	}
	/*
	@Override
	public void onBackPressed() {
		pausaButton.setVisibility(View.GONE);
		PauseMenu.setVisibility(View.VISIBLE);
		game_panel.Pause_game=true;
	}
	
	@Override
	protected void onStop() {
		if (MainMusic.isPlaying())
			MainMusic.stop();
		super.onStop();
	}

	protected void i_lose() {
		// TODO Auto-generated method stub
		
		if (MainMusic.isPlaying())
			MainMusic.stop();
		MainMusic = MediaPlayer.create(Game.this, R.raw.lose);
		MainMusic.start();
		game_panel.Pause_game=true;
		LoseDialog.setVisibility(View.VISIBLE);
	}

	protected void i_get_coin() {
		get_coins++;
		score+=200;
		txt.setText("Score: " + score);
		MediaPlayer mp = MediaPlayer.create(Game.this, R.raw.coin);
		mp.start();
		if (get_coins==20){
			i_win();
		}
		
	}*/

	protected void i_lose() {
		// TODO Auto-generated method stub
		if (MainMusic.isPlaying())
			MainMusic.stop();
		MainMusic = MediaPlayer.create(Game.this, R.raw.lose);
		MainMusic.start();
		game_panel.Pause_game=true;
		LoseDialog.setVisibility(View.VISIBLE);
	}

	protected void i_get_coin() {
		// TODO Auto-generated method stub
		get_coins++;
		score+=200;
		 s();
		

		
		txt.setText("Score: " + score+" HighScore: " + hscore);
		
		MediaPlayer mp = MediaPlayer.create(Game.this, R.raw.coin);
		mp.start();
		
			
		
		if (get_coins==50){
			i_win();
		}
	}

	private void i_win() {
		// TODO Auto-generated method stub
		if (MainMusic.isPlaying())
			MainMusic.stop();
		MainMusic = MediaPlayer.create(Game.this, R.raw.win);
		MainMusic.start();
		game_panel.Pause_game=true;
		WinDialog.setVisibility(View.VISIBLE);
	}
	  public void FullScreencall() {
		    if(Build.VERSION.SDK_INT < 19){ //19 or above api
		        View v = this.getWindow().getDecorView();
		        v.setSystemUiVisibility(View.GONE);
		    } else {
		            //for lower api versions.
		        View decorView = getWindow().getDecorView(); 
		        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		        decorView.setSystemUiVisibility(uiOptions);
		    }
		}
	/*private void i_win() {
		// TODO Auto-generated method stub
		if (MainMusic.isPlaying())
			MainMusic.stop();
		MainMusic = MediaPlayer.create(Game.this, R.raw.win);
		MainMusic.start();
		
		
		game_panel.Pause_game=true;
		WinDialog.setVisibility(View.VISIBLE);
		
	}*/
	
}