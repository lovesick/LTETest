package com.futureinternet.cysmile.lte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;


/**
 * Created by cysmile on 2015/6/18.
 */


public class SaveFile {

    FileOutputStream fos;
    FileInputStream fis;

    public void fileCreate(String kind) {
        String fileName = "/" + getSDPath() + "/LTE吞吐量测试 基于" + kind + ".txt";
        File filex = new File(fileName);
        try {
            filex.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 创建文件


    }

    /**
     * @param fileName
     * @param content
     */
    public void fileAppend(String kind, String content) {
        String file = getSDPath() + "/LTE吞吐量测试 基于" + kind + ".txt";
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

}

