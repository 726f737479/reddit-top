package com.dev.rosty.reddit.common;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManagerImpl implements FileManager {

    private static final String SNAPSHOT_DATEFORMAT = "yyyyMMdd_HHmmss";

    private final String dir;

    public FileManagerImpl(String dir) {
        this.dir = dir;
    }

    @Override
    public String saveImage(final Bitmap image) {

        String filename = getImageFilename();
        String dir = getGalleryDirPath();

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(dir + File.separator + filename);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);

            return dir + File.separator + filename;

        } catch (Exception e) {
            e.printStackTrace();

            return null;

        } finally {

            try {
                if (out != null) out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getImageFilename() {

        SimpleDateFormat format = new SimpleDateFormat(SNAPSHOT_DATEFORMAT);

        return "image_"
                .concat(format.format(new Date()))
                .concat(".png");
    }

    private String getGalleryDirPath() {

        File file = new File(dir);

        if (!file.exists() || !file.isDirectory())
            file.mkdirs();

        return file.getAbsolutePath();
    }
}
