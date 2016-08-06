package com.example.rockstar.mplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private ImageView albumart;
    private ImageView nowplaying;
    private TextView songstart;
    private SeekBar seekBar;
    private MediaPlayer mediaplayer;
    private long songStartTime;
    private ImageButton play;
    private int forwardtime;
    private ImageButton forwardbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
        seekUpdation();
        forward();
    }

    private void initHandler() {
        handler.postDelayed(updateUI, 1000);
    }

    private void initView() {
        forwardtime = 5*1000;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        play = (ImageButton) findViewById(R.id.play);
        mediaplayer = MediaPlayer.create(this, R.raw.pacha);
        songstart = (TextView) findViewById(R.id.songstart);
        nowplaying = (ImageView) findViewById(R.id.nowplaying);
        nowplaying.setImageDrawable(getResources().getDrawable(R.drawable.nowplaying));
        albumart = (ImageView) findViewById(R.id.albumart);
        albumart.setImageDrawable(getResources().getDrawable(R.drawable.albumart));
        forwardbutton = (ImageButton) findViewById(R.id.forward);
        forwardbutton.setImageDrawable(getResources().getDrawable(R.drawable.forward));
    }
    public void seekUpdation(){
        seekBar.setProgress(mediaplayer.getCurrentPosition());
        handler.postDelayed(updateUI,10000);
    }




    public void forward(){

        forwardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer!=null) {
                    int currentPosition = mediaplayer.getCurrentPosition();
                    mediaplayer.seekTo(currentPosition + forwardtime);
                }
                else{
                    mediaplayer.seekTo(mediaplayer.getDuration());
                }
            }


        });
        }



    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
            forward();
            double seekPercentage = 100 * mediaplayer.getCurrentPosition() / mediaplayer.getDuration();
            seekBar.setProgress((int) seekPercentage);
            long seconds = (System.currentTimeMillis() - songStartTime) / 1000;
            songstart.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
            handler.postDelayed(this, 1000);
        }

    };


    private void initListeners() {
        play.setImageResource(R.drawable.play);
        play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaplayer.isPlaying()) {
                            handler.removeCallbacks(updateUI);
                            mediaplayer.pause();
                            play.setImageResource(R.drawable.play);

                        } else {
                            initHandler();
                            mediaplayer.start();
                            songStartTime = System.currentTimeMillis();
                            play.setImageResource(R.drawable.pause);
                            //play.setText("Pause");
                        }
                    }
                }
        );
    }
}

  /*  private void initView(){
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        play = (ImageButton) findViewById(R.id.play);
        //mediaplayer = MediaPlayer.create();
        songstart = (TextView) findViewById(R.id.songstart);
        nowplaying = (ImageView) findViewById(R.id.nowplaying);
        nowplaying.setImageDrawable(getResources().getDrawable(R.drawable.nowplaying));
        albumart = (ImageView) findViewById(R.id.albumart);
        albumart.setImageDrawable(getResources().getDrawable(R.drawable.albumart));
    }*/