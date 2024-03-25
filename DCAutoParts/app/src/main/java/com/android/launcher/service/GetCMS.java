package com.android.launcher.service;

import com.android.launcher.App;
import com.android.launcher.util.ACache;

public class GetCMS {

    public static ACache aCache = ACache.get(App.getGlobalContext()) ;
    public static int suff39Index = 0 ;

    public static String cms3e1(){

        String send3e1 = "" ;
        String canId1 = "AA000008000003E1" ;
        String send3e1s = "CDFC3F3C00FF5555" ;
        String send3e1c = "CDFC3F3C00FF0000" ;
        String send3e1m = "CDFC3F3C00FFAAAA" ;


        String  currentCms=  aCache.getAsString("CMS");
        String  lightStatus = aCache.getAsString("lightStatus");

        if(lightStatus==null){
            lightStatus= "off" ;
        }
        if(currentCms==null){
            aCache.put("CMS","S");
            currentCms = "S" ;
        }
        if (currentCms.equals("S")){
            send3e1=lightStatus.equals("on")? send3e1s.replaceFirst("C","D") :send3e1s ;
//            send74e=send74es;
//            send19f=send19fs;
        }
        if (currentCms.equals("M")){
            send3e1=lightStatus.equals("on")? send3e1m.replaceFirst("C","D") :send3e1m ;
//            send74e=send74em;
//            send19f=send19fm;
        }

        if (currentCms.equals("C")){
            send3e1=lightStatus.equals("on")? send3e1c.replaceFirst("C","D") :send3e1c  ;
//            send74e=send74ec;
//            send19f=send19fc;
        }

        return canId1+send3e1;
    }

    public static String cms19f() {

        String send19f = "" ;
        String canId3 = "AA0000080000019F" ;
        String send19fs = "00009010001765D8" ;
        String send19fc = "00009010001765D8" ;
        String send19fm = "00009010001765D8" ;


        String  currentCms=  aCache.getAsString("CMS");
        String  lightStatus = aCache.getAsString("lightStatus");

        if(lightStatus==null){
            lightStatus= "off" ;
        }
        if(currentCms==null){
            aCache.put("CMS","S");
            currentCms = "S" ;
        }
        if (currentCms.equals("S")){
//            send3e1=lightStatus.equals("on")? send3e1s.replaceFirst("C","D") :send3e1s ;
////            send74e=send74es;
            send19f=send19fs;
        }
        if (currentCms.equals("M")){
//            send3e1=lightStatus.equals("on")? send3e1m.replaceFirst("C","D") :send3e1m ;
////            send74e=send74em;
            send19f=send19fm;
        }

        if (currentCms.equals("C")){
//            send3e1=lightStatus.equals("on")? send3e1c.replaceFirst("C","D") :send3e1c  ;
////            send74e=send74ec;
            send19f=send19fc;
        }


        return canId3+send19f ;
    }

    public static String cms39f() {
        String can39F = "AA0000080000039F" ;
        String s9F = "000006020302FF" ;
        String send39F = get39F(s9F);
        String data = can39F +send39F ;

        return data+"7A";
    }

    public static int xx ;
    private static String get39F(String s9F) {

        String s1 = s9F.substring(0,2) ;

        String s2= s9F.substring(2,4) ;

        String s3 = s9F.substring(4,6) ;

        if(s1.equals("ff")) {
            s1 = "00" ;
        }

        if(s2.equals("ff")) {
            int s1int = Integer.parseInt(s1,16) ;
            s1int = s1int+1 ;

            String s1string = Integer.toHexString(s1int) ;
            if(s1string.length()<2) {
                s1string= "0"+s1string ;
            }
            s1 = s1string ;

            s2 = "00" ;
        }

        if(s3.equals("3b")) {

            int s2int = Integer.parseInt(s2,16) ;
            s2int = s2int+1 ;

            String s2string = Integer.toHexString(s2int) ;
            if(s2string.length()<2) {
                s2string= "0"+s2string ;
            }
            s2 = s2string ;
            s3 = "00" ;
            xx=0 ;
        }

        String finalstr = s1+s2+s3 ;

        int ss = Integer.parseInt(finalstr,16);
        ss= ss+1 ;

        String s = Integer.toHexString(ss) ;


        String str0 = "0000" ;


        if(s.length()==str0.length()) {

        }else {
            int sulen = str0.length()-s.length() ;

            String stri0 = str0.substring(0,sulen) ;

            s9F = stri0+s+"06020302FF" ;
        }

        xx++ ;
        return s9F;
    }

    public static String cms3D9() {
        return "AA000007000003D940BE8EBA14CCF600" ;
    }

    public static String cms3f3() {
        return "AA000008000003F3019000C4019000C4" ;
    }

    public static String cms3ed() {
        return "AA000008000003ED00FE000000000000" ;
    }

    public static String cms3f6() {
        return "AA000005000003F60200000000000000" ;
    }

    public static String cms39d() {
        String can39D = "AA0000080000039D" ;
        String send39D = "FF7F02AA000C00" ;
        String[] suff39D = new String[]{"07","8F","97","1F","A7","2F","37","BF","C7","4F","57","DF","67","EF","F7","7F"} ;

        String returndata = can39D+send39D+suff39D[suff39Index] ;

        suff39Index++ ;
        if (suff39Index>15){
            suff39Index = 0 ;
        }

        return returndata ;
    }

    public static String cms3e4() {
        return "AA000002000003E41308000000000000" ;
    }

    public static String cms1c1() {
        return "AA000004000001C10000000000000000" ;
    }

    public static String cms1c2() {
        return "AA000002000001C2143C000000000000" ;
    }

    public static String cms401() {
        return "AA00000800000401FD0A040F10000004" ;
    }

    public static String cms74e() {


        String send74e = "" ;
        String canId3 = "AA0000080000074E" ;
        String send74es = "1400500400FF0000" ;
        String send74ec = "0400500400FF0000" ;
        String send74em = "2400500400FF0000" ;


        String  currentCms=  aCache.getAsString("CMS");
        if(currentCms==null){
            aCache.put("CMS","S");
            currentCms = "S" ;
        }
        if (currentCms.equals("S")){
//            send3e1=lightStatus.equals("on")? send3e1s.replaceFirst("C","D") :send3e1s ;
            send74e=send74es;
//            send19f=send19fs;
        }
        if (currentCms.equals("M")){
//            send3e1=lightStatus.equals("on")? send3e1m.replaceFirst("C","D") :send3e1m ;
            send74e=send74em;
        }

        if (currentCms.equals("C")){
//            send3e1=lightStatus.equals("on")? send3e1c.replaceFirst("C","D") :send3e1c  ;
            send74e=send74ec;
        }


        return canId3+send74e ;

    }
}
