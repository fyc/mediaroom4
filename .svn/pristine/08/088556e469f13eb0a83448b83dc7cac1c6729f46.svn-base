package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;

public class WeatherResponse extends Result<WeatherResponse.Result> {

    public static class Result implements Serializable {

        /**
         * city : 广州
         * currentWeather : {"time":"2020-08-05 17:00","weatherCode":"d08","weather":"中雨","temperature":"26℃","windDirection":"无持续风向","windScale":"<3级"}
         * generalWeather : {"date":"2020-08-05","weatherCode1":"d301","weatherCode2":"n301","weather":"雨","tempRange":"28℃/25℃","windDirection":"东南风","windScale":"<3级"}
         */

        private String city;
        private CurrentWeatherBean currentWeather;
        private GeneralWeatherBean generalWeather;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public CurrentWeatherBean getCurrentWeather() {
            return currentWeather;
        }

        public void setCurrentWeather(CurrentWeatherBean currentWeather) {
            this.currentWeather = currentWeather;
        }

        public GeneralWeatherBean getGeneralWeather() {
            return generalWeather;
        }

        public void setGeneralWeather(GeneralWeatherBean generalWeather) {
            this.generalWeather = generalWeather;
        }

        public static class CurrentWeatherBean {
            /**
             * time : 2020-08-05 17:00
             * weatherCode : d08
             * weather : 中雨
             * temperature : 26℃
             * windDirection : 无持续风向
             * windScale : <3级
             */

            private String time;
            private String weatherCode;
            private String weather;
            private String temperature;
            private String windDirection;
            private String windScale;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getWeatherCode() {
                return weatherCode;
            }

            public void setWeatherCode(String weatherCode) {
                this.weatherCode = weatherCode;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getWindScale() {
                return windScale;
            }

            public void setWindScale(String windScale) {
                this.windScale = windScale;
            }
        }

        public static class GeneralWeatherBean {
            /**
             * date : 2020-08-05
             * weatherCode1 : d301
             * weatherCode2 : n301
             * weather : 雨
             * tempRange : 28℃/25℃
             * windDirection : 东南风
             * windScale : <3级
             */

            private String date;
            private String weatherCode1;
            private String weatherCode2;
            private String weather;
            private String tempRange;
            private String windDirection;
            private String windScale;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeatherCode1() {
                return weatherCode1;
            }

            public void setWeatherCode1(String weatherCode1) {
                this.weatherCode1 = weatherCode1;
            }

            public String getWeatherCode2() {
                return weatherCode2;
            }

            public void setWeatherCode2(String weatherCode2) {
                this.weatherCode2 = weatherCode2;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTempRange() {
                return tempRange;
            }

            public void setTempRange(String tempRange) {
                this.tempRange = tempRange;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getWindScale() {
                return windScale;
            }

            public void setWindScale(String windScale) {
                this.windScale = windScale;
            }
        }
    }
}
