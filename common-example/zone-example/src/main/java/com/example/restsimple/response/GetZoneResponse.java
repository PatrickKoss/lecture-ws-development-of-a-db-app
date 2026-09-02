package com.example.restsimple.response;

public class GetZoneResponse {
    private String dnsName;
    private String name;
    private String description;

    public GetZoneResponse() {}

    public GetZoneResponse(String dnsName, String name, String description) {
        this.description = description;
        this.name = name;
        this.dnsName = dnsName;
    }

    public String getDnsName() {
        return dnsName;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }
}
