package com.example.lennont.gankclientdemo.main;

import android.support.v4.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lennont.gankclientdemo.R;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LennonT on 2016/7/7.
 */
public abstract class BaseLoadingFragment extends Fragment {

    private ProgressActivity mProgressActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressActivity = (ProgressActivity) inflater.inflate(R.layout.base_loading_layout, container, false);
        mProgressActivity.addView(onCreateContentView(inflater, mProgressActivity, savedInstanceState));
        return mProgressActivity;
    }

    abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void showLoading() {
        mProgressActivity.showLoading();
    }

    protected void showContent(){
        mProgressActivity.showContent();
    }

    protected void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, List<Integer> skipIds) {
        if(null == skipIds)skipIds = new ArrayList<>();
        mProgressActivity.showEmpty(emptyImageDrawable, emptyTextTitle, emptyTextContent,skipIds);
    }

    protected void showError(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, String errorButtonText, View.OnClickListener onClickListener) {
        mProgressActivity.showError(emptyImageDrawable, emptyTextTitle, emptyTextContent, errorButtonText, onClickListener);
    }

}
