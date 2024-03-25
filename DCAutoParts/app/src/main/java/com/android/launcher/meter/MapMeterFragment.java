package com.android.launcher.meter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.entity.NavEntity;
import com.android.launcher.meter.map.MapAfterRecoveryFrag;
import com.android.launcher.meter.map.MapAttentionAidSysFrag;
import com.android.launcher.meter.map.MapFMFrag;
import com.android.launcher.meter.map.MapHomeFrag;
import com.android.launcher.meter.map.MapLauncherAfterFrag;
import com.android.launcher.meter.map.MapMediaFrag;
import com.android.launcher.meter.map.MapSpeedCalibrationFrag;
import com.android.launcher.meter.view.OilMeterView;
import com.android.launcher.meter.view.WaterTempMeterView;
import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.ACache;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import module.map.overlay.DrivingRouteOverlay;

/**
 * 地图仪表
 *
 * @date： 2023/11/29
 * @author: 78495
 */
public class MapMeterFragment extends MeterFragmentBase implements AMap.OnMyLocationChangeListener,
        RouteSearch.OnRouteSearchListener {

    // 仪表
    public WaterTempMeterView watertempMeterView;

    public ImageView waterTempIV;


    public ImageView rightMeterPointer;
    public ImageView leftMeterPointer;

    //
    public ACache aCache = ACache.get(App.getGlobalContext());


    public String startEngA = "0.0", endEngA = "0.0";

    public OilMeterView oilMeterView;

    public ArrayList<String> tempArr = new ArrayList<>();

    public long ll;
    public String startA = "0.0", endA = "0.0";
    private TextView distanceToEmptyTV;
    private TextView distanceToEmptyUnitTV;

    private MapView mMapView;
    private AMap aMap;
    private RouteSearch routeSearch;
    private LatLonPoint fromPoint;
    private NavEntity lastNavEntity = new NavEntity("0.0","0.0",0,0);


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mMapView = (MapView) view.findViewById(R.id.aMapView);

        try {
            initNavParams(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftMeterPointer = view.findViewById(R.id.left_pointer);
        rightMeterPointer = view.findViewById(R.id.right_pointer);
        watertempMeterView = view.findViewById(R.id.watertempMeterView);
        waterTempIV = view.findViewById(R.id.waterTempIV);

        distanceToEmptyTV = view.findViewById(R.id.distanceToEmptyTV);
        distanceToEmptyUnitTV = view.findViewById(R.id.distanceToEmptyUnitTV);

        oilMeterView = view.findViewById(R.id.oilMeterView);
        if (MeterActivity.unitType == UnitType.KM.ordinal()) {
            distanceToEmptyUnitTV.setText("km");
        } else {
            distanceToEmptyUnitTV.setText("mp");
        }

        oilMeterView.setProgress(LivingService.currentPercentOil);
        watertempMeterView.setProgress(LivingService.currentWaterTemp);

        LogUtils.printI(TAG, "navEntity=" + MeterActivity.mNavEntity);

        try {
            routeSearch = new RouteSearch(getActivity());
            routeSearch.setRouteSearchListener(this);

            fromPoint = new LatLonPoint(0, 0);
        } catch (AMapException e) {
            e.printStackTrace();
        }

        meterLauncherAnimateFinish = true;
    }


    private void initNavParams(Bundle savedInstanceState) throws Exception {
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//
////        myLocationStyle.interval(MyLocationStyle.LOCATION_TYPE_FOLLOW); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
//        ////设置定位蓝点精度圆圈的边框颜色的方法
        myLocationStyle.strokeColor(Color.TRANSPARENT);
//        //设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
//
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式
//
//
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
////aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //设置是否打开交通路况图层。
        aMap.setTrafficEnabled(true);
        //设置是否显示3D建筑物，默认显示。
        aMap.showBuildings(true);
        //设置是否显示室内地图，默认不显示。
        aMap.showIndoorMap(true);
        //设置在建道路图层是否显示,默认不显示
        aMap.setConstructingRoadEnable(true);


        if (MeterActivity.isNightMode) {
            aMap.setMapType(AMap.MAP_TYPE_NIGHT);
//            aMap.setMapType(AMap.MAP_TYPE_NAVI_NIGHT);
        } else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
//            aMap.setMapType(AMap.MAP_TYPE_NAVI);
        }
        //设置地图模式为导航模式
//        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        //卫星地图
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);

        UiSettings uiSettings = aMap.getUiSettings();

        //设置缩放按钮是否可见。
        uiSettings.setZoomControlsEnabled(false);
        //设置双指缩放手势是否可用。
        uiSettings.setZoomGesturesEnabled(false);
        //设置拖拽手势是否可用。
        uiSettings.setScrollGesturesEnabled(false);
        //设置比例尺控件是否可见
        uiSettings.setScaleControlsEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        //设置定位按钮是否可见。
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setLogoBottomMargin(-DensityUtil.dip2px(getContext(), 60));
        //设置指南针是否可见。
        uiSettings.setCompassEnabled(false);
        uiSettings.setAllGesturesEnabled(false);

        aMap.setOnMyLocationChangeListener(this);

        CameraPosition build = new CameraPosition.Builder()
                .target(new LatLng(0f,0f))
                .zoom(20) //最大缩放20
                .bearing(0) //可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。
                .tilt(90) //地图倾斜角度， 最大90
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
        aMap.moveCamera(cameraUpdate);



        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        aMap.setMinZoomLevel(20);

    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.UPDATE_RUNNING_DISTANCE) {
            if (distanceToEmptyTV != null) {
                distanceToEmptyTV.setText(String.valueOf((int) LivingService.distance));
            }
        } else if (event.type == MessageEvent.Type.ON_DOWN_KEY) {
            onDown();
        } else if (event.type == MessageEvent.Type.ON_UP_KEY) {
            onUp();
        }
//        else if (event.type == MessageEvent.Type.STOP_MAP_NAV) {
////            try {
////                if (aMap != null) {
////                    aMap.clear();
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//        }
    }


    @Override
    protected List<Fragment> getMenuFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new MapHomeFrag());
//        fragments.add(new MapLauncherAfterFrag());
//        fragments.add(new MapAfterRecoveryFrag());
//        fragments.add(new MapAttentionAidSysFrag());
//        fragments.add(new MapMediaFrag());
//        fragments.add(new MapFMFrag());
//        fragments.add(new MapSpeedCalibrationFrag());
        return fragments;
    }

    @Override
    protected void updateOilProgress(float percent) {
        oilMeterView.setProgress(percent);
    }

    @Override
    public void updateWaterTemp(int temp) {
        if (temp < 50) {
            temp = 51;
        }
        if (watertempMeterView != null) {
            watertempMeterView.setProgress(temp);
            if (temp > 110) {
                waterTempIV.setImageResource(R.drawable.tmp_alert);
            } else {
                waterTempIV.setImageResource(R.drawable.tmp);
            }
        }
    }

    @Override
    public void updateEngineSpeed(int engineSpeed) {

    }

    @Override
    public void startMeterAnimation() {

    }

    @Override
    public void updateCarSpeed(String speedData) {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_meter_map;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            MeterActivity activity = (MeterActivity) getActivity();
            activity.bottomStatusView.showWaterTempLineView(false);
            activity.bottomStatusView.showOilLineView(false);
            activity.speedCenterView.setVisibility(View.VISIBLE);
        }
        if (mMapView != null) {
            //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
            mMapView.onResume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
            mMapView.onPause();
        }

    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mMapView.onSaveInstanceState(outState);
//    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (mMapView != null) {
            //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
            mMapView.onDestroy();
        }

        try {
            aMap.removeOnMyLocationChangeListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.MAP;
    }

    @Override
    public void onMyLocationChange(Location location) {

//        if (fromPoint.getLatitude() == location.getLatitude() && fromPoint.getLongitude() == location.getLongitude()) {
//            return;
//        }
//        LogUtils.printI(TAG, "onMyLocationChange---location=" + location);
//        fromPoint.setLatitude(location.getLatitude());
//        fromPoint.setLongitude(location.getLongitude());


//        CameraPosition build = new CameraPosition.Builder()
//                .target(new LatLng(location.getLatitude(), location.getLongitude()))
//                .zoom(20) //最大缩放20
//                .bearing(0) //可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。
//                .tilt(90) //地图倾斜角度， 最大90
//                .build();
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
//        aMap.moveCamera(cameraUpdate);

        if (location != null) {
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                /*
                errorCode
                errorInfo
                locationType
                */
                LogUtils.printI(TAG, "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
            } else {
                LogUtils.printI(TAG, "定位信息， bundle is null ");

            }

        } else {
            LogUtils.printI(TAG, "定位失败");
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int rCode) {
        LogUtils.printI(TAG, "onDriveRouteSearched---rCode=" + rCode);
        if (driveRouteResult == null || MeterActivity.mNavEntity == null) {
            return;
        }
        try {
            aMap.clear();// 清理地图上的所有覆盖物
            List<DrivePath> paths = driveRouteResult.getPaths();
            if (paths != null && !paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    DrivePath drivePath = paths.get(i);
                    LogUtils.printI(TAG, "drivePathV2=" + drivePath.getStrategy() + ", 总长度=" + drivePath.getTollDistance() / 1000 + ", 红绿灯个数=" + drivePath.getTotalTrafficlights());


                }
                DrivePath drivePath = driveRouteResult.getPaths()
                        .get(0);
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        getContext(), aMap, drivePath,
                        driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos(), null);
                drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResultV2, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResultV2, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResultV2, int i) {

    }
}
