package com.swuos.ALLFragment.library.library.model;

/**
 * Created by youngkaaa on 2016/10/27.
 */
public class BookItem {
    private String bookName;
    private String bookIsbn;
    private String kind;
    private String time;
    private float fine;

    public BookItem() {
    }

    public BookItem(String bookName, String bookIsbn, String kind, String time, float fine) {
        this.bookName = bookName;
        this.bookIsbn = bookIsbn;
        this.kind = kind;
        this.time = time;
        this.fine = fine;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BookItem item = (BookItem) obj;

        if (bookIsbn != null && item.bookIsbn != null && time != null && item.time != null
                && bookIsbn.equals(item.bookIsbn) && time.equals(item.time)) {
            return true;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 7 * bookIsbn.hashCode() + 13 * time.hashCode();
    }
}
