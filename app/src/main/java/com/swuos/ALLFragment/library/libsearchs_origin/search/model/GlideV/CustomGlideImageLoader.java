package com.swuos.ALLFragment.library.libsearchs_origin.search.model.GlideV;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.swuos.ALLFragment.library.libsearchs_origin.search.model.douabn.DoubanBookCoverImage;

import java.io.InputStream;

/**
 * Created by 张孟尧 on 2016/9/28.
 */

public class CustomGlideImageLoader implements ModelLoader<DoubanBookCoverImage, InputStream> {

    private final ModelCache<DoubanBookCoverImage, DoubanBookCoverImage> modelCache;

    public CustomGlideImageLoader(ModelCache<DoubanBookCoverImage, DoubanBookCoverImage> modelCache) {
        this.modelCache = modelCache;
    }

    public CustomGlideImageLoader() {
        this(null);
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(DoubanBookCoverImage model, int width, int height) {
        DoubanBookCoverImage doubanBookCoverImage = (DoubanBookCoverImage) model;
        if (modelCache != null) {
            doubanBookCoverImage = modelCache.get((DoubanBookCoverImage) model, 0, 0);
            if (doubanBookCoverImage == null) {
                modelCache.put(model, 0, 0, model);
                doubanBookCoverImage = model;
            }
        }
        return new CustomGlideFetcher(doubanBookCoverImage);
    }

    public static class Factory implements ModelLoaderFactory<DoubanBookCoverImage, InputStream> {
        //设置缓存
        private final ModelCache<DoubanBookCoverImage, DoubanBookCoverImage> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<DoubanBookCoverImage, InputStream> build(Context context, GenericLoaderFactory factories) {

            return new CustomGlideImageLoader(mModelCache);
        }

        @Override
        public void teardown() {

        }
    }
}
