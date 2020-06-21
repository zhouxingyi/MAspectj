package com.fishman.zxy.maspectj.uplodPoint.body;

public class PointBody {
    private String type;
    private String  actionId;
    private String  data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", actionId='" + actionId + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
