package com.example.one.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.one.R;
import com.example.one.common.Constants;
import com.example.one.dto.HomePageDto;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2015/1/7.
 */
public class MainFragmentSub extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private Integer id;
    private HomePageDto homePageDto;
    private View view;

    private NetworkImageView networkImageView;
    private TextView tvMainId;
    private TextView tvDay;
    private TextView tvDate;
    private TextView tvImageName;
    private TextView tvImageAuthor;
    private TextView tvOneWord;
    private SwipeRefreshLayout mainSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_sub, container, false);
        tvMainId = (TextView) view.findViewById(R.id.tv_mainId);
        tvDay = (TextView) view.findViewById(R.id.tv_day);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        networkImageView = (NetworkImageView) view.findViewById(R.id.imageView);
        tvImageName = (TextView) view.findViewById(R.id.tv_imageName);
        tvImageAuthor = (TextView) view.findViewById(R.id.tv_imageAuthor);
        tvOneWord = (TextView) view.findViewById(R.id.tv_oneWord);
        mainSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.mainSwipeLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainSwipeLayout.setOnRefreshListener(this);

        mainSwipeLayout.setColorSchemeResources(
                android.R.color.holo_red_light
                , android.R.color.holo_orange_light
                , android.R.color.holo_blue_light,
                android.R.color.holo_green_light
        );

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            if (id != null) {
                //缓存读取
                onRefresh();
            }
        }
    }

    public void getJSONByVolley(Integer id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.MAINDETAILURL + "/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("TAG",jsonObject.toString());
                        homePageDto = new Gson().fromJson(jsonObject.toString(),HomePageDto.class);

                        bindData(homePageDto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                });
        //AppApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * 获取数据成功后绑定数据
     * @param homePageDto
     */
    public void bindData(HomePageDto homePageDto) {
        tvMainId.setText(String.valueOf(homePageDto.getId()));
        tvImageName.setText(homePageDto.getImgName());
        tvImageAuthor.setText(homePageDto.getImgAuthor());
        tvOneWord.setText(homePageDto.getOneWord());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(homePageDto.getPublishDate());
        Log.i("TAG",d);

        //LruImageCache lruImageCache = AppApplication.getInstance().getmLruImageCache();

       // ImageLoader imageLoader = new ImageLoader(AppApplication.getInstance().getRequestQueue(),
         //       lruImageCache);

        networkImageView.setDefaultImageResId(R.drawable.default_img_bg);
        networkImageView.setErrorImageResId(R.drawable.default_img_bg);
       // networkImageView.setImageUrl(homePageDto.getImgUrl(), imageLoader);
    }

    @Override
    public void onRefresh() {
        mainSwipeLayout.setRefreshing(true);
        if (homePageDto!=null){
            bindData(homePageDto);
            mainSwipeLayout.setRefreshing(false);
        }else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    getJSONByVolley(id);
                    mainSwipeLayout.setRefreshing(false);
                }
            }, 2500);
        }
    }
}
