package itute.phucduong.engvocabularylearning;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("About");
        //String title = actionBar.getTitle().toString();  //lấy tiêu đề
        //actionBar.hide();  //ẩn ActionBar

    }
}
