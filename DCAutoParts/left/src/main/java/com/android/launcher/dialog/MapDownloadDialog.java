package com.android.launcher.dialog;

import android.content.Context;
import android.widget.TextView;

import com.android.launcher.R;
import dc.library.ui.numberprogress.NumberProgressBar;
import com.lxj.xpopup.core.CenterPopupView;

public class MapDownloadDialog extends CenterPopupView {

    private NumberProgressBar numberProgressBar;

    private String cityName;
    private TextView hintTv;
    private TextView cancelTv;

    private OnClickListener cancelListener;

    public MapDownloadDialog(Context context, String cityName, OnClickListener listener) {
        super(context);
        this.cityName = cityName;
        this.cancelListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_map_download;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView cityTv = findViewById(R.id.cityTv);
        hintTv = findViewById(R.id.hintTv);
        numberProgressBar = findViewById(R.id.numberProgressBar);

        cancelTv = findViewById(R.id.cancelTv);
        if (cityName != null) {
            cityTv.setText(cityName);
//            if(offlineMapCity.getcompleteCode() == 100){
//                hintTv.setText(getResources().getString(R.string.updating));
//            }else{
            hintTv.setText(getResources().getString(R.string.downloading));
//            }
        }
        cancelTv.setOnClickListener(cancelListener);
    }

    public void updateProgress(int status, int progress) {
        if (status == 1) {
            hintTv.setText(getResources().getString(R.string.unzip));
        }
        numberProgressBar.setProgress(progress);
    }

}
