package com.cityconnect.webservice;
import org.json.JSONObject;

public class BaseResponse {

    private String mMessage;
    private boolean mStatus;
    private int mAPIType;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean ismStatus() {
        return mStatus;
    }

    public void setmStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public int getmAPIType() {
        return mAPIType;
    }

    public void setmAPIType(int mAPIType) {
        this.mAPIType = mAPIType;
    }

    public static BaseResponse fromJson(String pResult) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            JSONObject obj= new JSONObject(pResult);
            baseResponse.setmStatus(obj.getBoolean("valid"));
            baseResponse.setmMessage(obj.getString("msg"));

        }catch (Exception e){

        }
        return baseResponse;

    }
}


