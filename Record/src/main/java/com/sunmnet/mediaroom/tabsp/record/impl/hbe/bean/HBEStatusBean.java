package com.sunmnet.mediaroom.tabsp.record.impl.hbe.bean;

import java.util.List;

public class HBEStatusBean {
    /**
     * Info : {"ControlInit":{"Value":1},"Channel":{"Item":[{"DeviceType":3,"Height":720,"Width":1280,"ChannelName":"教师"},{"DeviceType":4,"Height":720,"Width":1280,"ChannelName":"教师"},{"DeviceType":5,"Height":720,"Width":1280,"ChannelName":"学生"},{"DeviceType":6,"Height":720,"Width":1280,"ChannelName":"学生"},{"DeviceType":7,"Height":720,"Width":1280,"ChannelName":"台式机"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"互动主流"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"互动辅流"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"}]},"RecordStatus":{"Value":0},"Dog":{"MaxHours":0,"Year":0,"Value":1,"DogType":1,"Day":0,"Month":0},"AutoRule":{"Value":1},"MediaMode":{"Value":1},"RecordTime":{"Value":"00:00:00"},"DiskInfo":{"TotalDisk":931,"FreeDisk":931},"PhoneCall":{"RunTimer":-1,"Status":-1,"WorkMode":0,"RemoteIp":"","Call":0,"PushVGAFlag":0},"NetSysUpgrade":{"State":0,"Progress":""},"ResourceMode":{"Value":0},"AudioVolume":{"Item":[{"ID":0,"Value":0},{"ID":1,"Value":0},{"ID":2,"Value":0},{"ID":3,"Value":0}],"AudioMix":0},"GPS":{"Stu":0,"Tea":0},"PVW":{"Value":4},"PGM":{"Value":0}}
     */

    private InfoBean Info;

    public InfoBean getInfo() {
        return Info;
    }

    public void setInfo(InfoBean Info) {
        this.Info = Info;
    }

    public static class InfoBean {
        /**
         * ControlInit : {"Value":1}
         * Channel : {"Item":[{"DeviceType":3,"Height":720,"Width":1280,"ChannelName":"教师"},{"DeviceType":4,"Height":720,"Width":1280,"ChannelName":"教师"},{"DeviceType":5,"Height":720,"Width":1280,"ChannelName":"学生"},{"DeviceType":6,"Height":720,"Width":1280,"ChannelName":"学生"},{"DeviceType":7,"Height":720,"Width":1280,"ChannelName":"台式机"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"互动主流"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"互动辅流"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"},{"DeviceType":0,"Height":720,"Width":1280,"ChannelName":"None"}]}
         * RecordStatus : {"Value":0}
         * Dog : {"MaxHours":0,"Year":0,"Value":1,"DogType":1,"Day":0,"Month":0}
         * AutoRule : {"Value":1}
         * MediaMode : {"Value":1}
         * RecordTime : {"Value":"00:00:00"}
         * DiskInfo : {"TotalDisk":931,"FreeDisk":931}
         * PhoneCall : {"RunTimer":-1,"Status":-1,"WorkMode":0,"RemoteIp":"","Call":0,"PushVGAFlag":0}
         * NetSysUpgrade : {"State":0,"Progress":""}
         * ResourceMode : {"Value":0}
         * AudioVolume : {"Item":[{"ID":0,"Value":0},{"ID":1,"Value":0},{"ID":2,"Value":0},{"ID":3,"Value":0}],"AudioMix":0}
         * GPS : {"Stu":0,"Tea":0}
         * PVW : {"Value":4}
         * PGM : {"Value":0}
         */

        private ControlInitBean ControlInit;
        private ChannelBean Channel;
        private RecordStatusBean RecordStatus;
        private DogBean Dog;
        private AutoRuleBean AutoRule;
        private MediaModeBean MediaMode;
        private RecordTimeBean RecordTime;
        private DiskInfoBean DiskInfo;
        private PhoneCallBean PhoneCall;
        private NetSysUpgradeBean NetSysUpgrade;
        private ResourceModeBean ResourceMode;
        private AudioVolumeBean AudioVolume;
        private GPSBean GPS;
        private PVWBean PVW;
        private PGMBean PGM;

        public ControlInitBean getControlInit() {
            return ControlInit;
        }

        public void setControlInit(ControlInitBean ControlInit) {
            this.ControlInit = ControlInit;
        }

        public ChannelBean getChannel() {
            return Channel;
        }

        public void setChannel(ChannelBean Channel) {
            this.Channel = Channel;
        }

        public RecordStatusBean getRecordStatus() {
            return RecordStatus;
        }

        public void setRecordStatus(RecordStatusBean RecordStatus) {
            this.RecordStatus = RecordStatus;
        }

        public DogBean getDog() {
            return Dog;
        }

        public void setDog(DogBean Dog) {
            this.Dog = Dog;
        }

        public AutoRuleBean getAutoRule() {
            return AutoRule;
        }

        public void setAutoRule(AutoRuleBean AutoRule) {
            this.AutoRule = AutoRule;
        }

        public MediaModeBean getMediaMode() {
            return MediaMode;
        }

        public void setMediaMode(MediaModeBean MediaMode) {
            this.MediaMode = MediaMode;
        }

        public RecordTimeBean getRecordTime() {
            return RecordTime;
        }

        public void setRecordTime(RecordTimeBean RecordTime) {
            this.RecordTime = RecordTime;
        }

        public DiskInfoBean getDiskInfo() {
            return DiskInfo;
        }

        public void setDiskInfo(DiskInfoBean DiskInfo) {
            this.DiskInfo = DiskInfo;
        }

        public PhoneCallBean getPhoneCall() {
            return PhoneCall;
        }

        public void setPhoneCall(PhoneCallBean PhoneCall) {
            this.PhoneCall = PhoneCall;
        }

        public NetSysUpgradeBean getNetSysUpgrade() {
            return NetSysUpgrade;
        }

        public void setNetSysUpgrade(NetSysUpgradeBean NetSysUpgrade) {
            this.NetSysUpgrade = NetSysUpgrade;
        }

        public ResourceModeBean getResourceMode() {
            return ResourceMode;
        }

        public void setResourceMode(ResourceModeBean ResourceMode) {
            this.ResourceMode = ResourceMode;
        }

        public AudioVolumeBean getAudioVolume() {
            return AudioVolume;
        }

        public void setAudioVolume(AudioVolumeBean AudioVolume) {
            this.AudioVolume = AudioVolume;
        }

        public GPSBean getGPS() {
            return GPS;
        }

        public void setGPS(GPSBean GPS) {
            this.GPS = GPS;
        }

        public PVWBean getPVW() {
            return PVW;
        }

        public void setPVW(PVWBean PVW) {
            this.PVW = PVW;
        }

        public PGMBean getPGM() {
            return PGM;
        }

        public void setPGM(PGMBean PGM) {
            this.PGM = PGM;
        }

        public static class ControlInitBean {
            /**
             * Value : 1
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class ChannelBean {
            private List<ItemBean> Item;

            public List<ItemBean> getItem() {
                return Item;
            }

            public void setItem(List<ItemBean> Item) {
                this.Item = Item;
            }

            public static class ItemBean {
                /**
                 * DeviceType : 3
                 * Height : 720
                 * Width : 1280
                 * ChannelName : 教师
                 */

                private int DeviceType;
                private int Height;
                private int Width;
                private String ChannelName;

                public int getDeviceType() {
                    return DeviceType;
                }

                public void setDeviceType(int DeviceType) {
                    this.DeviceType = DeviceType;
                }

                public int getHeight() {
                    return Height;
                }

                public void setHeight(int Height) {
                    this.Height = Height;
                }

                public int getWidth() {
                    return Width;
                }

                public void setWidth(int Width) {
                    this.Width = Width;
                }

                public String getChannelName() {
                    return ChannelName;
                }

                public void setChannelName(String ChannelName) {
                    this.ChannelName = ChannelName;
                }
            }
        }

        public static class RecordStatusBean {
            /**
             * Value : 0
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class DogBean {
            /**
             * MaxHours : 0
             * Year : 0
             * Value : 1
             * DogType : 1
             * Day : 0
             * Month : 0
             */

            private int MaxHours;
            private int Year;
            private int Value;
            private int DogType;
            private int Day;
            private int Month;

            public int getMaxHours() {
                return MaxHours;
            }

            public void setMaxHours(int MaxHours) {
                this.MaxHours = MaxHours;
            }

            public int getYear() {
                return Year;
            }

            public void setYear(int Year) {
                this.Year = Year;
            }

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }

            public int getDogType() {
                return DogType;
            }

            public void setDogType(int DogType) {
                this.DogType = DogType;
            }

            public int getDay() {
                return Day;
            }

            public void setDay(int Day) {
                this.Day = Day;
            }

            public int getMonth() {
                return Month;
            }

            public void setMonth(int Month) {
                this.Month = Month;
            }
        }

        public static class AutoRuleBean {
            /**
             * Value : 1
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class MediaModeBean {
            /**
             * Value : 1
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class RecordTimeBean {
            /**
             * Value : 00:00:00
             */

            private String Value;

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
        }

        public static class DiskInfoBean {
            /**
             * TotalDisk : 931
             * FreeDisk : 931
             */

            private int TotalDisk;
            private int FreeDisk;

            public int getTotalDisk() {
                return TotalDisk;
            }

            public void setTotalDisk(int TotalDisk) {
                this.TotalDisk = TotalDisk;
            }

            public int getFreeDisk() {
                return FreeDisk;
            }

            public void setFreeDisk(int FreeDisk) {
                this.FreeDisk = FreeDisk;
            }
        }

        public static class PhoneCallBean {
            /**
             * RunTimer : -1
             * Status : -1
             * WorkMode : 0
             * RemoteIp :
             * Call : 0
             * PushVGAFlag : 0
             */

            private int RunTimer;
            private int Status;
            private int WorkMode;
            private String RemoteIp;
            private int Call;
            private int PushVGAFlag;

            public int getRunTimer() {
                return RunTimer;
            }

            public void setRunTimer(int RunTimer) {
                this.RunTimer = RunTimer;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getWorkMode() {
                return WorkMode;
            }

            public void setWorkMode(int WorkMode) {
                this.WorkMode = WorkMode;
            }

            public String getRemoteIp() {
                return RemoteIp;
            }

            public void setRemoteIp(String RemoteIp) {
                this.RemoteIp = RemoteIp;
            }

            public int getCall() {
                return Call;
            }

            public void setCall(int Call) {
                this.Call = Call;
            }

            public int getPushVGAFlag() {
                return PushVGAFlag;
            }

            public void setPushVGAFlag(int PushVGAFlag) {
                this.PushVGAFlag = PushVGAFlag;
            }
        }

        public static class NetSysUpgradeBean {
            /**
             * State : 0
             * Progress :
             */

            private int State;
            private String Progress;

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
            }

            public String getProgress() {
                return Progress;
            }

            public void setProgress(String Progress) {
                this.Progress = Progress;
            }
        }

        public static class ResourceModeBean {
            /**
             * Value : 0
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class AudioVolumeBean {
            /**
             * Item : [{"ID":0,"Value":0},{"ID":1,"Value":0},{"ID":2,"Value":0},{"ID":3,"Value":0}]
             * AudioMix : 0
             */

            private int AudioMix;
            private List<ItemBeanX> Item;

            public int getAudioMix() {
                return AudioMix;
            }

            public void setAudioMix(int AudioMix) {
                this.AudioMix = AudioMix;
            }

            public List<ItemBeanX> getItem() {
                return Item;
            }

            public void setItem(List<ItemBeanX> Item) {
                this.Item = Item;
            }

            public static class ItemBeanX {
                /**
                 * ID : 0
                 * Value : 0
                 */

                private int ID;
                private int Value;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public int getValue() {
                    return Value;
                }

                public void setValue(int Value) {
                    this.Value = Value;
                }
            }
        }

        public static class GPSBean {
            /**
             * Stu : 0
             * Tea : 0
             */

            private int Stu;
            private int Tea;

            public int getStu() {
                return Stu;
            }

            public void setStu(int Stu) {
                this.Stu = Stu;
            }

            public int getTea() {
                return Tea;
            }

            public void setTea(int Tea) {
                this.Tea = Tea;
            }
        }

        public static class PVWBean {
            /**
             * Value : 4
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }

        public static class PGMBean {
            /**
             * Value : 0
             */

            private int Value;

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }
        }
    }
}
