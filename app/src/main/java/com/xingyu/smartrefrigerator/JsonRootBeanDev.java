package com.xingyu.smartrefrigerator;

import java.util.List;


public class JsonRootBeanDev {
    private int errno;
    private Data data;
    private String error;
    public void setErrno(int errno) {
        this.errno = errno;
    }
    public int getErrno() {
        return errno;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }

    public class Data {
        private List<Devices> devices;
        private int total_count;

        public void setDevices(List<Devices> devices) {
            this.devices = devices;
        }
        public List<Devices> getDevices() {
            return devices;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
        public int getTotal_count() {
            return total_count;
        }

        public class Devices {
            private String id;
            private String online;
            private String title;


            public void setId(String id) {
                this.id = id;
            }
            public String getId() {
                return id;
            }

            public void setOnline(String online) {
                this.online = online;
            }
            public String getOnline() {
                return online;
            }

            public void setTitle(String ttitle) {
                this.title = title;
            }
            public String getTitle() {
                return title;
            }
        }

    }

}


