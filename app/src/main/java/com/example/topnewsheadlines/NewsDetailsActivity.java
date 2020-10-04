package com.example.topnewsheadlines;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    public String imageLink;
    public String headline;
    public String description;
    public String publishedat;
    public String source;
    private ImageView imageViewImage;
    private TextView textViewHeadline;
    private TextView textViewDescription;
    private TextView textViewPublishedAt;
    private TextView textViewSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        getSupportActionBar().hide();

        imageLink = getIntent().getStringExtra("image");
        headline = getIntent().getStringExtra("headline");
        description = getIntent().getStringExtra("description");
        publishedat = getIntent().getStringExtra("publishedat");
        source = getIntent().getStringExtra("source");


        imageViewImage = findViewById(R.id.idImageViewImageNewsDetailsActivity);
        textViewHeadline = findViewById(R.id.idTextViewHeadlineNewsDetailsActivity);
        textViewDescription = findViewById(R.id.idTextViewDescriptionNewsDetailsActivity);
        textViewPublishedAt = findViewById(R.id.idTextViewPublishedAtNewsDetailsActivity);
        textViewSource = findViewById(R.id.idTextViewSourceNewsDetailsActivity);

        textViewSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_animation);
                animation.reset();

                textViewSource.clearAnimation();
                textViewSource.startAnimation(animation);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(source));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Browser Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        textViewHeadline.setText(headline);
        textViewDescription.setText(description);
        textViewPublishedAt.setText(publishedat);

        Picasso.get().load(imageLink).into(imageViewImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}