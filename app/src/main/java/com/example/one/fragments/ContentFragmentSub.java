package com.example.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.one.AppApplication;
import com.example.one.R;
import com.example.one.common.Constants;
import com.example.one.common.DateTimeUtil;
import com.example.one.dto.ArticleDto;
import com.example.one.dto.HomePageDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/1/7.
 */
public class ContentFragmentSub extends Fragment {

    private ArticleDto articleDto;
    private Integer id;

    private ScrollView layout_content;
    private ProgressBar proBarContentSub;
    private TextView tvDateTime;
    private TextView tvArticle;
    private TextView tvArticleAuthor;
    private TextView tvArticleAuthor1;
    private TextView tvArticleAuthorDesc;
    private TextView tvArticleTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_sub, container, false);
        tvDateTime = (TextView) view.findViewById(R.id.tv_dateTime);
        tvArticle = (TextView) view.findViewById(R.id.tv_article);
        tvArticleAuthor = (TextView) view.findViewById(R.id.tv_articleauthor);
        tvArticleTitle = (TextView) view.findViewById(R.id.tv_articletitle);
        tvArticleAuthorDesc = (TextView) view.findViewById(R.id.tv_articleauthordesc);
        layout_content = (ScrollView) view.findViewById(R.id.layout_content);
        proBarContentSub = (ProgressBar) view.findViewById(R.id.proBar_contentsub);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (articleDto != null) {
            bindData(articleDto);

            return;
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            if (id != null) {
                //缓存读取

                //网络读取
                getJSONByVolley(id);
            }
        }

    }

    private void getJSONByVolley(Integer id) {
        layout_content.setVisibility(View.GONE);
        proBarContentSub.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.CONTENTURL + "/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        layout_content.setVisibility(View.VISIBLE);
                        proBarContentSub.setVisibility(View.GONE);
                        ArticleDto articleDto = new Gson().fromJson(jsonObject.toString(),ArticleDto.class);
                        bindData(articleDto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        layout_content.setVisibility(View.VISIBLE);
                        proBarContentSub.setVisibility(View.GONE);
                    }
                });

        AppApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * 绑定数据
     * @param articleDto
     */
    private void bindData(ArticleDto articleDto) {

        tvDateTime.setText(articleDto.getPublishDate().toString());

        tvArticleTitle.setText(articleDto.getArticleTitle());
        tvArticleAuthor.setText(articleDto.getArticleAuthor());
        String article = new String(Base64.decode(articleDto.getArticle(),Base64.DEFAULT));
        tvArticle.setText(Html.fromHtml(article));
        tvArticleAuthorDesc.setText(articleDto.getArticleAuthorDesc());
    }
}
