package com.sunmnet.mediaroom.brand.bean.face;

import java.util.List;

public class FaceModel {


    private List<ModelFile> modelFile;

    public List<ModelFile> getModelFile() {
        return modelFile;
    }

    public void setModelFile(List<ModelFile> modelFile) {
        this.modelFile = modelFile;
    }

    public static class ModelFile {
        private String name;
        private String md5;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }
}
