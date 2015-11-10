package com.udacity.gradle.builditbigger.sync;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nicolascarrasco.com.backend.myApi.MyApi;
import com.nicolascarrasco.www.jokeactivity.JokeActivity;

import java.io.IOException;

/**
 * Simple AsyncTask used to fetch jokes from the Endpoint
 */
public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private EndpointsAsyncTaskListener mListener = null;

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://build-it-bigger-backend.appspot.com/_ah/api");
            //Use the code below to test code locally instead
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/") // 10.0.2.2 is localhost's IP address in Android emulator
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(this.mListener != null){
            this.mListener.onComplete(result);
        }
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, result);
        context.startActivity(intent);
    }

    public void setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
    }

    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String result);
    }
}
