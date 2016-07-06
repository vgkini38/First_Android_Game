package com.example.spaceprowler;

import com.example.spaceprowler.Game;
import com.example.spaceprowler.MainMenu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.TaskDescription;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {

	RelativeLayout Btn;
	ImageView ImageButton,qm;
	TextView txt,txtv1;
	MediaPlayer MainMenuMusic;
	View LoseDialog;
	Button rtry;

	 @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@SuppressLint("NewApi")
	protected void p(){if(Build.VERSION.SDK_INT > 19){
		 TaskDescription tDesc = new TaskDescription(null,null,Color.YELLOW);
	this.setTaskDescription(tDesc);}}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	
		FullScreencall();
		Toast.makeText(MainMenu.this, "Developed by Vishwas G Kini & Aman Bakshi", Toast.LENGTH_LONG).show();
		p();
		int color = 0xff0000;
		NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainMenu.this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Space Prowler")
                        .setContentText("Thank You for playing! Hope you enjoyed the game!")
                        .setColor(color)
	     .setAutoCancel(true); // clear notification after click
	     Intent intent = new Intent(this, MainMenu.class);
	     PendingIntent pi = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
	     mBuilder.setContentIntent(pi);
	     NotificationManager mNotificationManager =
	                         (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	     mNotificationManager.notify(0, mBuilder.build());
	  
                        //.setAutoCancel(true);
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/
	
	Btn=(RelativeLayout)findViewById(R.id.btn_start);
	
	ImageButton=(ImageView)findViewById(R.id.img_btn);
	txt=(TextView)findViewById(R.id.text_start);
	txtv1=(TextView)findViewById(R.id.textView1);
	Typeface Custom=Typeface.createFromAsset(getAssets(),"font.ttf");
	txt.setTypeface(Custom);
	qm=(ImageView)findViewById(R.id.imageView1);
	Btn.setOnTouchListener(new TochButton(ImageButton));

	qm.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		AlertDialog.Builder builder=new AlertDialog.Builder(MainMenu.this);
		builder.setMessage("Just touch the screen to move the spaceship upwards and release it to move it downwards.Dont forget to collect the coins which gives 200 points for each and yeah beware of the barriers both below and above!");
		AlertDialog dialog=builder.create();
		dialog.show();
			
		}
	});
	txtv1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vgkini38"));
			startActivity(browserIntent);	
		}
	});

	//rtry=(Button)findViewById(R.id.rtry);
	
	Btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent=new Intent(MainMenu.this,Game.class);
			startActivity(myIntent);
			
		}
	});
	
	
	
	}


	//build your notification here.
	                
	   @Override
	    protected void onStart() {
	        MainMenuMusic = MediaPlayer.create(MainMenu.this, R.raw.main);
	    	MainMenuMusic.setVolume(0.3f, 0.3f);
	    	MainMenuMusic.start();
	    	super.onStart();
	    }
	    
	    @Override
	    protected void onStop() {
	    	if (MainMenuMusic.isPlaying())
	    		MainMenuMusic.stop();
	    	super.onStop();
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
}
