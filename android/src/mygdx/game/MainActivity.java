package mygdx.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        Button buttonStart = findViewById(R.id.start);
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                try {
                    Intent i = new Intent(MainActivity.this, mygdx.game.AndroidLauncher.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        });


        Button button = findViewById(R.id.settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                try {
                    Intent i = new Intent(MainActivity.this, Options.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        });


    }
}
