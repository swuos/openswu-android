package com.swuos.ALLFragment.library.libsearchs.bookdetail.model;

/**
 * Created by 张孟尧 on 2016/9/4.
 */
public class BookLocationInfo {

    //    馆藏地址
    private String address;
    //    在架
    private String frameState;

    public String getShelfUrl() {
        return shelfUrl;
    }

    public void setShelfUrl(String shelfUrl) {
        this.shelfUrl = shelfUrl;
    }

    //    架位
    private String shelfUrl;
    private String shelf;

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFrameState() {
        return frameState;
    }

    public void setFrameState(String frameState) {
        this.frameState = frameState;
    }
}
