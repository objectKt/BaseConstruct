package com.android.launcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.android.launcher.vo.PhoneVo;

import java.util.List;

/**
 * 中间 电话本
 */
public class PhoneMidAdapter extends ArrayAdapter<PhoneVo> {

    public int resourceId ;

    public PhoneMidAdapter(@NonNull Context context, int resource, List<PhoneVo> phoneVos) {
        super(context, resource, phoneVos);
        this.resourceId = resource ;
    }

    private int selectedPosition = 0;// 选中的位置

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        PhoneVo phoneVo = getItem(position) ;
        View view = null;
        ViewPhoneMidHolder viewPhoneMidHolder;
        if(convertView==null){
            viewPhoneMidHolder=new ViewPhoneMidHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);     // 得到子布局，非固定的，和子布局id有关

            viewPhoneMidHolder.phoneUserName = (TextView) view.findViewById(R.id.phoneUserName);  //获取控件
            viewPhoneMidHolder.phoneNum = (TextView) view.findViewById(R.id.phoneNum);  //获取控件

            view.setTag(viewPhoneMidHolder);

        }else{
            view=convertView;      //convertView不为空代表布局被加载过，只需要将convertView的值取出即可
            viewPhoneMidHolder=(ViewPhoneMidHolder) view.getTag();
        }
        viewPhoneMidHolder.phoneUserName.setText(phoneVo.getPhoneUserName());
        viewPhoneMidHolder.phoneNum.setText(phoneVo.getPhoneNum());
        if (selectedPosition == position) {
            view.setBackgroundResource(R.drawable.item_seletor);
        } else {
            view.setBackground(null);
        }

        return view;
    }

     class ViewPhoneMidHolder {   //当布局加载过后，保存获取到的控件信息。
        TextView phoneUserName;
        TextView phoneNum ;
    }


}
