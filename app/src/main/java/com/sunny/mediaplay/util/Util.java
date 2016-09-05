package com.sunny.mediaplay.util;

import com.sunny.mediaplay.model.Song;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by home on 2016/8/25.
 */
public class Util {
    public static Song getMediaInfo(File file ){
        Song s = new Song();
        RandomAccessFile raf = null;//随机读写方式打开MP3文件
        try {
            byte[] buf = new byte[256];
            raf = new RandomAccessFile(file, "r");
//            raf.seek(raf.length() - 128);//移动到文件MP3末尾
            raf.read(buf);//读取标签信息
            raf.close();//关闭文件

       /*     if(buf.length != 128){//数据长度是否合法
                throw new Exception("MP3标签信息数据长度不合法!");
            }*/
//            if(!"TAG".equalsIgnoreCase(new String(buf,0,3))){//标签头是否存在
//                throw new Exception("MP3标签信息数据格式不正确!");
//            }
            String SongName = new String(buf,3,30,"gbk").trim();//歌曲名称

            String Artist = new String(buf,33,30,"gbk").trim();//歌手名字

            String Album = new String(buf,63,30,"gbk").trim();//专辑名称

            String Year = new String(buf,93,4,"gbk").trim();//出品年份

            String Comment = new String(buf,97,28,"gbk").trim();//备注信息
            System.out.println(SongName+"|"+Artist+"|"+Album+"|"+Year+"|"+Comment);
            System.out.print(new String(buf,"gbk"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return s;
    }
}
