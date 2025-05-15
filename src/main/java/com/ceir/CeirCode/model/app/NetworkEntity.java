package com.ceir.CeirCode.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;

@Entity
@Table(name = "network_node_mapping")
public class NetworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mno_name")
    private String mnoName;

    @Column(name = "ne_name")
    private String neName;

    @Column(name = "ne_type")
    private String neType;

    @Column(name = "gt_address")
    private String gtAddress;

    @Column(name = "hostname")
    private String hostname;

    @Transient
    @JsonProperty(value = "auditTrailModel", access = JsonProperty.Access.WRITE_ONLY)
    private AuditTrailModel auditTrailModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMnoName() {
        return mnoName;
    }

    public void setMnoName(String mnoName) {
        this.mnoName = mnoName;
    }

    public String getNeName() {
        return neName;
    }

    public void setNeName(String neName) {
        this.neName = neName;
    }

    public String getNeType() {
        return neType;
    }

    public void setNeType(String neType) {
        this.neType = neType;
    }

    public String getGtAddress() {
        return gtAddress;
    }

    public void setGtAddress(String gtAddress) {
        this.gtAddress = gtAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public AuditTrailModel getAuditTrailModel() {
        return auditTrailModel;
    }

    public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
        this.auditTrailModel = auditTrailModel;
    }

    @Override
    public String toString() {
        return "NetworkEntity{" +
                "id=" + id +
                ", mnoName='" + mnoName + '\'' +
                ", neName='" + neName + '\'' +
                ", neType='" + neType + '\'' +
                ", gtAddress='" + gtAddress + '\'' +
                ", hostname='" + hostname + '\'' +
                ", auditTrailModel=" + auditTrailModel +
                '}';
    }
}
