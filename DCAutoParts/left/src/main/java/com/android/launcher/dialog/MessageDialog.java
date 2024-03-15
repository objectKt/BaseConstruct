package com.android.launcher.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.util.StringUtils;
import com.android.launcher.vo.AlertVo;
import com.lxj.xpopup.core.CenterPopupView;

import io.reactivex.rxjava3.annotations.NonNull;


/**
 * @date： 2023/11/9
 * @author: 78495
 */
public class MessageDialog extends CenterPopupView {

    private AlertVo item;
    private ImageView messageIV;
    private TextView messageTV;
    private ImageView doorIV;
    private ConstraintLayout multiplyCL;

    private MessageType type = MessageType.NONE;

    private int dialogWidth;
    private int dialogHeight;

    public MessageDialog(@NonNull Context context, AlertVo item, int dialogWidth, int dialogHeight) {
        super(context);
        this.item = item;
        this.dialogWidth = dialogWidth;
        this.dialogHeight = dialogHeight;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        type = MessageType.NONE;
//        ConstraintLayout dialogRootCL = findViewById(R.id.dialogRootCL);
//        ViewGroup.LayoutParams layoutParams = dialogRootCL.getLayoutParams();
//        layoutParams.width = dialogWidth;
//        layoutParams.height = dialogHeight;
//        if(MeterActivity.currentFragmentType == MeterFragmentType.MAP){
//            dialogRootCL.setBackgroundResource(R.drawable.bg_map_warn_dialog);
//        }else{
//            dialogRootCL.setBackgroundResource(R.drawable.bg_dialog);
//        }

        multiplyCL = findViewById(R.id.multiplyCL);
        messageIV = findViewById(R.id.messageIV);
        messageTV = findViewById(R.id.messageTV);

        doorIV = findViewById(R.id.doorIV);

        doorIV.setVisibility(View.GONE);
        multiplyCL.setVisibility(View.GONE);


        if (item == null) {
            return;
        }

        if (item.getAlertImg() != 0 && !TextUtils.isEmpty(item.getAlertMessage())) {
            multiplyCL.setVisibility(View.VISIBLE);
            doorIV.setVisibility(View.GONE);
            type = MessageType.IMG_TEXT;
            messageIV.setImageResource(item.getAlertImg());
            if (item.getAlertMessage() != null) {
                messageTV.setText(item.getAlertMessage());
            }
        } else if (item.getAlertImg() == 0) {
            type = MessageType.TEXT;
            multiplyCL.setVisibility(View.VISIBLE);
            doorIV.setVisibility(View.GONE);

            messageIV.setVisibility(View.GONE);
            if (item.getAlertMessage() != null) {
                messageTV.setText(item.getAlertMessage());
            }
        } else {
            type = MessageType.IMG;
            doorIV.setVisibility(View.VISIBLE);
            multiplyCL.setVisibility(View.GONE);
            doorIV.setImageResource(item.getAlertImg());
        }
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_message;
    }

    public void updateMessage(String message) {
        if (messageTV != null) {
            messageTV.setText(StringUtils.filterNULL(message));
        }

    }

    public void updateImg(int redId) {
        if (type == MessageType.IMG_TEXT) {
            if (messageIV != null) {
                messageIV.setImageResource(redId);
            }
        } else {
            if (doorIV != null) {
                doorIV.setImageResource(redId);
            }

        }
    }


    enum MessageType {
        NONE,
        TEXT,
        IMG,
        IMG_TEXT
    }
}
