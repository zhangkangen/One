package com.example.one.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.one.AppApplication;
import com.example.one.MyActivity;
import com.example.one.R;
import com.example.one.common.Constants;
import com.example.one.common.DateTimeUtil;
import com.example.one.common.LruImageCache;
import com.example.one.dialog.CustomProgressDialog;
import com.example.one.dto.HomePageDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2015/1/7.
 */
public class MainFragmentSub extends Fragment {

    private Integer id;
    private HomePageDto homePageDto;
    private View view;

    private ScrollView layoutMain;
    private NetworkImageView networkImageView;
    private TextView tvMainId;
    private TextView tvDay;
    private TextView tvDate;
    private TextView tvImageName;
    private TextView tvImageAuthor;
    private TextView tvOneWord;
    private ProgressBar proBarSub;
    private ProgressBar proBarImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_sub, container, false);
        layoutMain = (ScrollView) view.findViewById(R.id.layout_main);
        tvMainId = (TextView) view.findViewById(R.id.tv_mainId);
        tvDay = (TextView) view.findViewById(R.id.tv_day);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        networkImageView = (NetworkImageView) view.findViewById(R.id.imageView);
        tvImageName = (TextView) view.findViewById(R.id.tv_imageName);
        tvImageAuthor = (TextView) view.findViewById(R.id.tv_imageAuthor);
        tvOneWord = (TextView) view.findViewById(R.id.tv_oneWord);
        proBarSub = (ProgressBar) view.findViewById(R.id.proBarSub);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (homePageDto != null) {
            bindData(homePageDto);
            layoutMain.setVisibility(View.VISIBLE);
            proBarSub.setVisibility(View.GONE);
            return;
        }
        Log.i("Tag", getUserVisibleHint() + "");
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

    public void getJSONByVolley(Integer id) {
        layoutMain.setVisibility(View.GONE);
        proBarSub.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.MAINDETAILURL + "/" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("TAG",jsonObject.toString());
                        HomePageDto homePageDto = new Gson().fromJson(jsonObject.toString(),HomePageDto.class);

                        proBarSub.setVisibility(View.GONE);
                        layoutMain.setVisibility(View.VISIBLE);
                        bindData(homePageDto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        proBarSub.setVisibility(View.GONE);
                        Toast.makeText(getActivity().getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                });
        AppApplication.getInstance().addToRequestQueue(jsonObjectRequest);
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

        LruImageCache lruImageCache = AppApplication.getInstance().getmLruImageCache();

        ImageLoader imageLoader = new ImageLoader(AppApplication.getInstance().getRequestQueue(),
                lruImageCache);

        networkImageView.setDefaultImageResId(R.drawable.default_img_bg);
        networkImageView.setErrorImageResId(R.drawable.default_img_bg);
        networkImageView.setImageUrl(homePageDto.getImgUrl(), imageLoader);
    }
}
