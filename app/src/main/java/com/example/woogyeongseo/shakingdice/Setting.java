package com.example.woogyeongseo.shakingdice;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Setting extends AppCompatActivity {


    MediaPlayer defaultSong;
    MediaPlayer popSong;
    MediaPlayer pop2Song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        defaultSong = MediaPlayer.create(Setting.this, R.raw.present );
        popSong = MediaPlayer.create(Setting.this,R.raw.pop );
        pop2Song = MediaPlayer.create(Setting.this, R.raw.pop2 );
    }


    public void playMusic(View view) {
        switch(view.getId()) {
            case R.id.defaultSongBtn:
                if(popSong.isPlaying()){
                    defaultSong.stop();
                } else if (pop2Song.isPlaying()){
                    pop2Song.stop();
                }
                defaultSong.start();
                break;
            case R.id.popSongBtn:
                if(defaultSong.isPlaying()){
                    defaultSong.stop();
                } else if (pop2Song.isPlaying()){
                    pop2Song.stop();
                }
                popSong.start();
                break;
            case R.id.pop2SongBtn:
                if(defaultSong.isPlaying()){
                    defaultSong.stop();
                } else if (popSong.isPlaying()){
                    popSong.stop();
                }
                pop2Song.start();
                break;
        }

    }


    public void pauseAllMusic (MediaPlayer song, MediaPlayer song2, MediaPlayer song3){
        song.release();
        song2.release();
        song3.release();
    }



    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Setting","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Setting","on stop called");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pauseAllMusic(defaultSong,popSong,pop2Song);
        Log.i("Setting","on destroy called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getMenuInflater().inflate(R.menu.menu_main,menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_ranking) {
            Intent intent = new Intent(this, Ranking.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
