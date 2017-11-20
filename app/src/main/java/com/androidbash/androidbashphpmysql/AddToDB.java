package com.androidbash.androidbashphpmysql;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddToDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_db);
    }

    public void DoPost(View view) {
        String movie_name = ((EditText) findViewById(R.id.editText)).getText().toString();
        String movie_genre = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String image_url = ((EditText) findViewById(R.id.editText3)).getText().toString();

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            boolean RequestHappened = true;

            @Override
            protected Void doInBackground(String... params) {

                String moviename = params[0];
                String moviegenre = params[1];
                String imageurl = params[2];

                OkHttpClient client = new OkHttpClient();

                RequestBody body = new FormBody.Builder()
                        .add("movie_name", moviename)
                        .add("movie_genre", moviegenre)
                        .add("image_url", imageurl).build();

                Request request = new Request.Builder()
                        .url("http://csis.svsu.edu/~rcthoma3/cs403/movie_post.php")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    RequestHappened = false;
                }
                return null;
            }
        };

        asyncTask.execute(movie_name, movie_genre, image_url);

        Context context = getApplicationContext();
        CharSequence text = "Data sent!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}