package com.example.topnewsheadlines;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //a list to store all the prs users name
    public ArrayList<NewsInfo> newsInfoArrayList;
    //the recyclerview
    RecyclerView recyclerView;
    //adapter
    private NewsAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkConfiguration networkConfiguration = new NetworkConfiguration(this);

        if (!networkConfiguration.isConnected()) {
            setContentView(R.layout.main_no_internet);
            getSupportActionBar().hide();
            alertMessage();
            return;
        }

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        progressBar = findViewById(R.id.idProgressbarLoadingMainActivity);

        newsInfoArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.idRecycleViewAllNewsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setNestedScrollingEnabled(false);

        adapter = new NewsAdapter(newsInfoArrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewsInfo newsInfo = newsInfoArrayList.get(position);

                Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                intent.putExtra("image", newsInfo.getImage());
                intent.putExtra("headline", newsInfo.getHeadline());
                intent.putExtra("description", newsInfo.getDescription());
                intent.putExtra("source", newsInfo.getSource());
                intent.putExtra("publishedat", newsInfo.getPublishedAt());
                startActivity(intent);
            }

            @Override
            public void onItemClick1(int position) {
                TextView textView = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idTextViewSourceAllNews);

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_animation);
                animation.reset();

                textView.clearAnimation();
                textView.startAnimation(animation);

                NewsInfo newsInfo = newsInfoArrayList.get(position);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(newsInfo.getSource()));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Browser Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getNews();
    }

    private void getNews() {
        Log.d("TAG", "getNews: 111");
        String requestUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=d3c2786fbac64fb0aa0e4b9ef149f1d6";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);


                                String temp = jsonObject.getString("publishedAt");
                                String[] split = temp.split("T");
                                String str = null;
                                for (int i = 0; i < split.length; i++) {
                                    if (i == 0) {
                                        str = split[0];
                                    } else {
                                        str = str + "+" + split[i];
                                    }

                                }

                                String publishedAt = split[0] + ", " + split[1];


                                Log.d("TAG", "onResponse: " + Arrays.toString(split));


                                newsInfoArrayList.add(new NewsInfo(
                                        jsonObject.getString("urlToImage"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("description"),
                                        jsonObject.getString("url"),
                                        publishedAt
                                ));

                            }

                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.GONE);

                            recyclerView.getLayoutManager().scrollToPosition(5);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void alertMessage() {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Please connect with to working internet connection");

        // Set Alert Title
        builder.setTitle("Network Error!");

        // Set Cancelable false// Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.

        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Ok",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                finish();
                                startActivity(getIntent());
                            }
                        });


        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

}