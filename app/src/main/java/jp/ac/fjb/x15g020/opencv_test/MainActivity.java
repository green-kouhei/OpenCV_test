package jp.ac.fjb.x15g020.opencv_test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    static{
        System.loadLibrary("opencv_java");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if (!OpenCVLoader.initDebug()) {
//            Log.i("OpenCV", "Failed");
//        } else {
//            Log.i("OpenCV", "successfully built !");
//        }


        try {
            copyAssets("haarascades");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CameraView cameraView = new CameraView(this,90);

        ViewGroup activityMain =(ViewGroup)findViewById(R.id.activity_main);
        activityMain.addView(cameraView);


    }

    private void copyAssets(String dir)throws IOException{
        byte[] buf = new byte[8192];
        int size;

        File dst = new File(getCacheDir(),dir);
        if(!dst.exists()){
            dst.mkdirs();
            dst.setReadable(true,false);
            dst.setWritable(true,false);
            dst.setExecutable(true,false);
        }

        for(String filename:getAssets().list(dir)){
            File file = new File(dst,filename);
            OutputStream out = new FileOutputStream(file);
            InputStream in = getAssets().open(dir+"/"+filename);
            while ((size=in.read(buf))>=0){
                if(size > 0){
                    out.write(buf,0,size);
                }
            }
            in.close();
            out.close();
            file.setReadable(true,false);
            file.setWritable(true,false);
            file.setExecutable(true,false);
        }
    }

}