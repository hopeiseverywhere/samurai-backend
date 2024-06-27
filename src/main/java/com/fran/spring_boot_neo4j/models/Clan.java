package com.fran.spring_boot_neo4j.models;

import java.util.ArrayList;
import java.util.List;

public class Clan {

    /**
     * 氏
     */
    private String shisei;

    /**
     * 八色の姓
     */
    private String kabane;

    /**
     * 苗字
     */
    private String myoji;

    private List<String> allMyojis;

    public Clan(String shisei, String kabane, String myoji) {
        allMyojis = new ArrayList<>();
        this.shisei = shisei;
        this.kabane = kabane;
        this.myoji = myoji;
        allMyojis.add(myoji);
    }

    public String getShisei() {
        return shisei;
    }

    public void setShisei(String shisei) {
        this.shisei = shisei;
    }

    public String getKabane() {
        return kabane;
    }

    public void setKabane(String kabane) {
        this.kabane = kabane;
    }

    public String getMyoji() {
        return myoji;
    }

    public void setMyoji(String myoji) {
        this.myoji = myoji;
    }

    public List<String> getAllMyojis() {
        return allMyojis;
    }

    public void setAllMyojis(List<String> allMyojis) {
        this.allMyojis = allMyojis;
    }
}
