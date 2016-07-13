package com.example.lennont.gankclientdemo.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lennont.gankclientdemo.R;
import com.example.lennont.gankclientdemo.adapter.BenefitAdapter;
import com.example.lennont.gankclientdemo.bean.GoodsResult;
import com.example.lennont.gankclientdemo.bean.Image;
import com.example.lennont.gankclientdemo.network.GankCloudApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BenefitFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener {

    //region 属性

    private static final String ARG_PARAM1 = "param1";
    @Bind(R.id.benefit_recycler_view)
    RecyclerView benefitRecyclerView;
    @Bind(R.id.benefit_swipe_refresh)
    SwipeRefreshLayout benefitSwipeRefresh;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private BenefitAdapter mBenefitAdapter;

    private Realm mRealm;

    private ArrayList<Image> mBenefitImages;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private List<Image> mAllBenefitImage = new ArrayList<>();

    //endregion

    public BenefitFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BenefitFragment newInstance(String param1) {
        BenefitFragment fragment = new BenefitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        mBenefitAdapter = new BenefitAdapter(getActivity()) {
            @Override
            protected void onItemClick(View v, int adapterPosition) {
                //TODO 进入大图的单图查看模式
            }
        };
        mBenefitImages = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_benefit, container, false);
    }

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.benefit_item_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupViewContent();

        benefitSwipeRefresh.setOnRefreshListener(this);
    }

    //初始化RecyclerView的内容
    private void setupViewContent() {
        benefitSwipeRefresh.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        benefitSwipeRefresh.setOnRefreshListener(this);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        benefitRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        benefitRecyclerView.setAdapter(mBenefitAdapter);
        benefitRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    BenefitFragment.this.onScrollStateChanged();
                }
            }
        });

//        benefitRecyclerView.setAnimation(null);
//        benefitRecyclerView.addItemDecoration(null);


    }

    private void onScrollStateChanged() {
        int[] positions = new int[mStaggeredGridLayoutManager.getSpanCount()];
        mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
        for (int position : positions) {
            if (position == mStaggeredGridLayoutManager.getItemCount() - 1) {
//                loadMore();
                break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        mRealm.close();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getInstance(getActivity());

        refreshBenefitGoods();
        reloadData();
    }

    private void refreshBenefitGoods() {

        mAllBenefitImage.clear();
        RealmResults<Image> results = mRealm.where(Image.class).notEqualTo("width",0).findAll();
        mAllBenefitImage.addAll(results);
        mBenefitAdapter.replaceWith(mAllBenefitImage);
    }

    private void loadImageData() {
        RealmResults<Image> images = mRealm.where(Image.class).notEqualTo("width", 0).findAll();

    }

    @Override
    public void onRefresh() {

        reloadData();


    }

    private void reloadData() {
        //TODO 刷新照片列表

        benefitSwipeRefresh.setRefreshing(true);
        mAllBenefitImage.clear();

        loadData(1);
    }

    private void loadData(int startPage){
        GankCloudApi.getInstance()
                .getGankImages(GankCloudApi.LOAD_LIMIT, startPage)
                .cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GoodsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GoodsResult result) {

                    }
                });
    }

}
