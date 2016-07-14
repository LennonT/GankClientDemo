package com.example.lennont.gankclientdemo.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.lennont.gankclientdemo.R;
import com.example.lennont.gankclientdemo.adapter.ContentPageAdapter;
import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.GoodsResult;
import com.example.lennont.gankclientdemo.bean.Image;
import com.example.lennont.gankclientdemo.data.ImageGoodsCache;
import com.example.lennont.gankclientdemo.network.GankCloudApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    //region Field

    @Bind(R.id.main_toolbar)   //顶部工具栏
            Toolbar mainToolbar;
    @Bind(R.id.tabs)           //切换tab
            TabLayout tabs;
    @Bind(R.id.app_bar)        //工具栏整体
            AppBarLayout appBar;
    @Bind(R.id.view_pager)     //切换各fragment的ViewPager
            ViewPager viewPager;
    @Bind(R.id.nav_view)       //侧滑菜单栏
            NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;  //侧滑布局

    private Realm mRealm;

    //endregion

    //region Override

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRealm = Realm.getInstance(this);

        setSupportActionBar(mainToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        setPagerContent();

        tabs.setupWithViewPager(viewPager);

        loadAllImages();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //endregion

    //region Private

    private void loadAllImages() {
        RealmResults<Image> allImage = mRealm.where(Image.class).findAll();   //获取当前已缓存的Image
        if (allImage.size() == 0) {

            GankCloudApi.getInstance()
                    .getGankImages(GankCloudApi.LOAD_LIMIT, 1)
                    .subscribeOn(Schedulers.io())
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

                            Log.v("retrofit", "callback");

                            if (result != null && result.getResults().size() > 0) {

                                mRealm.beginTransaction();
                                for (Goods goods : result.getResults()) {
                                    Image image = mRealm.createObject(Image.class);
                                    Image.updateValue(image, goods);
                                }

                            }

                            mRealm.commitTransaction();
                        }
                    });
        } else {
            ImageGoodsCache.getInstance().replaceAllGoods(allImage);
        }

    }

    private void setPagerContent() {
        ContentPageAdapter mAdapter = new ContentPageAdapter(getSupportFragmentManager());
        mAdapter.addItem(GoodsFragment.newInstance("Android"), "Android");
        mAdapter.addItem(GoodsFragment.newInstance("iOS"), "iOS");
        mAdapter.addItem(new BenefitFragment(), "Gank");

        viewPager.setAdapter(mAdapter);
    }

    //endregion

}
