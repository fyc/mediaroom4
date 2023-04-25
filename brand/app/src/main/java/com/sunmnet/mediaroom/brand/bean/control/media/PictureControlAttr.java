package com.sunmnet.mediaroom.brand.bean.control.media;

import java.util.List;

public class PictureControlAttr {


    /**
     * carousel : 10
     * files : [{"filePath":"/picture/school.png"},{"filePath":"/picture/school.png"}]
     */

    private String carousel;
    private List<FilesBean> files;

    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        public FilesBean() {
        }

        public FilesBean(String filePath) {
            this.filePath = filePath;
        }

        /**
         * filePath : /picture/school.png
         */

        private String filePath;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
