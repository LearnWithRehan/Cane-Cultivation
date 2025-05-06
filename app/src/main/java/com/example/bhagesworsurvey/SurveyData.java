package com.example.bhagesworsurvey;

import java.io.Serializable;

public class SurveyData implements Serializable {
    private String MachineNo;
    private String V_code;
    private String G_code;
    private String g_name;
    private String f_name;
    private String v_Name;
    private String variety;
    private String Ctype;
    private String plotNo;
   // private String Area;
   private double AREA;
    private String sdate;

    // Getters and Setters for each field


    public String getMachineNo() {
        return MachineNo;
    }

    public void setMachineNo(String machineNo) {
        MachineNo = machineNo;
    }

    public String getV_code() {
        return V_code;
    }

    public void setV_code(String v_code) {
        V_code = v_code;
    }

    public String getG_code() {
        return G_code;
    }

    public void setG_code(String g_code) {
        G_code = g_code;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getV_Name() {
        return v_Name;
    }

    public void setV_Name(String v_Name) {
        this.v_Name = v_Name;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getCtype() {
        return Ctype;
    }

    public void setCtype(String ctype) {
        Ctype = ctype;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public double getAREA() {
        return AREA;
    }

    public void setAREA(double AREA) {
        this.AREA = AREA;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
}
