package com.union.travel.tvtest3;

import java.io.Serializable;

public class Watch implements Serializable {

    private String name;
    private String header;
    private String titile;
    private String mainDescription;
    private String smallDescription;
    private String iconUrlMain;
    private String iconUrlSecondary;


    public Watch(String name,
                 String header, String titile,
                 String mainDescription, String smallDescription,
                 String iconUrlMain, String iconUrlSecondary) {
        this.name = name;
        this.header = header;
        this.titile = titile;
        this.mainDescription = mainDescription;
        this.smallDescription = smallDescription;
        this.iconUrlMain = iconUrlMain;
        this.iconUrlSecondary = iconUrlSecondary;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public String getSmallDescription() {
        return smallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        this.smallDescription = smallDescription;
    }

    public String getIconUrlMain() {
        return iconUrlMain;
    }

    public void setIconUrlMain(String iconUrlMain) {
        this.iconUrlMain = iconUrlMain;
    }

    public String getIconUrlSecondary() {
        return iconUrlSecondary;
    }

    public void setIconUrlSecondary(String iconUrlSecondary) {
        this.iconUrlSecondary = iconUrlSecondary;
    }


}
