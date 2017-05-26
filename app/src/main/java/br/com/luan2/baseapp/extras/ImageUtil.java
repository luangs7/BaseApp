package br.com.luan2.baseapp.extras;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DevMaker on 4/14/16.
 */
public class ImageUtil {

    // Scale and maintain aspect ratio given a desired width
    // BitmapScaler.scaleToFitWidth(bitmap, 100);
    public static Bitmap scaleToFitWidth(Bitmap b, int width)
    {
        float factor = width / (float) b.getWidth();
        if(b.getWidth() > width){
            return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
        }else{
            return Bitmap.createBitmap(b);
        }

    }


    // Scale and maintain aspect ratio given a desired height
    // BitmapScaler.scaleToFitHeight(bitmap, 100);
    public static Bitmap scaleToFitHeight(Bitmap b, int height)
    {
        float factor = height / (float) b.getHeight();
        if(b.getHeight() > height){
            return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
        }else{
            return Bitmap.createBitmap(b);
        }
    }

    public static String getBase64FromBitmap(Bitmap bitmap) {
        bitmap = scaleToFitWidth(bitmap,600);// getResizedBitmap(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String baseImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("tamanho string Base64", String.valueOf(baseImage.getBytes().length));

        return baseImage;
    }


    public static void getBase64FromBitmap(Context context, List<Bitmap> bitmap, ProcessBase64 processBase64) {
        task(context,bitmap,processBase64);
    }

    public static void getBase64FromBitmap(Context context, Bitmap bitmap, ProcessBase64 processBase64) {
        task(context,bitmap,processBase64);
    }


    private static void task(final Context context, final List<Bitmap> bitmap, final ProcessBase64 processBase64){
        AsyncTask<Void, String, List<String>> task = new AsyncTask<Void, String, List<String>>() {
            private boolean success = false;
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Processando dados");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.show();
            }

            @Override
            protected List<String> doInBackground(Void... arg0) {
                try {
                    List<String> strings = new ArrayList<>();
                    for (int i = 0; i < bitmap.size(); i++) {
                        strings.add(getBase64FromBitmap(bitmap.get(i)));
                    }
                    return strings;
                }catch (Exception ex)
                {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<String> result) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                if(result != null){
                    try {
                        for (int i = 0; i < bitmap.size(); i++) {
                            bitmap.get(i).recycle();
                        }
                        System.gc();
                    }catch (Exception ex){

                    }
                }
                processBase64.onProcessBase64(result);
            }
        };
        task.execute((Void[])null);
    }

    private static void task(final Context context, final Bitmap bitmap, final ProcessBase64 processBase64){
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            private boolean success = false;
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Processando dados");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.show();
            }

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    return getBase64FromBitmap(bitmap);
                }catch (Exception ex)
                {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (dialog!=null) {
                    dialog.dismiss();
                }
                if(result != null){
                    try {
                        bitmap.recycle();
                        System.gc();
                    }catch (Exception ex){

                    }
                }
                processBase64.onProcessBase64(result);
            }
        };
        task.execute((Void[])null);
    }


    public interface ProcessBase64
    {
        public void onProcessBase64(List<String> base64);
        public void onProcessBase64(String base64);
    }



}
