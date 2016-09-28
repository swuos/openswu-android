package com.swuos.ALLFragment.library.libsearchs.search.model.douabn;

/**
 * Created by 张孟尧 on 2016/9/28.
 */

public class DoubanBookCoverImage {
    //真正的bookCover的url
    private String url;
    //区分id
    private String id;
    private String ISBN;

    public String getISBN() {
        return ISBN;
    }

    public DoubanBookCoverImage(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DoubanBookCoverImage that = (DoubanBookCoverImage) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
