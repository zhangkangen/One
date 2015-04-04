package com.example.one;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.one.common.Constants;
import com.example.one.fragments.ContentFragment;
import com.example.one.fragments.MainFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyActivity extends FragmentActivity{

    private Map<String, Object> map;
    private boolean isExit;

    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private RadioButton rb_main;
    private RadioButton rb_article;
    private List<Integer> ids;
    private LinearLayout mainLayout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        rb_main.setChecked(true);
                    } else {
                        radioGroup.getFocusedChild().setClickable(true);
                    }
                    Log.i("TAG", "radioButton id =" + radioGroup.getCheckedRadioButtonId());
                    break;
                case 1:
                    radioGroup.setClickable(false);
                    displayToast("网络请求发生了错误", Toast.LENGTH_SHORT);
                    Log.i("TAG", "radioButton id =" + radioGroup.getCheckedRadioButtonId());
                    break;
                case 3:
                    isExit = false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        radioGroup = (RadioGroup) findViewById(R.id.rg_main);
        rb_main = (RadioButton) findViewById(R.id.rb_main);
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        getJSONByVolley(Constants.MAINURL);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("Tag", checkedId + "=============" + R.id.rb_main + ";;;;;" + R.id.rb_article);
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = FragmentFactory.getInstance(checkedId);
                if (ids != null && ids.size() > 0) {
                    final ArrayList<Integer> list = (ArrayList<Integer>) ids;
                    Bundle argument = new Bundle();
                    argument.putIntegerArrayList("ids", list);
                    fragment.setArguments(argument);
                } else {
                    Log.i("Tag", "没有ids，checkedId ================================ " + checkedId);
                    getJSONByVolley(Constants.MAINURL);
                }

                transaction.replace(R.id.mainLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    private void getJSONByVolley(String url) {
        final Message msg = new Message();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        ids = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Integer>>() {
                        }.getType());

                        handler.sendEmptyMessage(0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = volleyError.getMessage();
                        handler.sendMessage(msg);
                    }
                });


        AppApplication.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Toast信息
     *
     * @param str  要显示的内容
     * @param time 时间 Toast.Long/Toast.Short
     */
    private void displayToast(String str, int time) {
        Toast.makeText(this, str, time).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(3, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class FragmentFactory {
        public static Fragment getInstance(int checkedId) {
            Fragment fragment = null;
            switch (checkedId) {
                case R.id.rb_main:
                    fragment = new MainFragment();
                    break;
                case R.id.rb_article:
                    fragment = new ContentFragment();
                    break;
            }
            return fragment;
        }
    }
}
