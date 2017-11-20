package com.androidbash.androidbashphpmysql;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddToDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_db);
    }

    public void DoPost(View view){
        String url = "http://csis.svsu.edu/~rcthoma3/cs403/movie_post.php";
        String movie_name = ((EditText) findViewById(R.id.editText)).getText().toString();
        String movie_genre = ((EditText) findViewById(R.id.editText2)).getText().toString();
        PostClass postObj = new PostClass();
        postObj.execute(url, movie_name, movie_genre);
    }
    public class PostClass extends AsyncTask<String, String, String> {
        String urlString;
        String movie_name;
        String movie_genre;

        @Override
        protected String doInBackground(String... params) {
            urlString = params[0];
            movie_name = params[1];
            movie_genre = params[2];

            OutputStream out = null;
            try {
                String data = URLEncoder.encode("movie_name", "UTF-8")
                        + "=" + URLEncoder.encode(movie_name, "UTF-8");
                data += "&" + URLEncoder.encode("movie_genre", "UTF-8")
                        + "=" + URLEncoder.encode(movie_genre, "UTF-8");

                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                out = new BufferedOutputStream(urlConnection.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                writer.write(data);

                writer.flush();

                writer.close();

                out.close();

                urlConnection.connect();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(String result)
        {
            Context context = getApplicationContext();
            CharSequence text = movie_name;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
