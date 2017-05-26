package br.com.luan2.baseapp.extras;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;



/**
 * Created by Luan on 04/05/17.
 */

public class Utils {

    public Utils(View contentView) {
        this.contentView = contentView;
    }
    public Utils() {
    }

    public View contentView;

    public void threadCheck(){
        final Handler h = new Handler();;
        final int delay = 1000;
        //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                checkKeyboardOpen();
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    public void checkKeyboardOpen(){

        this.contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                }
                else {
                    hideSystemUI();
                }
            }
        });
    }

    public void hideSystemUI() {
        this.contentView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public boolean checkEmpty(EditText editText){
        if(editText.getText().length() < 1)
            return true;
        else
            return false;
    }

    public String GsonCustom(List<Object> objects, String key){
        JsonArray ja = new JsonArray();
        for (Object object: objects) {
            Gson gson = new Gson();
            JsonElement je = gson.toJsonTree(object);
            JsonObject jo = new JsonObject();
            jo.add(key, je);
            ja.add(jo);
        }

        JsonObject objMain = new JsonObject();
        objMain.add("surveys",ja);

        return objMain.toString();
    }

    public String GsonCustomAuth(Object object, String key){
        Gson gson = new Gson();
        JsonElement je = gson.toJsonTree(object);
        JsonObject jo = new JsonObject();
        jo.add(key, je);


        return jo.toString();
    }


    public static File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }

    public static File createImageFile(
            Context context,
            String dirName,
            String fileName,
            String fileType) {
        try {
            File file = createDir(context, dirName);
            File image = new File(file.getAbsoluteFile() + File.separator + fileName + fileType);
            image.createNewFile();
            return image;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param dirName
     * @return
     */
    public static File createDir(
            Context context,
            String dirName){
        File file = new File(context.getExternalFilesDir(null) + File.separator + dirName);
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

    /**
     *
     * @param file
     * @param requiredHeight
     * @return
     */
    public static Bitmap decodeFile(File file, int requiredHeight) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= requiredHeight &&
                    o.outHeight / scale / 2 >= requiredHeight) {
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param bitmap
     * @param filePath
     * @param imageType
     * @param compression
     */
    public static void saveBitmap(Bitmap bitmap, String filePath, String imageType, int compression){

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // PNG is a loss less format, the compression factor (100) is ignored
            if(imageType.equals("png") || imageType.equals("PNG") || imageType.equals(".png")){
                bitmap.compress(Bitmap.CompressFormat.PNG, compression, out);
            }
            else if(imageType.equals("jpg") || imageType.equals("JPG") || imageType.equals(".jpg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
            else if(imageType.equals("jpeg") || imageType.equals("JPEG") || imageType.equals(".jpeg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
