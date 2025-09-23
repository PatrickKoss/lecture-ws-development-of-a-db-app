package com.example.restsimple.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "zone")
public class Zone {
    @Id
    @Column(name="dns_name")
    private String dnsName;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public Zone() {}

    public Zone(String dnsName, String name, String description) {
        this.dnsName = dnsName;
        this.name = name;
        this.description = description;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDnsName() {
        return dnsName;
    }

    public String getName() {
        return name;
    }
}
