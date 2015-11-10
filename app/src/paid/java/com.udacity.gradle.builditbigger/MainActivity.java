package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nicolascarrasco.www.jokeactivity.JokeActivity;
import com.udacity.gradle.builditbigger.sync.EndpointsAsyncTask;


public class MainActivity extends ActionBarActivity {

    static final int JOKE_ACTIVITY_REQUEST = 100;
    Context mContext;
    ProgressBar mSpinner;
    Button mButton;
    TextView mTextView;
    EndpointsAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mButton = (Button) findViewById(R.id.joke_button);
        mTextView = (TextView) findViewById(R.id.instructions_text_view);
        mSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void launchJokeActivity(View view){
        mAsyncTask = new EndpointsAsyncTask();
        mAsyncTask.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                Intent intent = new Intent(mContext, JokeActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, result);
                startActivityForResult(intent, JOKE_ACTIVITY_REQUEST);
            }
        });
        Utility.swapVisibility(new View[]{
                mButton,
                mTextView,
                mSpinner});
        mAsyncTask.execute(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == JOKE_ACTIVITY_REQUEST) {
            Utility.swapVisibility(new View[]{
                    mButton,
                    mTextView,
                    mSpinner});
        }
    }
}