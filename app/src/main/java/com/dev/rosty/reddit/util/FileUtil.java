package com.dev.rosty.reddit.util;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    private static final String SNAPSHOT_DATEFORMAT = "yyyyMMdd_HHmmss";

    public static String getGalleryDirPath() {

        String snapshotDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();

        File file = new File(snapshotDir);

        if (!file.exists() || !file.isDirectory())
            file.mkdirs();

        return file.getAbsolutePath();
    }


}
