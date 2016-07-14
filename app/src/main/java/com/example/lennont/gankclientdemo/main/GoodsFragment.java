package com.example.lennont.gankclientdemo.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lennont.gankclientdemo.R;
import com.example.lennont.gankclientdemo.adapter.GoodsAdapter;
import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.GoodsResult;
import com.example.lennont.gankclientdemo.bean.Image;
import com.example.lennont.gankclientdemo.network.GankCloudApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.operators.BlockingOperatorToIterator;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BenefitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener {

    //region Field

    private static final String ARG_PARAM1 = "mType";
    @Bind(R.id.goods_recycler_view)
    RecyclerView benefitRecyclerView;
    @Bind(R.id.goods_swipe_refresh)
    SwipeRefreshLayout benefitSwipeRefresh;

    private String goodsType;  // Android/iOS

    private OnFragmentInteractionListener mListener;

    private GoodsAdapter mGoodsAdapter;

    private Subscriber<GoodsResult> getCommonGoodsObserver = new Subscriber<GoodsResult>() {
        @Override
        public void onCompleted() {
            Log.v("Goods", "Get Goods Compted");
        }

        @Override
        public void onError(Throwable e) {
            Log.v("Goods", "Get Goods Error: " + e.getMessage());
        }

        @Override
        public void onNext(GoodsResult result) {
            mGoodsAdapter.updateGoodsData(result.getResults());
        }
    };



    //endregion

    //region ctor

    public GoodsFragment() {
        // Required empty public constructor
    }

    public static GoodsFragment newInstance(String type) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        fragment.setArguments(args);

        return fragment;
    }

    //endregion

    //region Override

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goodsType = getArguments().getString(ARG_PARAM1);
        }

        mGoodsAdapter = new GoodsAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.goods_item_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        setupBaseView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadImageData();
    }

    //endregion

    private void loadImageData() {

        if(goodsType.equalsIgnoreCase("Android")) {
            GankCloudApi.getInstance()
                    .getAndroidGoods(GankCloudApi.LOAD_LIMIT, 1)
                    .cache()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getCommonGoodsObserver);
        } else if (goodsType.equalsIgnoreCase("iOS")) {
            GankCloudApi.getInstance()
                    .getIOSGoods(GankCloudApi.LOAD_LIMIT, 1)
                    .cache()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getCommonGoodsObserver);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setupBaseView() {
        benefitRecyclerView.setLayoutManager(new LinearLayoutManager(benefitRecyclerView.getContext()));
        benefitRecyclerView.setAdapter(mGoodsAdapter);

        benefitRecyclerView.addOnScrollListener(mOnScrollListener);
        benefitSwipeRefresh.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        benefitSwipeRefresh.setOnRefreshListener(this);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

}
