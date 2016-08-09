package com.cityconnect.webservice;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Sandeep on 11/22/15.
 */
public class ApiHelper extends BaseHttpHelper{

    public ApiHelper(String Type, String ApiName, List<NameValuePair> inputParameter, ResponseHelper pResponseHelper) {
        super(Type,ApiName,inputParameter, pResponseHelper);
    }

    @Override
    public void invokeAPI() {

        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
