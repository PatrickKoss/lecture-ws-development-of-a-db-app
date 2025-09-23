package com.example.restsimple.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateZoneRequest {
    @NotBlank(message = "dns name is required")
    @Size(max = 63, min = 2, message = "dns name must not exceed 63 characters")
    private String dnsName;

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Name must not exceed 2000 characters")
    private String description;

    public CreateZoneRequest() {}

    public CreateZoneRequest(String dnsName, String name, String description) {
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
