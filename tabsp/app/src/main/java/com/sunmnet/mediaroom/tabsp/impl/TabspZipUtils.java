package com.sunmnet.mediaroom.tabsp.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class TabspZipUtils {

    /**
     * Default buff byte size
     *
     */
    private static final int DEFAULT_BUFF_SIZE = 1024;

    /**
     * Default basedir value
     *
     */
    private static final boolean DEFAULT_DIR = false;

    public static String decompress(String srcPath) throws Exception {
        return decompress(new File(srcPath));
    }

    public static String decompress(File srcFile) throws Exception {
        File baseFile = srcFile.getParentFile();
        return decompress(srcFile, baseFile);
    }

    public static void decompress(String srcPath, String destPath) throws Exception {
        decompress(new File(srcPath), new File(destPath));
    }

    public static String decompress(File srcFile, File destFile) throws Exception {
        String depressDir=null;
        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());
        ZipInputStream zis = new ZipInputStream(cis);
        ZipEntry zipEntry =zis.getNextEntry();
        if (zipEntry!=null){
            String name=zipEntry.getName();
            int index=name.indexOf("/");
            String directory=name.substring(0,index);
            doDecompress(destFile, zis,zipEntry);//desFile 是根目录
            depressDir=new File(destFile,directory).getPath();
        }
        zis.close();
        cis.close();
        return depressDir;
    }

    private static void doDecompress(File destFile, ZipInputStream zis,ZipEntry zipEntry) throws Exception {
        do {
            String dir = destFile.getPath() + File.separator + zipEntry.getName();
            File dirFile = new File(dir);
            fileProber(dirFile);
            if (zipEntry.isDirectory()) {
                dirFile.mkdirs();
            } else {
                doDecompressFile(dirFile, zis);
            }
            zis.closeEntry();
        }
        while ((zipEntry = zis.getNextEntry()) != null) ;
    }

    private static void doDecompressFile(File destFile, ZipInputStream zis) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        int len;
        byte[] buff = new byte[DEFAULT_BUFF_SIZE];
        while ((len = zis.read(buff, 0 ,DEFAULT_BUFF_SIZE)) != -1) {
            bos.write(buff, 0, len);
        }
        bos.close();
    }

    /**
     * 文件探测
     *
     * When the parent file not exist.Create it.
     *
     * @param dirFile
     * @throws Exception
     */
    public static void fileProber(File dirFile) throws Exception {
        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {
            fileProber(parentFile);
            parentFile.mkdirs();
        }
    }

    public static void compress(String srcPath, String destPath,boolean dirFlag) throws Exception {
        compress(new File(srcPath), new File(destPath), dirFlag);
    }

    public static void compress(String srcPath, String destPath) throws Exception {
        compress(new File(srcPath), new File(destPath), DEFAULT_DIR);
    }

    public static void compress(File srcFile, File destFile, boolean dirFlag) throws Exception {
        compress(srcFile, new ZipOutputStream(new FileOutputStream(destFile)), dirFlag);
    }

    public static void compress(File srcFile, ZipOutputStream zos, boolean dirFlag) throws Exception {
        if (srcFile.isDirectory()) {
            if (dirFlag) {
                doCompress(zos, srcFile, srcFile.getName() + File.separator);
            } else {
                doCompress(zos, srcFile, "");
            }
        } else {
            doCompress(zos, srcFile, "");
        }
        zos.close();
    }

    public static void doCompress(ZipOutputStream zos, File file, String baseDir) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                doCompress(zos, files[i], baseDir);
            }
        } else {
            byte[] buff = new byte[DEFAULT_BUFF_SIZE];
            InputStream in = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(baseDir + File.separator + file.getName()));
            int len;
            while ((len = in.read(buff,0 ,DEFAULT_BUFF_SIZE)) != -1) {
                zos.write(buff, 0, len);
            }
            in.close();
        }
    }
}
