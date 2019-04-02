package com.example.woogyeongseo.shakingdice;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Login extends AppCompatActivity {

    private ImageView userImage;
    private EditText userName;

    String img_str;
    String username;
    SharedPreferences Preferences;
    public static final int SELECT_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Preferences = getSharedPreferences("data", MODE_PRIVATE);

        userImage = (ImageView) findViewById(R.id.userImage);
        userName = (EditText) findViewById(R.id.userName);



        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use implicit intent to get image content
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//MIME for image
                //startActivity(intent)
                startActivityForResult(intent, SELECT_IMAGE);

            }
        }); // goes to gallery

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence string, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("done...");
                username = userName.getText().toString();
                System.out.println(username);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            Uri dataUri = intent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dataUri);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                userImage.setImageDrawable(drawable);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                img_str = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }


        }
    }

    public void startGame(View view) {
        System.out.println("done..." + username);
        Preferences.edit()
                .putString("userName", username)
                .putString("userImage", img_str)
                .commit();
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }


    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Login","on start called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Login","on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Login","on destroy called");}



}

