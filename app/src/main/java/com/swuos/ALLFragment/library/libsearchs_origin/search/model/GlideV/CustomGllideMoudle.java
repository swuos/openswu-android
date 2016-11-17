package com.swuos.ALLFragment.library.libsearchs_origin.search.model.GlideV;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;
import com.swuos.ALLFragment.library.libsearchs_origin.search.model.douabn.DoubanBookCoverImage;

import java.io.InputStream;

/**
 * Created by 张孟尧 on 2016/9/28.
 */

public class CustomGllideMoudle implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(DoubanBookCoverImage.class, InputStream.class, new CustomGlideImageLoader.Factory());
    }
}
