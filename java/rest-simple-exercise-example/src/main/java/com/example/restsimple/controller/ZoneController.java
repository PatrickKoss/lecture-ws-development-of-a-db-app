package com.example.restsimple.controller;

import com.example.restsimple.model.Zone;
import com.example.restsimple.repository.ZoneRepository;
import com.example.restsimple.request.CreateZoneRequest;
import com.example.restsimple.request.UpdateZoneRequest;
import com.example.restsimple.response.CreateZoneResponse;
import com.example.restsimple.response.ErrorResponse;
import com.example.restsimple.response.GetAllZonesResponse;
import com.example.restsimple.response.GetZoneResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zones")
@Tag(name = "Zone Management", description = "Zone management operations")
public class ZoneController {
    private ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping("")
    @Operation(
            summary = "Get all zones",
            description = "Returns all zones"
    )
    @ApiResponse(responseCode = "200", description = "Get all zones")
    public ResponseEntity<GetAllZonesResponse> getAllZones() {
        // 1. get data
        List<Zone> modelZones = this.zoneRepository.findAll();

        // 2. transform data
        List<GetZoneResponse> responseZones = new ArrayList<>();
        for (int i = 0; i < modelZones.toArray().length; i++) {
            responseZones.add(new GetZoneResponse(modelZones.get(i).getDnsName(), modelZones.get(i).getName(), modelZones.get(i).getDescription()));
        }

        GetAllZonesResponse getAllZonesResponse = new GetAllZonesResponse("successfully got all zones", responseZones);

        // 3. return transformed data
        return ResponseEntity.ok(getAllZonesResponse);
    }

    @PostMapping("")
    @Operation(
            summary = "Create a zone",
            description = "Create a zone"
    )
    @ApiResponse(responseCode = "201", description = "Create a zone", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateZoneResponse.class)))
    @ApiResponse(responseCode = "409", description = "Zone already exists", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Zone not valid", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CreateZoneResponse> createZone(@Valid @RequestBody CreateZoneRequest createZoneRequest) {
        Zone zone = new Zone(createZoneRequest.getDnsName(), createZoneRequest.getName(), createZoneRequest.getDescription());
        this.zoneRepository.save(zone);
        return ResponseEntity.status(201).body(new CreateZoneResponse(zone.getDnsName(), zone.getName(), zone.getDescription()));
    }

    @PutMapping("/{dnsName}")
    @Operation(
            summary = "Update a zone",
            description = "Update an existing zone by DNS name"
    )
    @ApiResponse(responseCode = "200", description = "Zone updated successfully", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = GetZoneResponse.class)))
    @ApiResponse(responseCode = "404", description = "Zone not found", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<GetZoneResponse> updateZone(@PathVariable String dnsName, @Valid @RequestBody UpdateZoneRequest updateZoneRequest) {
        int updatedRows = this.zoneRepository.updateZone(dnsName, updateZoneRequest.getName(), updateZoneRequest.getDescription());
        if (updatedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found");
        }
        return ResponseEntity.ok(new GetZoneResponse(dnsName, updateZoneRequest.getName(), updateZoneRequest.getDescription()));
    }

    @DeleteMapping("/{dnsName}")
    @Operation(
            summary = "Delete a zone",
            description = "Delete an existing zone by DNS name"
    )
    @ApiResponse(responseCode = "204", description = "Zone deleted successfully")
    @ApiResponse(responseCode = "404", description = "Zone not found", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteZone(@PathVariable String dnsName) {
        int deletedRows = this.zoneRepository.deleteByDnsName(dnsName);
        if (deletedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found");
        }
        return ResponseEntity.noContent().build();
    }
}
