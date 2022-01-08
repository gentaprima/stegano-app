package com.example.steganoapp.helper;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Helper {
    static public String ApiURL = "http://192.168.0.151/steganografi/";

    static public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    static public String[] getRealPathFromURImage(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int indexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return new String[]{cursor.getString(columnIndex), cursor.getString(indexData)};
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    static public String encode(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = fis.read(buf))) {
            baos.write(buf, 0, n);
        }
        byte[] videoBytes = baos.toByteArray();
        String encoder = Base64.encodeToString(videoBytes, 0);
        return encoder;
    }

    static public void decode(String imageBase64) throws IOException {
        byte[] decoded = Base64.decode(imageBase64, 0);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "test.txt");
        file.createNewFile();
        FileOutputStream os = new FileOutputStream(file, true);
        os.write(decoded);
        os.close();
    }

    static public void downloadFile(Context context, String url, String fileName) {
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setDescription("Ouput Stegano");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "game-of-life");
        downloadmanager.enqueue(request);
    }
}
