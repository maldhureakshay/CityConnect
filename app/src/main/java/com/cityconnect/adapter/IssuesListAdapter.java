package com.cityconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cityconnect.R;
import com.cityconnect.modal.IssueModal;
import com.cityconnect.ui.IssueDetailsActivity;

import java.util.List;

/**
 * Created by MY PC on 31-07-2016.
 */
public class IssuesListAdapter extends RecyclerView.Adapter<IssuesListHolder> {

    public Context context;
    List<IssueModal> itemList;

    public IssuesListAdapter(List<IssueModal> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public IssuesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_issue_layout,null);
        IssuesListHolder issuesListHolder=new IssuesListHolder(view);

        return issuesListHolder;
    }

    @Override
    public void onBindViewHolder(IssuesListHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IssueDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                txtIntent .setType("text/plain");
                txtIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, "Test");
                txtIntent .putExtra(android.content.Intent.EXTRA_TEXT, "Test");
                context.startActivity(Intent.createChooser(txtIntent ,"Share"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
