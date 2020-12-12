package com.wte.hadoop.hdfs;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        System.out.println(sb2(new BigDecimal("10.0")));
    }
    public static  String sb2(Object obj){
        if(obj==null)return "0";
        BigDecimal dig=new BigDecimal(String.valueOf(obj));
        long sb2 = dig.setScale(0, BigDecimal.ROUND_DOWN).longValue();
        return String.valueOf(sb2);
    }















    public static  String sb(Object obj){
        if(obj instanceof  Double){
            Double dl=Double.valueOf(String.valueOf(obj));
            int floor =(int) Math.floor(dl);
            System.out.println("Double");
            return String.valueOf(floor);
        }else if(obj instanceof Float){
            Float fla=Float.valueOf(String.valueOf(obj));
            int floor =(int) Math.floor(fla);
            System.out.println("fla");
            return String.valueOf(floor);
        }else if(obj instanceof BigDecimal) {
            BigDecimal dig=(BigDecimal)obj;
            long tt2 = dig.setScale(0, BigDecimal.ROUND_DOWN).longValue();
            System.out.println("BigDecimal");
            return String.valueOf(tt2);
        } else  {
            System.out.println("other");
            return String.valueOf(obj);
        }
    }
}
