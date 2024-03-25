package test;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.launcher.can.Can105;
import com.android.launcher.can.Can139;
import com.android.launcher.can.Can203;
import com.android.launcher.can.Can2f3;
import com.android.launcher.can.Can2ff;
import com.android.launcher.can.Can30d;
import com.android.launcher.can.Can378;
import com.android.launcher.can.Can380;
import com.android.launcher.can.Canf8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CanDataTest {

    //发动机转速
    private static Can105 can105 = new Can105();
    //水温表
    private static Can30d can30d = new Can30d();
    //车速
    private static Can203 can203 = new Can203();

    //机油液位
    private static Can380 can380 = new Can380();

    //油表数据c
    private static Canf8 canf8 = new Canf8();

    //车门， 车身温度，ESP
    private static Can139 can139 = new Can139();

    //胎压
    private static Can2ff can2ff = new Can2ff();

    //档位
    private static Can2f3 can2f3 = new Can2f3();

    private static Can378 can378 = new Can378();

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);

    public static void start(){
//        startCan30d();
        startCan203();
//        starCan380();

//        starCan378();
    }

    private static void starCan378() {
        //00 00 00 00 51 00 00 00 等待设定,没有开启的状态
        //00 3C 00 00 51 3C 00 00 没有开启的状态
        //00 5B 00 00 45 5B 00 00 正在定速巡航
        //00 5A 00 00 51 5A 00 00 取消定速巡航
        //80 3c 00 00 45 46 00 00 正在开启
        //00 46 00 00 45 46 00 00  开启状态, 两个46为车速
        //[378, 8, 80, 25, 00, 00, 63, 25, 00, 00] //63等待设定

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//
                List<String> list;



                //等待设定,没有开启的状态
                list = parse("378, 8,00,00,00, 00, 51, 00, 00, 00");
                can378.handlerCan(list);
//
//
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //63等待设定
                list = parse("378, 8, 80, 25, 00, 00, 63, 25, 00, 00");
                can378.handlerCan(list);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //                //正在开启
                list = parse("378, 8,80,3c,00,00,45,46,00,00");
                can378.handlerCan(list);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //开启状态, 两个46为车速
                list = parse("378, 8, 00, 46, 00, 00, 45, 46, 00, 00");
                can378.handlerCan(list);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //正在定速巡航
                list = parse("378, 8, 00, 5B, 00, 00, 45, 5B, 00, 00");
                can378.handlerCan(list);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //取消定速巡航
                list = parse("378, 8, 00, 5A, 00, 00, 51, 5A, 00, 00");
                can378.handlerCan(list);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //没有开启的状态
                list = parse("378, 8, 00, 3C, 00, 00, 51, 3C, 00, 00");
                can378.handlerCan(list);
            }
        }).start();
    }

    private static void starCan380() {
        new Thread(() -> {
            List<String> list = parse("380, 8, 05, 13, 03, 06, 01, 0F, 38, 4D");
            can380.handlerCan(list);
        }).start();
    }

    private static void startCan203() {

        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                List<String> list = parse("380, 8, 43, 0A, 40, E4, C0, DD, C0, DD");
                can203.handlerCan(list);
            }
        },5000,200,TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<String> list = parse("203, 8, 61, DD, 40, E4, C0, DD, C0, DD");
                can203.handlerCan(list);
            }
        },3000,100,TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<String> list = parse("203, 8, 62, DD, 40, E4, C0, DD, C0, DD");
                can203.handlerCan(list);
            }
        },3000,300,TimeUnit.MILLISECONDS);
    }

    private static void startCan30d() {
        executorService.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<String> list = parse("30d, 8, 87, 8D, 89, 7F, 3C, 02, E8, 7B");
                can30d.handlerCan(list);
            }
        },3000,200, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<String> list = parse("30d, 8, 75, 5E, 67, 53, 3D, 0A, 51, 7C");
                can30d.handlerCan(list);
            }
        },3000,200, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<String> list = parse("30d, 8, 57, 52, 4E, 45, 3D, 03, 79, 7B");
                can30d.handlerCan(list);
            }
        },3000,200, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<String> list = parse("30d, 8, 57, 52, 4E, 45, 3D, 03, 79, 7B");
                can30d.handlerCan(list);
            }
        },3000,200, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                List<String> list = parse("30d, 8, 61, 54, 54, 4B, 3D, 00, 00, 7B");
                can30d.handlerCan(list);
            }
        },3000,200, TimeUnit.MILLISECONDS);
    }


    public static List<String> parse(String data){
        data = data.replaceAll(" ", "");
        String[] split = data.split(",");
        List<String> list = new ArrayList<>();
        for (int i=0; i<split.length; i++){
            list.add(split[i]);
        }
        return list;
    }
}
