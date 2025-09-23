package com.example.restsimple.response;

import java.util.List;

public class GetAllZonesResponse {
    private String message;
    private List<GetZoneResponse> zones;

    public GetAllZonesResponse() {}

    public GetAllZonesResponse(String message, List<GetZoneResponse> zones) {
        this.message = message;
        this.zones = zones;
    }

    public List<GetZoneResponse> getZones() {
        return zones;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setZones(List<GetZoneResponse> zones) {
        this.zones = zones;
    }
}
