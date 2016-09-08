package com.swuos.ALLFragment.library.libsearchs.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.swuassistant.BaseActivity;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/9/7.
 */

public class BookDetailActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayoutl;
    private ImageView bookcover;
    private TextView writerTextView;
    private TextView suoshuhaoTextView;
    private TextView ISBNTextView;
    private TextView summaryTextView;
    private String title;
    private String writerString;
    private String suoshuhaoString;
    private String ISBNString;
    private String summaryString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book_detail);
        getintent();
        bindview();
        initview();
    }

    private void bindview() {
        toolbar = (Toolbar) findViewById(R.id.search_book_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        setSupportActionBar(toolbar);
        collapsingToolbarLayoutl = (CollapsingToolbarLayout) findViewById(R.id.search_book_collapsingToolabar);
        writerTextView = (TextView) findViewById(R.id.search_book_writer);
        suoshuhaoTextView = (TextView) findViewById(R.id.search_book_suoshuhao);
        ISBNTextView = (TextView) findViewById(R.id.search_book_ISBN);
        summaryTextView = (TextView) findViewById(R.id.search_book_summary);

    }

    private void initview() {
        dynamicAddView(collapsingToolbarLayoutl, "CollapsingToolbarLayoutcontent", R.color.colorPrimary);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationOnClickListener(this);
        writerTextView.setText(writerString);
        suoshuhaoTextView.setText(suoshuhaoString);
        ISBNTextView.setText(ISBNString);
        summaryTextView.setText(summaryString);

    }

    private void getintent() {
        Intent intent = getIntent();
        title = intent.getStringExtra("bookname");
        writerString = intent.getStringExtra("writer");
        suoshuhaoString = intent.getStringExtra("suoshuhao");
        ISBNString = intent.getStringExtra("ISBN");
        summaryString = intent.getStringExtra("summary");
    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
