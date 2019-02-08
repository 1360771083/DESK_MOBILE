package com.example.desk.ui.first;

import android.content.Context;

import com.example.desk.entity.Seat;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FirstContract {
    interface View extends BaseView {
        void Error(String error);//请求网络数据失败调用
    }

    interface  Presenter extends BasePresenter<View> {
        List<Seat> getSeatData();//得到每个自习室的id,可用座位以及总座位
    }
}
