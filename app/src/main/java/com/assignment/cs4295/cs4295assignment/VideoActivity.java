package com.assignment.cs4295.cs4295assignment;

import android.content.Intent;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.VideoView;


public class VideoActivity extends AppCompatActivity {
    VideoView video;
    TextView desc;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video=(VideoView)findViewById(R.id.video);
        desc=(TextView)findViewById(R.id.desc);
        title=(TextView)findViewById(R.id.title);

        //get the information from the previous activity
        Intent intent = getIntent();
        String eventTitle = intent.getStringExtra("title_name");

        String description="The 20th Annual \"Red Wine & Chocolate\" event is the weekend"
                +" of Valentine's Day with the \"Secret Crush\" event kicking things off Friday night, "
                +"the 13th. Wines from all over the Yakima Valley will be featured and each winery "
                +"will offer different activities throughout the weekend. Jordan Wilkerson went to one"
                +"of the participating tasting rooms to get the details.";

        Uri u= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        video.setVideoURI(u);
        desc.setText(description);
        title.setText(eventTitle);
        MediaController c =new MediaController(this);
        c.setAnchorView(video);
        video.setMediaController(c);
        video.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}