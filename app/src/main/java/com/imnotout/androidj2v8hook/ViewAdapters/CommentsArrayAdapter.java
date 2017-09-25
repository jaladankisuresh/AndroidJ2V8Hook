package com.imnotout.androidj2v8hook.ViewAdapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.imnotout.androidj2v8hook.BR;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.databinding.ListItemCommentLayoutBinding;

import java.util.List;

public class CommentsArrayAdapter
        extends RecyclerView.Adapter<CommentsArrayAdapter.CommentViewHolder> {

    private List<AppBaseModels.CommentBase> collection;

    public CommentsArrayAdapter(List<AppBaseModels.CommentBase> collection) {
        this.collection = collection;
    }

    public static CommentsArrayAdapter newInstance(List<AppBaseModels.CommentBase> collection) {
        return new CommentsArrayAdapter(collection);
    }

    @Override
    public CommentsArrayAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemCommentLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_comment_layout, parent, false);

        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentsArrayAdapter.CommentViewHolder holder, int position) {
        holder.bind(collection.get(position));
    }

    @Override
    public int getItemCount() {
        return collection == null ? 0 : collection.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private final ListItemCommentLayoutBinding binding;

        public CommentViewHolder(ListItemCommentLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String comment) {
            binding.setVariable(BR.model, comment);
            binding.executePendingBindings();
        }
    }
}
