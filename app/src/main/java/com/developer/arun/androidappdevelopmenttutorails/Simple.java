package com.developer.arun.androidappdevelopmenttutorails;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.style.FadingCircle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Simple extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private ArrayList<ExampleItem> mexamplelist;
    private RequestQueue requestQueue;
    private android.support.v7.widget.Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        progressBar=(ProgressBar) findViewById(R.id.spin);
        FadingCircle threeBounce=new FadingCircle();
        progressBar.setIndeterminateDrawable(threeBounce);
        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mexamplelist = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        AnimationDrawable animationDrawable=(AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        YoYo.with(Techniques.Wave).playOn(recyclerView);
        parseJSON();

        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        if (connected==false)
        {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void parseJSON() {
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        if (connected==false)
        {
            progressBar.setVisibility(View.INVISIBLE);
        }

        String url1 ="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=PLrnPJCHvNZuCaFbD-1TsnRaO39huczYcA&key=AIzaSyAygs57QVux81pihKTo5dmpPb5hSId3g5I";
        final String url2 = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&pageToken=CDIQAA&playlistId=PLrnPJCHvNZuCaFbD-1TsnRaO39huczYcA&key=AIzaSyAygs57QVux81pihKTo5dmpPb5hSId3g5I";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject items=jsonArray.getJSONObject(i);
                        JSONObject snippet=items.getJSONObject("snippet");
                        String video_title=snippet.getString("title");
                        JSONObject thumbnail=snippet.getJSONObject("thumbnails").getJSONObject("medium");
                        String url=thumbnail.getString("url");
                        JSONObject resourceid=snippet.getJSONObject("resourceId");
                        String video_id=resourceid.getString("videoId");

                        mexamplelist.add(new ExampleItem(video_id,video_title,url));
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONArray jsonArray = response.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject items=jsonArray.getJSONObject(i);
                                JSONObject snippet=items.getJSONObject("snippet");
                                String video_title=snippet.getString("title");
                                JSONObject thumbnail=snippet.getJSONObject("thumbnails").getJSONObject("medium");
                                String url=thumbnail.getString("url");
                                JSONObject resourceid=snippet.getJSONObject("resourceId");
                                String video_id=resourceid.getString("videoId");

                                mexamplelist.add(new ExampleItem(video_id,video_title,url));
                            }
                            adapter=new ExampleAdapter(Simple.this,mexamplelist);
                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);

        final MenuItem searchitem=menu.findItem(R.id.action_search);
        final SearchView searchView=(SearchView) searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (adapter!=null)
                {
                    adapter.getFilter().filter(s);

                }

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refresh:
            {
                if(adapter==null)
                {
                    parseJSON();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No need to reload.",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
