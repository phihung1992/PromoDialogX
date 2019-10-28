package com.attlib.attpromodialogx;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Random;

public class PromoDialog extends DialogFragment {
    private Context mContext;
    private View mRootView;
    private TextView mTvAppName;
    private TextView mTvAppDescription;

    private AdDialogInfo[] mData;
    private int mCurrentIndex = 0;
    private boolean mCanceledByBackButton = false;
    private boolean mCanceledByTouchOutSite = false;

    private OnCallBack mListener;


    public PromoDialog setData(AdDialogInfo[] data) {
        mData = data;
        return this;
    }

    public PromoDialog setCanceled(boolean canceledByBackButton, boolean canceledByTouchOutSite) {
        mCanceledByBackButton = canceledByBackButton;
        mCanceledByTouchOutSite = canceledByTouchOutSite;
        return this;
    }

    public PromoDialog setListener(OnCallBack listener) {
        mListener = listener;
        return this;
    }

    public void show(FragmentActivity activity) {
        mContext = activity;
        if (activity != null && activity.getSupportFragmentManager() != null) {
            this.show(activity.getSupportFragmentManager(), "PromoDialog");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_ads, container, false);

        initViews();
        return mRootView;
    }

    private void initViews() {
        mRootView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (mData == null || mData.length == 0) return;

        mTvAppName = mRootView.findViewById(R.id.tv_app_name);
        mTvAppDescription = mRootView.findViewById(R.id.tv_app_description);

        mCurrentIndex = new Random().nextInt(mData.length);
        ViewPager vpImages = mRootView.findViewById(R.id.vp_images);
        ImagesPagerAdapter adapter = new ImagesPagerAdapter(mContext, mData);
        vpImages.setAdapter(adapter);
        vpImages.setCurrentItem(mCurrentIndex);
        vpImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                updatePromoDataToViews(mCurrentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        IndicatorView indicatorView = mRootView.findViewById(R.id.images_indicator);
        indicatorView.setViewPager(vpImages);

        updatePromoDataToViews(mCurrentIndex);

        mRootView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + mData[mCurrentIndex].getPromoAppPackage()));
                mContext.startActivity(intent);
                if (mListener != null) mListener.onOk();
                dismiss();
            }
        });

        mRootView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onCanceled();
                dismiss();
            }
        });
    }

    private void updatePromoDataToViews(int position) {
        mTvAppName.setText(mData[position].getPromoAppName());
        String des = mData[position].getPromoDescription();
        if (des == null || des.isEmpty()) {
            mTvAppDescription.setVisibility(View.GONE);
        } else {
            mTvAppDescription.setText(mData[position].getPromoDescription());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(mCanceledByBackButton);
        dialog.setCanceledOnTouchOutside(mCanceledByTouchOutSite);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog,
                                 int keyCode, android.view.KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    return !mCanceledByBackButton;
                }
                // Otherwise, do nothing else
                else return false;
            }
        });
    }

    public interface OnCallBack {
        void onOk();

        void onCanceled();
    }
}
