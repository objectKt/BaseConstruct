package com.android.launcher.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.App;
import com.android.launcher.R;

import java.util.List;

/**
 * 仪表中内容选择
 *
 */
public class MeterCmsAdapter extends ArrayAdapter<String> {

    public int resourceId ;

    public MeterCmsAdapter(@NonNull Context context, int resource, List<String> phones) {
        super(context, resource,phones);
        this.resourceId = resource ;
    }

    private int selectedPosition = 0;// 选中的位置

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position) ;
        View view = null;
        ViewCMSHolder viewCMSHolder;
        if(convertView==null){
            viewCMSHolder=new ViewCMSHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);     // 得到子布局，非固定的，和子布局id有关

            viewCMSHolder.carmodelmetername = (TextView) view.findViewById(R.id.carmodelmetername);  //获取控件
            viewCMSHolder.car_model_meter_icon = view.findViewById(R.id.car_model_meter_icon) ;
            view.setTag(viewCMSHolder);

        }else{
            view=convertView;      //convertView不为空代表布局被加载过，只需要将convertView的值取出即可
            viewCMSHolder=(ViewCMSHolder) view.getTag();
        }
        viewCMSHolder.carmodelmetername.setText(name);
        Drawable drawable = App.getGlobalContext().getResources().getDrawable(R.drawable.car_model_icon) ;
        viewCMSHolder.car_model_meter_icon.setBackground(drawable);

        if (selectedPosition == position) {
            view.setBackgroundResource(R.drawable.item_seletor);
            Drawable drawable1 = App.getGlobalContext().getResources().getDrawable(R.drawable.car_model_icon_selected) ;
            viewCMSHolder.car_model_meter_icon.setBackground(drawable1);
        } else {
            view.setBackground(null);
        }

        return view;
    }

    class ViewCMSHolder {   //当布局加载过后，保存获取到的控件信息。
        TextView carmodelmetername;
        TextView car_model_meter_icon ;
    }


}
