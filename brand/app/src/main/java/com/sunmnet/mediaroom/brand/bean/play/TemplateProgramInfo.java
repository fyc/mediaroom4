package com.sunmnet.mediaroom.brand.bean.play;

import java.io.Serializable;

public class TemplateProgramInfo implements Serializable {

    private String resFilePath;
    private String resFileName;
    private String resFileMd5;

    public String getResFilePath() {
        return resFilePath;
    }

    public String getResFileName() {
        return resFileName;
    }

    public String getResFileMd5() {
        return resFileMd5;
    }

    public void setResFilePath(String resFilePath) {
        this.resFilePath = resFilePath;
    }

    public void setResFileName(String resFileName) {
        this.resFileName = resFileName;
    }

    public void setResFileMd5(String resFileMd5) {
        this.resFileMd5 = resFileMd5;
    }
}
