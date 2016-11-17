package com.swuos.ALLFragment.library.libsearchs.search.model.GlideV;


import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanApi;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanBookCoverImage;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanParse;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by 张孟尧 on 2016/9/28.
 */

public class CustomGlideFetcher implements DataFetcher<InputStream> {
    private final DoubanBookCoverImage mdoubanBookCoverImage;
    private volatile boolean mIsCanceled;
    private InputStream mInputStream;
    private Subscriber urlSubscriber;
    private Subscriber downSubscriber;

    public CustomGlideFetcher(DoubanBookCoverImage doubanBookCoverImage) {
        mdoubanBookCoverImage = doubanBookCoverImage;
        urlSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mdoubanBookCoverImage.setUrl(s);
            }

        };
        downSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mInputStream = responseBody.byteStream();
            }
        };
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        String url = mdoubanBookCoverImage.getUrl();
        if (url == null) {
            if (mIsCanceled) {
                return null;
            }
            DoubanApi.getDoubanSearch().doubanSearch(mdoubanBookCoverImage.getISBN().substring(9).replace("-", "")).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return DoubanParse.getbookcover(s);
                }
            }).subscribe(urlSubscriber);
            if (mdoubanBookCoverImage.getUrl() == null) {
                return null;
            }
        }
        if (mIsCanceled) {
            return null;
        }
        DoubanApi.getDoubanDownImage().downImage(mdoubanBookCoverImage.getUrl()).subscribe(downSubscriber);
        return mInputStream;
    }

    @Override
    public void cleanup() {
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mInputStream = null;
            }
        }

    }

    @Override
    public String getId() {
        return mdoubanBookCoverImage.getId();
    }

    @Override
    public void cancel() {
        mIsCanceled = true;
        if (urlSubscriber.isUnsubscribed()) {
            urlSubscriber.unsubscribe();
        }
        if (downSubscriber.isUnsubscribed()) {
            downSubscriber.unsubscribe();
        }
    }
}
