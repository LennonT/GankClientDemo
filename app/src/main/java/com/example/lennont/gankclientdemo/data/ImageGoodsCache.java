package com.example.lennont.gankclientdemo.data;

import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import io.realm.RealmResults;

/**
 * Created by LennonT on 2016/7/13.
 * 用于缓存福利照片的信息在内存中
 */
public class ImageGoodsCache {

    //region Field

    //存储临时缓存
    private List<Image> imageArray;

    private static ImageGoodsCache instance;

    private Random random = new Random();

    //endregion

    //region ctor

    private ImageGoodsCache() {
        imageArray = new ArrayList<>();
    }

    public static ImageGoodsCache getInstance() {
        if(instance == null) {
            synchronized (ImageGoodsCache.class) {
                if(instance == null) {
                    instance = new ImageGoodsCache();
                }
            }
        }

        return instance;
    }

    //endregion

    //region Public

    //从Goods数据增加一个
    public void addImage(Goods goods) {
        Image image = new Image();
        Image.updateValue(image, goods);

        imageArray.add(image);
    }

    //从Goods数据增加多个
    public void addAllGoods(ArrayList<Goods> goodses) {
        if(goodses != null && goodses.size() > 0) {

            imageArray.clear();
            for(Goods goods : goodses) {
                addImage(goods);
            }
        }
    }

    //直接从Realm数据替换
    public void replaceAllGoods(RealmResults<Image> goodses) {
        if(goodses != null && goodses.size() > 0 ) {

            imageArray.clear();
            imageArray.addAll(goodses);
        }
    }

    //随机获取一张用于Android和iOS的背景显示
    public Image getRandomImage(int randomIndex) {
        int count = imageArray.size();

        if(count == 0 ) return null;

        int index = random.nextInt(count); //当前随机位置

        if(index + randomIndex >= count) {
            return imageArray.get(index);
        } else {
            return imageArray.get(index + randomIndex);
        }
    }

    //endregion

}
