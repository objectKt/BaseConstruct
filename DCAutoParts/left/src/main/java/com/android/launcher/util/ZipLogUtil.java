package com.android.launcher.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;


public class ZipLogUtil {


    public static void logZip() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
        String zipPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Music" + File.separator + "left.zip";
        File file = new File(zipPath);
        if (file.exists()) {
            file.delete();
        }
        ZipFolder(path, zipPath);
        File file1 = new File(zipPath);
        try {
//            upload(APKUtil.UPLOADURL,file1);
            //newUpload(APKUtil.UPLOADURL, file1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printI(ZipLogUtil.class.getSimpleName(), "logZip----Exception=" + e.getMessage());
        }
    }

//    private static void newUpload(String url, File file){
//
//        LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder().setLevel(Level.BASIC)
//                .log(Platform.WARN).
//                        tag("LoggingInterceptor").build();
//
//        //client
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(180, TimeUnit.SECONDS) // 设置连接超时时间为120秒
//                .writeTimeout(180, TimeUnit.SECONDS)
//                .readTimeout(180, TimeUnit.SECONDS) // 设置连接超时时间为120秒
//                //整个调用时期的超时时间，包括解析DNS、链接、写入请求体、服务端处理、以及读取响应结果
//                .callTimeout(360, TimeUnit.SECONDS)
//                .addInterceptor(loggingInterceptor)
//                .build();
//        Request.Builder builder = new Request.Builder();
//        builder.url(url);
//
//        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
//        bodyBuilder.setType(MultipartBody.FORM);
//        bodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//        MultipartBody body = bodyBuilder.build();
//
//        RequestBody requestBody = ProgressHelper.withProgress(body, new ProgressUIListener() {
//
//            @Override
//            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
//                LogUtils.printI(ZipLogUtil.class.getSimpleName(), "onUIProgressChanged---numBytes=" + numBytes + ", totalBytes=" + (float)(totalBytes /1024f / 1024f) + "M, percent=" + 100 * percent + ", speed=" + speed);
//            }
//
//            @Override
//            public void onUIProgressFinish() {
//                super.onUIProgressFinish();
//                LogUtils.printI(ZipLogUtil.class.getSimpleName(), "上传结束");
//            }
//
//        });
//        builder.header("Authorization", "ClientID" + UUID.randomUUID());
//        builder.post(requestBody);
//        Call call = okHttpClient.newCall(builder.build());
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                LogUtils.printI(ZipLogUtil.class.getSimpleName(), "上传失败---Exception=" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                LogUtils.printI(ZipLogUtil.class.getSimpleName(), "上传成功---response=" + response.headers());
//
//            }
//        });
//    }


    /**
     * @param url  服务器地址
     * @param file 所要上传的文件
     * @return 响应结果
     * @throws IOException
     */
    public static ResponseBody upload(String url, File file) throws Exception {
        LogUtils.printI(ZipLogUtil.class.getSimpleName(), "upload----file=" + file.getAbsolutePath());
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS) // 设置连接超时时间为120秒
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS) // 设置连接超时时间为120秒
                //整个调用时期的超时时间，包括解析DNS、链接、写入请求体、服务端处理、以及读取响应结果
                .callTimeout(360, TimeUnit.SECONDS).build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        LogUtils.printI(ZipLogUtil.class.getSimpleName(), "upload----response=" + response.toString());
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body();
    }


    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 压缩完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString) {

        try {
            //创建ZIP
            ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
            //创建文件
            File file = new File(srcFileString);
            //压缩
            LogUtils.printI(ZipLogUtil.class.getSimpleName(), "---->" + file.getParent() + "===" + file.getAbsolutePath());
            ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
            //完成和关闭
            outZip.finish();
            outZip.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printE(ZipLogUtil.class.getSimpleName(), "Exception="+e.getMessage());
        }
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {

        if (zipOutputSteam == null) {
            return;
        }
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            LogUtils.printI(ZipLogUtil.class.getSimpleName(), "ZipFiles-----file:" + file.getAbsolutePath());
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[1024 * 10];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            //文件夹
            String fileList[] = file.list();
            //没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            //子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString + fileString + "/", fileList[i], zipOutputSteam);
            }
        }
    }


}