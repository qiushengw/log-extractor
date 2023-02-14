package com.kidzmath.qius.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommUtils {

    public static Path generateZipFile(List<Path> files, String filePathStr){
        try {
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(filePathStr);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (Path srcFile : files) {
                FileInputStream fis = new FileInputStream(srcFile.toFile());
                zos.putNextEntry(new ZipEntry(srcFile.toFile().getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }

            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
