package mygdx.game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useCompass = false;
        cfg.useAccelerometer = false;

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.GRAY);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams layoutParamsView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linLayoutParam.gravity = Gravity.END;
        setContentView(linearLayout, linLayoutParam);

        Game game = new MyCmtGame();
        Button btn = new Button(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(mygdx.game.AndroidLauncher.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn.setBackgroundDrawable(Drawable.createFromPath("res/drawable/buttons.xml"));
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setText("<<BACK<<");
        linearLayout.addView(btn, layoutParamsView);

        View gameView = initializeForView(game, cfg);
        linearLayout.addView(gameView);

    }


}


