package ru.thever4.snake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GameView canvasView;
    private Button[] buttons;
    private GameEngine engine;
    private Button aboutUsButton;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView = findViewById(R.id.viewCanvas);
        engine = new GameEngine(getApplicationContext(), canvasView);
        buttons = new Button[] { findViewById(R.id.buttonDown), findViewById(R.id.buttonLeft), findViewById(R.id.buttonUp), findViewById(R.id.buttonRight), findViewById(R.id.buttonReload) };
        aboutUsButton = findViewById(R.id.learnMoreButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        engine.initGame();
        for(Button button : buttons)
            button.setOnClickListener(this);
        canvasView.setGameData(engine);
        aboutUsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRight:
                engine.setDirection(GameEngine.Direction.right);
                break;
            case R.id.buttonLeft:
                engine.setDirection(GameEngine.Direction.left);
                break;
            case R.id.buttonUp:
                engine.setDirection(GameEngine.Direction.up);
                break;
            case R.id.buttonDown:
                engine.setDirection(GameEngine.Direction.down);
                break;
            case R.id.buttonReload:
                engine.reloadGame();
                break;
            case R.id.learnMoreButton:
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
                break;
        }
    }
}