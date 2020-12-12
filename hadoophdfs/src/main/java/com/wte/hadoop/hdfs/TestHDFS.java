package com.wte.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.internal.PAData;

import java.io.*;
import java.net.URI;

public class TestHDFS {
    public Configuration conf =null;
    public FileSystem fs=null;
    @Before
    public void conn() throws Exception {
        conf=new Configuration(true);
        fs=FileSystem.get(conf);
//        <property>
//		    <name>fs.defaultFS</name>
//		    <value>hdfs://mycluster</value>
//	    </property>
        //去环境变量 HADOOP_USER_NAME ROOT
        fs=FileSystem.get(URI.create("hdfs://mycluster"),conf,"god");
    }
    @Test
    public  void test() throws IOException {
        Path dir=new Path("/god/01");
        if(fs.exists(dir)){
            fs.delete(dir,true);
        }
        fs.mkdirs(dir);
    }
    @Test
    public void mkdir() throws Exception {
        Path dir=new Path("/wte/01");
        if(fs.exists(dir)){
            fs.delete(dir,true);
        }
        fs.mkdirs(dir);
    }
    @Test
    public void upload() throws Exception {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(new File("./data/hello.txt")));
        Path path=new Path("/god/out.txt");
        FSDataOutputStream fsDataOutputStream = fs.create(path);
        IOUtils.copyBytes(input,fsDataOutputStream,conf,true);
    }
    @Test
    public void blocks() throws IOException {
        Path file = new Path("/user/god/data2.txt");
        FileStatus fss = fs.getFileStatus(file);
        BlockLocation[] blks = fs.getFileBlockLocations(fss, 0, fss.getLen());
        for (BlockLocation blk : blks) {
            System.out.println(blk);

        }
        //            0,1048576,node003,node004
        //            1048576,540319,node004,node002
        //计算向数据移动
        //其实用户和成勋读取的是文件这个级别！并不知道有块的概念
        FSDataInputStream in = fs.open(file);//
        in.seek(1048576);
        //计算向数据移动，期望的峰值，只读取自己关心，同时，具备距离的概念
        System.out.println( (char)in.readByte());
        System.out.println( (char)in.readByte());
        System.out.println( (char)in.readByte());
        System.out.println( (char)in.readByte());
        System.out.println( (char)in.readByte());
        System.out.println( (char)in.readByte());

    }
    @After
    public void close() throws Exception {
            fs.close();
    }
}
