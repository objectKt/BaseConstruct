package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;

import dc.library.utils.event.MessageEvent;

import com.android.launcher.meter.MenuType;


/**
 * @description: 地图导航
 * @createDate: 2023/9/21
 */
public abstract class MapFragmentBase extends MenuFragmentBase{
//        implements AMapNaviListener {
//
//    public AMapNaviView aMapNaviView;
//    private AMapNavi mAMapNavi;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        aMapNaviView = (AMapNaviView) view.findViewById(R.id.aMapNaviView);

//        initNavParams(savedInstanceState);
    }


    @Override
    protected void initData() {
//        try {
//            mAMapNavi = AMapNavi.getInstance(getContext());
//            mAMapNavi.addAMapNaviListener(this);
////            //开启多路线模式
//            mAMapNavi.setMultipleRouteNaviMode(true);
////            mAMapNavi.setEmulatorNaviSpeed(60);
//            mAMapNavi.setUseInnerVoice(false,false);
//        } catch (AMapException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.MAP;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.TO_NAV_MENU) {
            initData();
        }else if(event.type == MessageEvent.Type.STOP_MAP_NAV){
//            try {
//                try {
//                    if(mAMapNavi!=null){
//                        mAMapNavi.stopNavi();
//                        mAMapNavi.removeAMapNaviListener(this);
//                        AMapNavi.destroy();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }

    private void initNavParams(Bundle savedInstanceState) {

//        //设置车头朝向
//        aMapNaviView.setNaviMode(CAR_UP_MODE);
//        //设置导航页面显示模式 1-锁车态 2-全览态 3-普通态
//        aMapNaviView.setShowMode(1);
//        AMapNaviViewOptions viewOptions = aMapNaviView.getViewOptions();
//        //设置6秒后是否自动锁车
//        viewOptions.setAutoLockCar(true);
//
//        //设置是否开启动态比例尺 (锁车态下自动进行地图缩放变化)
//        viewOptions.setAutoChangeZoom(true);
//        viewOptions.setLeaderLineEnabled(-1);
//        //设置菜单按钮是否在导航界面显示
//        viewOptions.setSettingMenuEnabled(false);
//        //设置是否自动全览模式，即在算路成功后自动进入全览模式
//        viewOptions.setAutoDisplayOverview(false);
//        //设置是否自动画路
//        viewOptions.setAutoDrawRoute(true);
//        //设置是否显示道路信息view
//        viewOptions.setLaneInfoShow(true);
//        //设置是否显示路口放大图(路口模型图)
//        viewOptions.setModeCrossDisplayShow(false);
//
//        //设置锁车态下地图倾角,倾角为0时地图模式是2D模式。取值范围[0-60]
//        viewOptions.setTilt(40);
//        //设置导航界面UI是否显示。
//        viewOptions.setLayoutVisible(false);
//        //设置是否显示下下个路口的转向引导，默认不显示
//        viewOptions.setSecondActionVisible(false);
//
//        //设置菜单按钮是否在导航界面显示。
//        viewOptions.setSettingMenuEnabled(false);
//
//        //设置[实时交通图层开关按钮]是否显示（只适用于驾车导航，需要联网）
//        viewOptions.setTrafficLayerEnabled(false);
//
//        //设置锁车下地图缩放等级（仅在关闭了动态比例尺下生效） 取值范围[14-18]
//        viewOptions.setZoom(16);
//
//        //设置地图上是否显示交通路况（彩虹线）
//        viewOptions.setTrafficLine(true);
//
//        AMap map = aMapNaviView.getMap();
//        UiSettings uiSettings = map.getUiSettings();
//        //设置logo不可见
////        uiSettings.setLogoPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
////        uiSettings.setLogoBottomMargin(-500);
//        //设置指南针不可见
//        uiSettings.setCompassEnabled(false);
//        //设置比例尺控件是否可见
//        uiSettings.setScaleControlsEnabled(false);
//        //设置缩放按钮是否可见
//        uiSettings.setZoomControlsEnabled(false);
//
//
//        aMapNaviView.onCreate(savedInstanceState);
//
//        //设置自车位置锁定在屏幕中的位置
//        viewOptions.setPointToCenter(0.5, 0.5);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        try {
//            //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//            aMapNaviView.onDestroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            mAMapNavi.stopNavi();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            mAMapNavi.removeAMapNaviListener(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        aMapNaviView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        aMapNaviView.onPause();
    }


//    @Override
//    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
//        try {
//            HashMap<Integer, AMapNaviPath> naviPaths = mAMapNavi.getNaviPaths();
//            Set<Map.Entry<Integer, AMapNaviPath>> entries = naviPaths.entrySet();
//            Iterator<Map.Entry<Integer, AMapNaviPath>> iterator = entries.iterator();
//
//
//            while (iterator.hasNext()) {
//                Map.Entry<Integer, AMapNaviPath> pathEntry = iterator.next();
//                Integer routeId = pathEntry.getKey();
//                if (MeterActivity.mNavEntity != null) {
//                    if (routeId == MeterActivity.mNavEntity.getSelectRouteId()) {
//                        mAMapNavi.selectRouteId(routeId);
//                        mAMapNavi.startNavi(NaviType.GPS);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
//
//    }
//
//    @Override
//    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {
//
//    }
//
//    @Override
//    public void onGpsSignalWeak(boolean b) {
//
//    }
//
//    @Override
//    public void onInitNaviFailure() {
//        LogUtils.printI(TAG,"onInitNaviFailure----");
//    }
//
//    @Override
//    public void onInitNaviSuccess() {
//        LogUtils.printI(TAG,"onInitNaviSuccess----");
//        if (MeterActivity.mNavEntity == null) {
//            //实时导航
//            //            mAMapNavi.calculateDriveRoute(eList, null, strategyConvert);
//        } else {
//            LogUtils.printI(TAG, "navEntity=" + MeterActivity.mNavEntity);
//            NaviLatLng naviLatLng = new NaviLatLng(Double.parseDouble(MeterActivity.mNavEntity.getLatitude()), Double.parseDouble(MeterActivity.mNavEntity.getLongitude()));
//
//            List<NaviLatLng> list = new ArrayList<>();
//            list.add(naviLatLng);
//            mAMapNavi.calculateDriveRoute(list, null, MeterActivity.mNavEntity.getStrategyConvert());
//        }
//    }
//
//    @Override
//    public void onStartNavi(int i) {
//
//    }
//
//    @Override
//    public void onTrafficStatusUpdate() {
//
//    }
//
//    @Override
//    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
//
//    }
//
//    @Override
//    public void onGetNavigationText(int i, String s) {
//
//    }
//
//    @Override
//    public void onGetNavigationText(String s) {
//
//    }
//
//    @Override
//    public void onEndEmulatorNavi() {
//
//    }
//
//    @Override
//    public void onArriveDestination() {
//
//    }
//
//    @Override
//    public void onCalculateRouteFailure(int i) {
//
//    }
//
//    @Override
//    public void onReCalculateRouteForYaw() {
//
//    }
//
//    @Override
//    public void onReCalculateRouteForTrafficJam() {
//
//    }
//
//    @Override
//    public void onArrivedWayPoint(int i) {
//
//    }
//
//    @Override
//    public void onGpsOpenStatus(boolean b) {
//
//    }
//
//    @Override
//    public void onNaviInfoUpdate(NaviInfo naviInfo) {
//
//    }
//
//    @Override
//    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
//
//    }
//
//    @Override
//    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {
//
//    }
//
//    @Override
//    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
//
//    }
//
//    @Override
//    public void showCross(AMapNaviCross aMapNaviCross) {
//
//    }
//
//    @Override
//    public void hideCross() {
//
//    }
//
//    @Override
//    public void showModeCross(AMapModelCross aMapModelCross) {
//
//    }
//
//    @Override
//    public void hideModeCross() {
//
//    }
//
//    @Override
//    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
//
//    }
//
//    @Override
//    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
//
//    }
//
//    @Override
//    public void hideLaneInfo() {
//
//    }
//
//    @Override
//    public void onCalculateRouteSuccess(int[] ints) {
//
//    }
//
//    @Override
//    public void notifyParallelRoad(int i) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//
//    }
//
//    @Override
//    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
//
//    }
//
//    @Override
//    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
//
//    }
//
//    @Override
//    public void onPlayRing(int i) {
//
//    }

}
