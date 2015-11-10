package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.gradle.builditbigger.sync.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Nicolás Carrasco on 10/11/2015.
 */
public class AsyncTaskTest extends InstrumentationTestCase {

    private CountDownLatch signal = null;
    private String mResult = null;
    private Context mContext = null;

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
        mContext = getInstrumentation().getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJoke() throws Throwable{
        final EndpointsAsyncTask asyncTask = new EndpointsAsyncTask();
        asyncTask.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                mResult = result;
                signal.countDown();
            }
        });
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                asyncTask.execute(mContext);
            }
        });
        signal.await();

        assertFalse(TextUtils.isEmpty(mResult));
    }
}
