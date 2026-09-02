package com.example.restsimple.response;

public class CreateZoneResponse {
    private String dnsName;
    private String name;
    private String description;

    public CreateZoneResponse() {
    }

    public CreateZoneResponse(String dnsName, String name, String description) {
        this.dnsName = dnsName;
        this.name = name;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public void setName(String name) {
        this.name = name;
    }
}
