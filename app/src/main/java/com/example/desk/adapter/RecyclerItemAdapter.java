package com.example.desk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.entity.ShuoShuo;
import com.example.desk.util.TLog;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ShuoShuo> items;
    public static interface  OnPlayClickListener{
        void onItemClick(ShuoShuo shuo);
    }
    OnPlayClickListener onPlayClickListener;
    public void setOnplayClickListener(OnPlayClickListener onplayClickListener){
        this.onPlayClickListener = onplayClickListener;
    }
    public RecyclerItemAdapter(Context mContext, ArrayList<ShuoShuo> items) {
        this.mContext = mContext;
        this.items = items;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        //ViewHolder holder = null;
        //if (parent == null){
        ViewHolder holder = new ViewHolder(view);
          //  parent.setTag(holder);
        //}else {
          //  holder = (ViewHolder) parent.getTag();
        //}
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShuoShuo shuo = items.get(position);
        holder.tvTitle.setText(shuo.getUserName());
        holder.tvContent.setText(shuo.getContent());
        holder.tvScanner.setText(shuo.getBrowser());
        holder.tvTime.setText(shuo.getTime());
        // 使用ImageLoader加载网络图片
        /*
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnLoading(R.mipmap.ic_launcher) // 加载中显示的默认图片
                .showImageOnFail(R.mipmap.ic_launcher) // 设置加载失败的默认图片
                .cacheInMemory(true) // 内存缓存
                .cacheOnDisk(true) // sdcard缓存
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                .build();//
        ImageLoader.getInstance().displayImage(shuo.getPicture(),holder.ivAvatar,options);
        */
        Glide.with(mContext).load(shuo.getPicture()).into(holder.ivAvatar);
        final ArrayList<String> imageUrls = shuo.getEpicture();
        if (imageUrls == null || imageUrls.size() == 0){
            //没有图片资源隐藏GridView
            holder.gridview.setVisibility(View.GONE);
        }else {
            holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
        }
        // 点击回帖九宫格，查看大图
        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageBrower(position, imageUrls);
            }
        });
        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,shuo.getBrowser(),Toast.LENGTH_SHORT).show();
                if (onPlayClickListener != null){
                    onPlayClickListener.onItemClick(shuo);
                }
            }
        });

    }
    /**
     * 打开图片查看器
     *
     * @param position
     * @param imageUrls
     */
    private void imageBrower(int position, ArrayList<String> imageUrls) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageUrls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        TLog.log(items.size()+"");
        return items.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_scanner)
        TextView tvScanner;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gridview)
        NoScrollGridView gridview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
