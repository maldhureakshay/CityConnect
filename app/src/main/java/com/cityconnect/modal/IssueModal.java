package com.cityconnect.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MY PC on 31-07-2016.
 */
public class IssueModal {


    @SerializedName("issue_id")
    String mIssueId;

    @SerializedName("issue_name")
    String mIssueName;

    @SerializedName("issue_image")
    String mIssueImage;

    @SerializedName("issue_description")
    String mIssueDesc;

    @SerializedName("issue_category")
    String mIssueCategory;

    @SerializedName("issue_category_icon")
    String mIssueCategoryIcon;

    public String getmIssueId() {
        return mIssueId;
    }

    public void setmIssueId(String mIssueId) {
        this.mIssueId = mIssueId;
    }

    public String getmIssueName() {
        return mIssueName;
    }

    public void setmIssueName(String mIssueName) {
        this.mIssueName = mIssueName;
    }

    public String getmIssueImage() {
        return mIssueImage;
    }

    public void setmIssueImage(String mIssueImage) {
        this.mIssueImage = mIssueImage;
    }

    public String getmIssueDesc() {
        return mIssueDesc;
    }

    public void setmIssueDesc(String mIssueDesc) {
        this.mIssueDesc = mIssueDesc;
    }

    public String getmIssueCategory() {
        return mIssueCategory;
    }

    public void setmIssueCategory(String mIssueCategory) {
        this.mIssueCategory = mIssueCategory;
    }

    public String getmIssueCategoryIcon() {
        return mIssueCategoryIcon;
    }

    public void setmIssueCategoryIcon(String mIssueCategoryIcon) {
        this.mIssueCategoryIcon = mIssueCategoryIcon;
    }


    @Override
    public String toString() {
        return "IssueModal{" +
                "mIssueId='" + mIssueId + '\'' +
                ", mIssueName='" + mIssueName + '\'' +
                ", mIssueImage='" + mIssueImage + '\'' +
                ", mIssueDesc='" + mIssueDesc + '\'' +
                ", mIssueCategory='" + mIssueCategory + '\'' +
                ", mIssueCategoryIcon='" + mIssueCategoryIcon + '\'' +
                '}';
    }
}
