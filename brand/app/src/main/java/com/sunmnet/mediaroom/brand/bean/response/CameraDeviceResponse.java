package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class CameraDeviceResponse extends Result<List<CameraDeviceResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * deviceCode : CAMERA_0001
         * deviceName : 测试
         * brandId : bca5648139074dc5a0ab8b761fc99483
         * brandName : 海康
         * modelId : 9aa1ebc02e874f74a067e606ee2287e7
         * modelName : DS2-2SW
         * ip : 192.168.x.x
         * port : 554
         * username : admin
         * password : 123456
         * channelNumber : 1
         * videoAislePort : 8888
         * videoCodeStream : 视频流
         *
         */

        private String deviceCode;
        private String deviceName;
        private String brandId;
        private String brandName;
        private String modelId;
        private String modelName;
        private String ip;
        private String port;
        private String username;
        private String password;
        private String channelNumber;
        private String videoAislePort;
        private String videoCodeStream;

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getChannelNumber() {
            return channelNumber;
        }

        public void setChannelNumber(String channelNumber) {
            this.channelNumber = channelNumber;
        }

        public String getVideoAislePort() {
            return videoAislePort;
        }

        public void setVideoAislePort(String videoAislePort) {
            this.videoAislePort = videoAislePort;
        }

        public String getVideoCodeStream() {
            return videoCodeStream;
        }

        public void setVideoCodeStream(String videoCodeStream) {
            this.videoCodeStream = videoCodeStream;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "deviceCode='" + deviceCode + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", brandId='" + brandId + '\'' +
                    ", brandName='" + brandName + '\'' +
                    ", modelId='" + modelId + '\'' +
                    ", modelName='" + modelName + '\'' +
                    ", ip='" + ip + '\'' +
                    ", port='" + port + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", channelNumber='" + channelNumber + '\'' +
                    ", videoAislePort='" + videoAislePort + '\'' +
                    ", videoCodeStream='" + videoCodeStream + '\'' +
                    '}';
        }
    }

}
