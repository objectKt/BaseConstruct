package com.android.launcher.util;

import android.os.Environment;
import android.util.Log;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.service.UploadLogService;
import com.dc.auto.library.launcher.util.ACache;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class APKUtil {

    public static String UPLOADURL = "http://121.40.19.29:9000/car/log/uploadleftLog";
    public static String SERVERURL = "http://121.40.19.29:9000/car/carApkUpdateRecord/findAllCarUserApk" ;
    public static String DOWNLOADURL = "http://121.40.19.29/file/left.apk" ;

    public static String UPDATESTATUS = "http://121.40.19.29:9000/car/carApkUpdateRecord/updateCarApkUpdateRecordLeft" ;

    public static String UPDATELOGSTATUS = "http://121.40.19.29:9000/car/carApkUpdateRecord/updateLogStatusLeft" ;

    public static ACache aCache = ACache.get(App.getGlobalContext()) ;

    /**
     * 检查是否进行安装
     */
    public static void checkUpdate(){

        String deviceId = "";
        try {
            deviceId = CacheManager.getRightDeviceId(App.getGlobalContext());
            LogUtils.printI(APKUtil.class.getSimpleName(),"checkUpdate----deviceId="+deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final OkHttp okHttp = OkHttp.getInstance();
        String requestUrl = SERVERURL  ;
        Log.i("url==", "---" + requestUrl);
        try {
            JSONObject json = new JSONObject();
            json.put("carId",deviceId);
            json.put("location","left");

            Request request  = okHttp.doPostJsonParam(requestUrl,String.valueOf(json));

            LogUtils.printI(APKUtil.class.getSimpleName(),String.valueOf(json));
            okHttp.mOkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    int code = response.code();
                    if (code == 200) {
                        String reponseString =  response.body().string() ;
                        try {

                            JSONObject jsonObject = new JSONObject(reponseString) ;

                            if(jsonObject.has("data")){
                                JSONObject dataJson = jsonObject.getJSONObject("data") ;
                                if (dataJson!=null){
                                    JSONArray jsonArray = dataJson.getJSONArray("content")  ;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject temp = (JSONObject) jsonArray.get(i);
                                        String carId = temp.getString("carId");
                                        String leftApk = temp.getString("leftApk");
                                        String updateleftFlg = temp.getString("updateleftFlg") ;
                                        String uplog = temp.getString("uplogleft");
                                        Log.i("APK","返回内容=========22======="+carId+"===="+leftApk+"==="+updateleftFlg+"==log="+uplog) ;
                                        String carIdben = aCache.getAsString("CarNum") ;
                                        if (updateleftFlg.equals("T")){
                                            updateStatus(carId,leftApk,updateleftFlg);
                                            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.APK_DOWNLOAD_SHOW));
                                            downloadApkAndInstall();
                                        }
                                        if (uplog.equals("T")){
                                            updateLogStatus(carId);
                                            UploadLogService.startMyService(App.getGlobalContext());
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.i("APK","返回内容=========1======="+e.getMessage()) ;
                }
            }) ;


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private static void updateLogStatus(String carId) {
        final OkHttp okHttp = OkHttp.getInstance();

        Map map = new HashMap<String,String>() ;
        map.put("carId",carId);
        map.put("uplogleft","F");

        Request request =   okHttp.doPost(UPDATELOGSTATUS,map) ;
        okHttp.mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
//                Log.i("updateAPK",UPDATESTATUS+"-------2--------") ;
                if (code == 200) {
                    Log.i("updateLog",UPDATESTATUS+"--------3-------") ;
                    String reponseString =  response.body().string() ;
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("updateLog","返回内容=========1======="+e.getMessage()) ;
            }
        }) ;

    }

    private static void updateStatus(String carId, String leftApk, String updateleftFlg) {
        try {

            final OkHttp okHttp = OkHttp.getInstance();

            JSONObject json = new JSONObject();
            json.put("carId",carId) ;
            json.put("leftApk",leftApk) ;
            json.put("updateleftFlg","F") ;
            json.put("updateleftDate", FuncUtil.getCurrentDate()) ;
            Request request  = okHttp.doPostJsonParam(UPDATESTATUS,String.valueOf(json));

            Log.i("updateAPK",UPDATESTATUS+"--------1-------") ;
            okHttp.mOkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    int code = response.code();
                    Log.i("updateAPK",UPDATESTATUS+"-------2--------") ;
                    if (code == 200) {
                        Log.i("updateAPK",UPDATESTATUS+"--------3-------") ;
                        String reponseString =  response.body().string() ;
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.i("APK","返回内容=========1======="+e.getMessage()) ;
                }
            }) ;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载apk 安装
     */
    public static void downloadApkAndInstall(){


        OkHttp okHttp = OkHttp.getInstance();
        Request request = okHttp.doGet(APKUtil.DOWNLOADURL) ;
//        createNotification();
        okHttp.mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[4096];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath =  Environment.getExternalStorageDirectory().getPath()+"/left.apk" ;
                Log.i("APK",savePath+"------------------------===");
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath);
                    file.createNewFile() ;
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);

                        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.APK_DOWNLOAD_PROGRESS);
                        messageEvent.data = progress;
                        EventBus.getDefault().post(messageEvent);
                        Log.i("APK","===================下载进度"+progress) ;
                    }
                    fos.flush();
                    fos.close();

                    Thread.sleep(200);

                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.APK_INSTALL));

                } catch (Exception e) {

                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });
    }


}
