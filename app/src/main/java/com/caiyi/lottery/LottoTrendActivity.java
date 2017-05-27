package com.caiyi.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.caiyi.data.NumberStatEntity;
import com.caiyi.data.TrendData;
import com.caiyi.ui.DDTrendChart;
import com.caiyi.ui.DDTrendChart.ISelectedChangeListener;
import com.caiyi.ui.LottoTrendView;
import com.google.gson.Gson;
import com.lottery9188.Activity.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LottoTrendActivity extends Activity implements ISelectedChangeListener {

    private LottoTrendView mTrendView;
    final int maxSignleNum = 9;
    private DDTrendChart mTrendChart;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_lotto_trend);
        initViews();
        loadData();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            LottoTrendActivity.this.mTrendChart.updateData("01", (ArrayList) paramMessage.obj);
        }
    };

    private void initViews() {
        this.mTrendView = (LottoTrendView) findViewById(R.id.ltv_trendView);
        this.mTrendChart = new DDTrendChart(this, this.mTrendView);
        this.mTrendView.setChart(this.mTrendChart);
        this.mTrendChart.setShowYilou(true);
        this.mTrendChart.setDrawLine(true);
        this.mTrendChart.setSelectedChangeListener(this);
    }

    private final OkHttpClient client = new OkHttpClient();

    private void loadData() {
        // 根据01/30.xml 或者是01/50.xm可以调整数字
        String url = "http://m.1396mp.com/ssc/NumberStat?version=3000";
//        String url = "http://mobile.9188.com/data/app/zst/01/30.xml";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("string","string:"  + string);
                NumberStatEntity numberStatEntity = new Gson().fromJson(string, NumberStatEntity.class);
                a(numberStatEntity);
            }
        });
    }

    protected void a(NumberStatEntity numberStatEntity ){
        ArrayList arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        TrendData r0;
        for (int i = 0;i < numberStatEntity.items.size();i++) {
            NumberStatEntity.ItemsBean itemsBean = numberStatEntity.items.get(i);
            TrendData trendData = new TrendData();
                    trendData.setType("row");
            String s = itemsBean.date.replaceAll("-", "");
            trendData.setPid(s);
                    trendData.setRed(itemsBean.data);
//                    trendData.setBlue(newPullParser.getAttributeValue(null, "blue"));
//                    trendData.setBalls(newPullParser.getAttributeValue(null, "balls"));
//                    trendData.setOes(newPullParser.getAttributeValue(null, "oe"));
//                    trendData.setBss(newPullParser.getAttributeValue(null, "bs"));
//                    trendData.setOne(newPullParser.getAttributeValue(null, "one"));
//                    trendData.setTwo(newPullParser.getAttributeValue(null, "two"));
//                    trendData.setThree(newPullParser.getAttributeValue(null, "three"));
//                    trendData.setCodes(newPullParser.getAttributeValue(null, "codes"));
//                    trendData.setSum(newPullParser.getAttributeValue(null, "sum"));
//                    trendData.setSpace(newPullParser.getAttributeValue(null, "space"));
//                    trendData.setNum(newPullParser.getAttributeValue(null, "num"));
//                    trendData.setTimes(newPullParser.getAttributeValue(null, "times"));
//                    trendData.setForm(newPullParser.getAttributeValue(null, "form"));
                    arrayList.add(trendData);
        }
        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2) {

    }
}
