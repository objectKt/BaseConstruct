package com.android.launcher.meter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.UiSettings;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.MapStyle;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.AMapModeCrossOverlay;
import com.amap.api.navi.view.DriveWayView;
import com.amap.api.navi.view.TrafficProgressBar;
import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.DateUtils;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.StringUtils;
import com.android.launcher.util.UnitUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.amap.api.navi.AMapNaviView.CAR_UP_MODE;

/**
 * 导航仪表
 */
public class MapNavMeterFragment extends MeterFragmentBase implements AMapNaviListener {

    public ImageView rightMeterPointer;
    public ImageView leftMeterPointer;

    //
    public ACache aCache = ACache.get(App.getGlobalContext());


    private AMapNaviView aMapNaviView;
    private AMap aMap;
    private AMapNavi mAMapNavi;
    private ImageView zoomInCrossIv;
    private TrafficProgressBar trafficProgressBar;
    private TextView nextWayTv;
    private TextView retainDistanceTv;
    private TextView kilometreTv;
    private ImageView indicateIv;
    private DriveWayView driveWayView;
    private ConstraintLayout navInfoCL;
    private AMapModeCrossOverlay aMapModeCrossOverlay;

    //是否是执行巡航
    private boolean isAimlessMode = false;

    //是否计算了路线
    private boolean isCalculateDriveRoute = false;
    private Bitmap defaultBitmap;
    private TextView surplusKmTv;
    private TextView surplusTimeTv;
    private TextView surplusKmUnitTv;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        aMapNaviView = (AMapNaviView) view.findViewById(R.id.aMapNaviView);
        zoomInCrossIv = view.findViewById(R.id.zoomInCrossIv);
        driveWayView = view.findViewById(R.id.driveWayView);
        navInfoCL = view.findViewById(R.id.navInfoCL);
        nextWayTv = (TextView) view.findViewById(R.id.nextWayTv);
        retainDistanceTv = (TextView) view.findViewById(R.id.retainDistanceTv);
        kilometreTv = (TextView) view.findViewById(R.id.kilometreTv);
        indicateIv = (ImageView) view.findViewById(R.id.indicateIv);
        trafficProgressBar = (TrafficProgressBar) view.findViewById(R.id.trafficProgressBar);
        surplusKmTv = (TextView) view.findViewById(R.id.surplusKmTv);
        surplusTimeTv = (TextView) view.findViewById(R.id.surplusTimeTv);
        surplusKmUnitTv = (TextView) view.findViewById(R.id.surplusKmUnitTv);

        trafficProgressBar.setVisibility(View.INVISIBLE);
        try {
            initNavParams(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftMeterPointer = view.findViewById(R.id.left_pointer);
        rightMeterPointer = view.findViewById(R.id.right_pointer);
        LogUtils.printI(TAG, "navEntity=" + MeterActivity.mNavEntity);


        meterLauncherAnimateFinish = true;

        defaultBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);


//        if(!isCalculateDriveRoute){
//            isCalculateDriveRoute = true;

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                if (MeterActivity.mNavEntity != null) {
                    isAimlessMode = false;
                    NaviLatLng naviLatLng = new NaviLatLng(Double.parseDouble(MeterActivity.mNavEntity.getLatitude()), Double.parseDouble(MeterActivity.mNavEntity.getLongitude()));
                    List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
                    eList.add(naviLatLng);
                    mAMapNavi.calculateDriveRoute(eList, null, MeterActivity.mNavEntity.getStrategyConvert());
                } else {
                    mAMapNavi.startAimlessMode(3);
                    isAimlessMode = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 3000);
    }


    private void initNavParams(Bundle savedInstanceState) {
        aMapNaviView.onCreate(savedInstanceState);
        try {
            mAMapNavi = AMapNavi.getInstance(getContext());
            mAMapNavi.addAMapNaviListener(this);
            //开启多路线模式
            mAMapNavi.setMultipleRouteNaviMode(true);
            mAMapNavi.setUseInnerVoice(true, true);
            mAMapNavi.setEmulatorNaviSpeed(120);
        } catch (AMapException e) {
            e.printStackTrace();
        }


        //设置车头朝向
        aMapNaviView.setNaviMode(CAR_UP_MODE);
        AMapNaviViewOptions viewOptions = aMapNaviView.getViewOptions();
        //设置6秒后是否自动锁车
        viewOptions.setAutoLockCar(true);

        //设置是否开启动态比例尺 (锁车态下自动进行地图缩放变化)
        viewOptions.setAutoChangeZoom(false);
        viewOptions.setLeaderLineEnabled(-1);
        //设置菜单按钮是否在导航界面显示
        viewOptions.setSettingMenuEnabled(false);
        //设置是否自动全览模式，即在算路成功后自动进入全览模式
        viewOptions.setAutoDisplayOverview(false);
        //设置是否自动画路
        viewOptions.setAutoDrawRoute(true);
        //设置是否显示道路信息view
        viewOptions.setLaneInfoShow(true);
        //设置是否显示路口放大图(路口模型图)
        viewOptions.setModeCrossDisplayShow(true);

        //设置锁车态下地图倾角,倾角为0时地图模式是2D模式。
        viewOptions.setTilt(40);
        //设置导航界面UI是否显示。
        viewOptions.setLayoutVisible(false);
        //设置是否显示下下个路口的转向引导，默认不显示
        viewOptions.setSecondActionVisible(true);

        //设置菜单按钮是否在导航界面显示。
        viewOptions.setSettingMenuEnabled(false);

        //设置[实时交通图层开关按钮]是否显示（只适用于驾车导航，需要联网）
        viewOptions.setTrafficLayerEnabled(true);

        //设置地图上是否显示交通路况（彩虹线）
        viewOptions.setTrafficLine(true);
        viewOptions.setRouteListButtonShow(false);

        //        //设置自车位置锁定在屏幕中的位置
        viewOptions.setPointToCenter(0.5, 0.6);

        viewOptions.setZoom(17);


        viewOptions.setMapStyle(MapStyle.AUTO, null);

        viewOptions.setAfterRouteAutoGray(true);

        viewOptions.setCarBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_nav_car));

        aMapNaviView.setViewOptions(viewOptions);

        aMapNaviView.setLazyTrafficProgressBarView(trafficProgressBar);
        aMapNaviView.setLazyDriveWayView(driveWayView);


        //初始化地图控制器对象
        if (aMap == null) {
            aMap = aMapNaviView.getMap();
        }


        //设置是否打开交通路况图层。
        aMap.setTrafficEnabled(true);
        //设置是否显示3D建筑物，默认显示。
        aMap.showBuildings(true);
        //设置是否显示室内地图，默认不显示。
        aMap.showIndoorMap(true);
        //设置在建道路图层是否显示,默认不显示
        aMap.setConstructingRoadEnable(true);

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

        aMapModeCrossOverlay = new AMapModeCrossOverlay(getContext(), aMap);
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.ON_DOWN_KEY) {
            onDown();
        } else if (event.type == MessageEvent.Type.ON_UP_KEY) {
            onUp();
        } else if (event.type == MessageEvent.Type.STOP_MAP_NAV) {
            try {
                mAMapNavi.stopNavi();
                navInfoCL.setVisibility(View.INVISIBLE);
                trafficProgressBar.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    }

    @Override
    public void updateWaterTemp(int temp) {

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
    protected void onDown() {
        AMapNaviViewOptions viewOptions = aMapNaviView.getViewOptions();
        int zoom = viewOptions.getZoom();
        LogUtils.printI(TAG, "onDown--zoom=" + zoom);
        if (zoom > 14) {
            viewOptions.setZoom(--zoom);
            aMapNaviView.setViewOptions(viewOptions);
        }

    }

    @Override
    protected void onUp() {
        AMapNaviViewOptions viewOptions = aMapNaviView.getViewOptions();
        int zoom = viewOptions.getZoom();
        LogUtils.printI(TAG, "onUp--zoom=" + zoom);
        if (zoom < 18) {
            viewOptions.setZoom(++zoom);
            aMapNaviView.setViewOptions(viewOptions);
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_meter_map_nav;
    }


    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.MAP;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            MeterActivity activity = (MeterActivity) getActivity();
            activity.bottomStatusView.showWaterTempLineView(true);
            activity.bottomStatusView.showOilLineView(true);
            activity.speedCenterView.setVisibility(View.VISIBLE);
        }
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        aMapNaviView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        aMapNaviView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        aMapNaviView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (isAimlessMode) {
                try {
                    mAMapNavi.stopAimlessMode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mAMapNavi.stopNavi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mAMapNavi.removeAMapNaviListener(this);

            aMapNaviView.onDestroy();
            AMapNavi.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int type) {
        LogUtils.printI(TAG, "onStartNavi---开始导航--type=" + type);
        if (trafficProgressBar != null) {
            if (isAimlessMode) {
                trafficProgressBar.setVisibility(View.INVISIBLE);
            } else {
                trafficProgressBar.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
//        LogUtils.printI(TAG, "onLocationChange---aMapNaviLocation="+aMapNaviLocation +", isCalculateDriveRoute="+isCalculateDriveRoute);
        //当位置信息有更新时的回调函数。
        if (aMapNaviLocation != null) {
            NaviLatLng coord = aMapNaviLocation.getCoord();
            if (coord != null && coord.getLatitude() > 0.0 && coord.getLongitude() > 0.0) {
//
//                if(!isCalculateDriveRoute){
//                    isCalculateDriveRoute = true;
//
//                    if (MeterActivity.mNavEntity != null) {
//                        NaviLatLng naviLatLng = new NaviLatLng(Double.parseDouble(MeterActivity.mNavEntity.getLatitude()), Double.parseDouble(MeterActivity.mNavEntity.getLongitude()));
//                        List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
//                        eList.add(naviLatLng);
//                        mAMapNavi.calculateDriveRoute(eList, null, MeterActivity.mNavEntity.getStrategyConvert());
//                    } else {
//                        int strategy = 0;
//                        try {
//                            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
//                            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
//                        mAMapNavi.calculateDriveRoute(eList, null, strategy);
//                    }
//                }
            }
        }
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //导航播报信息回调函数。
        LogUtils.printI(TAG, "onGetNavigationText---type=" + type + ", text=" + text);
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        LogUtils.printI(TAG, "到达目的地");
        try {
            mAMapNavi.stopNavi();
            navInfoCL.setVisibility(View.INVISIBLE);
            trafficProgressBar.setVisibility(View.INVISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(() -> EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.STOP_MAP_NAV)), 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //用户手机位置信息设置是否开启的回调函数。
        LogUtils.printI(TAG, "onGpsOpenStatus---GPS开启：" + enabled);
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        try {
            setNaviInfo(naviInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setNaviInfo(NaviInfo naviInfo) {
        //导航引导信息回调。
        int allLength = mAMapNavi.getNaviPath().getAllLength();
        List<AMapTrafficStatus> trafficStatuses = mAMapNavi.getTrafficStatuses(0, 0);
        trafficProgressBar.update(allLength, naviInfo.getPathRetainDistance(), trafficStatuses);

        navInfoCL.setVisibility(View.VISIBLE);
        nextWayTv.setText(StringUtils.filterNULL(naviInfo.getNextRoadName()));

        int curStepRetainDistance = naviInfo.getCurStepRetainDistance();
        if (curStepRetainDistance < 1000) {
            retainDistanceTv.setText(String.valueOf(curStepRetainDistance));
            kilometreTv.setText(getResources().getString(R.string.m));
        } else {
            float distance = UnitUtils.mToKilometre(curStepRetainDistance);
            retainDistanceTv.setText(String.valueOf(distance));
            kilometreTv.setText(getResources().getString(R.string.kilometre));
        }

        //获取路线剩余距离
        int pathRetainDistance = naviInfo.getPathRetainDistance();
        if (pathRetainDistance < 1000) {
            surplusKmTv.setText(String.valueOf(pathRetainDistance));
            surplusKmUnitTv.setText(getResources().getString(R.string.m));
        } else {
            float distance = UnitUtils.mToKilometre(pathRetainDistance);
            surplusKmTv.setText(String.valueOf(distance));
            surplusKmUnitTv.setText(getResources().getString(R.string.kilometre));
        }

        int pathRetainTime = naviInfo.getPathRetainTime();
        String mileHour = DateUtils.getMileHourStyle1(getContext(), pathRetainTime * 1000);
        surplusTimeTv.setText(mileHour);

        Bitmap iconBitmap = naviInfo.getIconBitmap();
        int iconType = naviInfo.getIconType();

        //https://a.amap.com/lbs/static/unzip/Android_Navi_Doc/index.html
//        LogUtils.printI(TAG, "iconType="+iconType);
        if (iconBitmap != null) {
            indicateIv.setImageBitmap(iconBitmap);
        } else {
            indicateIv.setImageBitmap(defaultBitmap);
        }
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        LogUtils.printI(TAG, "显示路口放大图回调(实景图)");
        zoomInCrossIv.setVisibility(View.VISIBLE);
        zoomInCrossIv.setImageBitmap(aMapNaviCross.getBitmap());
    }

    @Override
    public void hideCross() {
        LogUtils.printI(TAG, "关闭路口放大图回调(实景图)");
        zoomInCrossIv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {
        //显示路口放大图回调(模型图)。 注意：该接口仅驾车模式有效
        LogUtils.printI(TAG, "显示路口放大图回调(模型图)");

        byte[] picBuf1 = aMapModelCross.getPicBuf1();
        zoomInCrossIv.setImageBitmap(BitmapFactory.decodeByteArray(picBuf1, 0, picBuf1.length));
        aMapModeCrossOverlay.createModelCrossBitMap(picBuf1, (bitmap, status) -> {
            //status - -1失败 0成功
            LogUtils.printI(TAG, "status=" + status);
            if (zoomInCrossIv != null) {
                zoomInCrossIv.setVisibility(View.VISIBLE);
                zoomInCrossIv.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void hideModeCross() {
        //关闭路口放大图回调(模型图)。
        LogUtils.printI(TAG, "关闭路口放大图回调(模型图)");
        zoomInCrossIv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
        //显示道路信息回调。 注意：该接口仅驾车模式有效
        //aMapLaneInfo - 道路信息，可获得当前道路信息，可用于用户使用自己的素材完全自定义显示。
        LogUtils.printI(TAG, "showLaneInfo---显示道路信息回调--" + aMapLaneInfo);
        driveWayView.loadDriveWayBitmap(aMapLaneInfo);
        driveWayView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLaneInfo() {
        driveWayView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        // 获取路线数据对象
        try {
            HashMap<Integer, AMapNaviPath> naviPaths = mAMapNavi.getNaviPaths();
            Set<Map.Entry<Integer, AMapNaviPath>> entries = naviPaths.entrySet();
            Iterator<Map.Entry<Integer, AMapNaviPath>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, AMapNaviPath> pathEntry = iterator.next();
                Integer routeId = pathEntry.getKey();
                AMapNaviPath mapNaviPath = pathEntry.getValue();


                LogUtils.printI(TAG, "onCalculateRouteSuccess---routeId=" + routeId + ", 总长度=" + mapNaviPath.getAllLength() + ", 所需时间=" + mapNaviPath.getAllTime());

            }

            if (MeterActivity.mNavEntity != null) {
                //使用pathid进行路线切换
                mAMapNavi.selectMainPathID(MeterActivity.mNavEntity.getSelectRouteId());

            }
            mAMapNavi.startNavi(NaviType.GPS);
//            mAMapNavi.startNavi(NaviType.EMULATOR);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
        LogUtils.printI(TAG, "onCalculateRouteFailure---路线计算失败");
        if (aMapCalcRouteResult != null) {
            LogUtils.printI(TAG, "onCalculateRouteFailure---" + aMapCalcRouteResult.getErrorCode() + ", " + aMapCalcRouteResult.getErrorDetail());
        }

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {
        //导航过程中道路信息通知 注意：该接口仅驾车模式有效
        //导航过程中针对拥堵区域、限行区域、禁行区域、道路封闭等情况的躲避通知。
    }

    @Override
    public void onGpsSignalWeak(boolean isWeak) {
        LogUtils.printI(TAG, "手机卫星定位信号弱：" + isWeak);
    }


}
