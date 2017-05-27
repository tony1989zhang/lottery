package com.caiyi.data;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public class NumberStatEntity {

    public int state;
    public String error;
    public List<ItemsBean> items;

    public static class ItemsBean {
        public String date;
        public String data;
    }
}
