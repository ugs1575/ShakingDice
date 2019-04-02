package com.example.woogyeongseo.shakingdice;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }


    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.playBtn:
                intent = new Intent(this, Login.class);
                break;
            case R.id.howBtn:
                intent = new Intent(this, HowToPlay.class);
                break;
            case R.id.settingBtn:
                intent = new Intent(this, Setting.class);
                break;
            case R.id.rankingBtn:
                intent = new Intent(this, Ranking.class);
                break;
        }

        startActivity(intent);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Main","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Main","on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main","on destroy called");}


}
