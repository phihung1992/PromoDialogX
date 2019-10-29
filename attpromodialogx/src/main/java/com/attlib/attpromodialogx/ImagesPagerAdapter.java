package com.attlib.attpromodialogx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

public class ImagesPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private AdDialogInfo[] mData;

    public ImagesPagerAdapter(Context context, AdDialogInfo[] data) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.item_image, container, false);
        ImageView imageView = itemView.findViewById(R.id.iv_image);
        if (mData != null)
            Glide.with(mContext).load(mData[position].getPromoImage()).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
