package com.example.relief.network.http;

import android.os.AsyncTask;
import android.util.Log;
import com.example.relief.callback.OnTaskCompleted;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpAsyncTaskPost extends AsyncTask<String, Void, String> {

    private static final String TAG = HttpAsyncTaskPost.class.getSimpleName();
    private final OnTaskCompleted listener;
    private final int requestId;

    public HttpAsyncTaskPost(OnTaskCompleted listener, int requestId) {
        this.listener = listener;
        this.requestId = requestId;
    }

    public static String post(String urlString, String data) {
        String result = "";
        try {
            Log.d(TAG, "Sending data[" + data + "]");
            Log.d(TAG, "Sending url[" + urlString + "]");
            // create HttpPost
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = null;
            try {
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Connection", "keep-alive");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    // receive response as inputStream
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    // convert inputstream to string
                    result = convertInputStreamToString(inputStream);
                } else {
                    result = "Did not work!";
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        inputStream.close();
        return result.toString();
    }

    // doInBackground execute tasks when asynctask is run
    @Override
    protected String doInBackground(String... parameters) {
        return post(parameters[0], parameters[1]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String response) {
        Log.d(TAG, response);
        listener.onTaskCompleted(response, requestId);
    }
}
