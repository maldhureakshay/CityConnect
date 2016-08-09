package com.cityconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityconnect.R;

/**
 * Created by MY PC on 31-07-2016.
 */
public class IssuesListHolder  extends RecyclerView.ViewHolder  {

    ImageView issueImage;
    TextView issueTitle,issueUploadedDate,issueAddress,issueUploadedBy;
    ImageButton shareButton;
    public IssuesListHolder(View itemView) {
        super(itemView);
        issueImage = (ImageView)itemView.findViewById(R.id.issueImageView);
        issueUploadedDate = (TextView)itemView.findViewById(R.id.issueUploadedDate);
        issueAddress = (TextView)itemView.findViewById(R.id.issueAddress);
        issueUploadedBy = (TextView)itemView.findViewById(R.id.issueUploadedBy);
        shareButton = (ImageButton)itemView.findViewById(R.id.shareButton);
    }
}
