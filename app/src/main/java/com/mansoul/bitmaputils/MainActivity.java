package com.mansoul.bitmaputils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mansoul.bitmaputils.utils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_photo;
    public static final String PHOTOS_URL = "http://10.0.2.2:8080/zhbj/photos/photos_1.json";// 组图信息接口
    private List<PhotosBean.PhotosData> photosData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化xutils
        x.Ext.init(getApplication());

        lv_photo = (ListView) findViewById(R.id.lv_photo);

        //服务器请求数据
        getDataFromServer();
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(PHOTOS_URL);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess");

                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);
        photosData = photosBean.data.news;

    }

    class MyAdapter extends BaseAdapter {

        private BitmapUtils mBitmapUtils;
        public MyAdapter() {
            mBitmapUtils = new BitmapUtils();

        }

        @Override
        public int getCount() {
            return photosData.size();
        }

        @Override
        public PhotosBean.PhotosData getItem(int position) {
            return photosData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.list_item_photos, null);
                holder = new ViewHolder();

                holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_title.setText(photosData.get(position).title);

            return convertView;
        }
    }

    static public class ViewHolder {
        ImageView iv_img;
        TextView tv_title;
    }
}
