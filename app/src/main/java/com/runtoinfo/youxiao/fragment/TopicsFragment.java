package com.runtoinfo.youxiao.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.runtoinfo.teacher.HttpEntity;
import com.runtoinfo.teacher.bean.RequestDataEntity;
import com.runtoinfo.teacher.bean.TopiceHttpResultEntity;
import com.runtoinfo.teacher.utils.HttpUtils;
import com.runtoinfo.youxiao.R;
import com.runtoinfo.youxiao.adapter.TopicsArticleAdapter;
import com.runtoinfo.youxiao.adapter.ViewPageAdapter;
import com.runtoinfo.youxiao.databinding.FragmentTopicsBinding;
import com.runtoinfo.youxiao.entity.SchoolDynamicsEntity;
import com.runtoinfo.youxiao.globalTools.adapter.UniversalRecyclerAdapter;
import com.runtoinfo.youxiao.globalTools.utils.DialogMessage;
import com.runtoinfo.youxiao.globalTools.utils.Entity;
import com.runtoinfo.youxiao.ui.DepthPageTransformer;
import com.runtoinfo.youxiao.utils.IntentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import myapplication.MyApplication;

/**
 * Created by Qjc on 2018/5/24 0024.
 */

public class TopicsFragment extends BaseFragment {

    public List<View> listView = new ArrayList<>();
    public List<TopiceHttpResultEntity> resultList = new ArrayList<>();
    public ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    public int currentItem;
    public FragmentTopicsBinding binding;
    public ViewPageAdapter adapter;
    public TopicsArticleAdapter articleAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topics, container, false);
        viewPages();
        startImageViewScroll();
        getAllArticle();
        return binding.getRoot();
    }

    public void startImageViewScroll(){
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                currentItem = (currentItem + 1) % listView.size();
                mHandler.sendEmptyMessage(0);
            }
        }, 2, 2, java.util.concurrent.TimeUnit.SECONDS);
    }

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    binding.topicsViewpage.setCurrentItem(currentItem);
                    break;
                case 200:
                    initItemsData();
                    initEvent();
                    break;
                case 400:
                    DialogMessage.showToast(getContext(), "数据处理错误");
                    break;
            }

        }
    };

    public void initItemsData(){
        if (resultList.size() > 0) {
            binding.topicsRecyclerview.setHasFixedSize(true);
            binding.topicsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            articleAdapter = new TopicsArticleAdapter(getActivity(), resultList, R.layout.topics_recyclerview_items);
            binding.topicsRecyclerview.setAdapter(articleAdapter);
        }else{
            DialogMessage.showToast(getContext(), "请求数据失败");
        }
    }
    public void initEvent(){
        articleAdapter.setOnItemClickListener(new UniversalRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopiceHttpResultEntity topics = articleAdapter.getList().get(position);
                String coverType = topics.getCoverType();
                switch (coverType){
                    case "0":
                        SchoolDynamicsEntity schoolEntity = new SchoolDynamicsEntity();
                        schoolEntity.setPublishTime(topics.getPublishTime());
                        schoolEntity.setContent(topics.getContentpublic());
                        schoolEntity.setReadNumber(Integer.valueOf(topics.getReplyNumber()));
                        schoolEntity.setTile(topics.getTitle());
                        String data = new Gson().toJson(schoolEntity);

                        ARouter.getInstance().build("/main/schoolDynamics").withString(IntentDataType.INTENT_KEY, IntentDataType.TOPICS)
                        .withString(IntentDataType.DATA, data);
                        break;
                    case "1"://视频
                        break;
                    case "null":
                        break;
                }
            }
        });
    }

    /**
     * 图片左右滑动引导页
     */
    public void viewPages(){
        initData();
        adapter = new ViewPageAdapter(listView);
        binding.topicsViewpage.setAdapter(adapter);
        binding.topicsIndicator.setViewPager(binding.topicsViewpage);
    }

    public void initData(){
        listView.clear();
        for (int i = 0; i < 3; i++)
        {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(R.drawable.topics_img_banner);
            listView.add(imageView);
        }
    }

    public void getAllArticle(){
        RequestDataEntity requestDataEntity = new RequestDataEntity();
        requestDataEntity.setToken(spUtils.getString(Entity.TOKEN));
        requestDataEntity.setUserId(Integer.parseInt(spUtils.getString(Entity.USER_ID)));
        requestDataEntity.setCourseId(spUtils.getInt(Entity.COURSE_ID));
        requestDataEntity.setUrl(HttpEntity.MAIN_URL + HttpEntity.COURSE_TOPICS);

        HttpUtils.getTopics(mHandler, requestDataEntity, resultList);
    }
}
