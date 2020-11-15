package ru.thever4.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button[] activityButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        activityButtons = new Button[] {findViewById(R.id.buttonToIITPage), findViewById(R.id.buttonToGitHub)};
        for(Button b : activityButtons)
            b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonToIITPage:
                openLinkInBrowser("https://vk.com/ab_csu_iit");
                break;
            case R.id.buttonToGitHub:
                openLinkInBrowser("https://github.com/TheVer4edu/DroidSnake");
                break;
        }
    }

    private void openLinkInBrowser(String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}