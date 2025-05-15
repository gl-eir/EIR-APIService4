package com.ceir.CeirCode.features.networkmanagement.csv_file_model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class NetworkManagementFileModel {
    @CsvBindByName(column = "MNO Name")
    @CsvBindByPosition(position = 0)
    private String mnoName;
    @CsvBindByName(column = "NE Name")
    @CsvBindByPosition(position = 1)
    private String neName;
    @CsvBindByName(column = "NE Type")
    @CsvBindByPosition(position = 2)
    private String neType;
    @CsvBindByName(column = "GT Address")
    @CsvBindByPosition(position = 3)
    private String gtAddress;

    @CsvBindByName(column = "Hostname")
    @CsvBindByPosition(position = 4)
    private String hostname;

    public String getMnoName() {
        return mnoName;
    }

    public NetworkManagementFileModel setMnoName(String mnoName) {
        this.mnoName = mnoName;
        return this;
    }

    public String getNeName() {
        return neName;
    }

    public NetworkManagementFileModel setNeName(String neName) {
        this.neName = neName;
        return this;
    }

    public String getNeType() {
        return neType;
    }

    public NetworkManagementFileModel setNeType(String neType) {
        this.neType = neType;
        return this;
    }

    public String getGtAddress() {
        return gtAddress;
    }

    public NetworkManagementFileModel setGtAddress(String gtAddress) {
        this.gtAddress = gtAddress;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public NetworkManagementFileModel setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

}
