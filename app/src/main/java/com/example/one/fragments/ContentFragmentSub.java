package com.example.one.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.one.R;
import com.example.one.common.Constants;
import com.example.one.dto.ArticleDto;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/7.
 */
public class ContentFragmentSub extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArticleDto articleDto;
    private Integer id;

    private TextView tvDateTime;
    private TextView tvArticle;
    private TextView tvArticleAuthor;
    private TextView tvArticleAuthor1;
    private TextView tvArticleAuthorDesc;
    private TextView tvArticleTitle;
    private SwipeRefreshLayout articleSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_sub, container, false);
        tvDateTime = (TextView) view.findViewById(R.id.tv_dateTime);
        tvArticle = (TextView) view.findViewById(R.id.tv_article);
        tvArticleAuthor = (TextView) view.findViewById(R.id.tv_articleauthor);
        tvArticleTitle = (TextView) view.findViewById(R.id.tv_articletitle);
        tvArticleAuthorDesc = (TextView) view.findViewById(R.id.tv_articleauthordesc);
        articleSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.articleSwipeLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        articleSwipeLayout.setOnRefreshListener(this);
        articleSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light
                , android.R.color.holo_orange_light
                , android.R.color.holo_blue_light,
                android.R.color.holo_green_light);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            if (id != null) {
                //缓存读取
                onRefresh();
            }
        }

    }

    private void getJSONByVolley(Integer id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.CONTENTURL + "/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        articleDto = new Gson().fromJson(jsonObject.toString(), ArticleDto.class);
                        bindData(articleDto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });

        //AppApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * 绑定数据
     *
     * @param articleDto
     */
    private void bindData(ArticleDto articleDto) {

        tvDateTime.setText(articleDto.getPublishDate().toString());

        tvArticleTitle.setText(articleDto.getArticleTitle());
        tvArticleAuthor.setText(articleDto.getArticleAuthor());
        String article = new String(Base64.decode(articleDto.getArticle(), Base64.DEFAULT));
        tvArticle.setText(Html.fromHtml(article));
        tvArticleAuthorDesc.setText(articleDto.getArticleAuthorDesc());
    }

    @Override
    public void onRefresh() {
        articleSwipeLayout.setRefreshing(true);
        if (articleDto != null) {
            articleSwipeLayout.setRefreshing(false);
            bindData(articleDto);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    getJSONByVolley(id);
                    articleSwipeLayout.setRefreshing(false);
                }
            }, 3000);
        }
    }
}
