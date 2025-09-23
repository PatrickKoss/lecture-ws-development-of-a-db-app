package com.example.restsimple.repository;

import com.example.restsimple.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ZoneRepository extends JpaRepository<Zone, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Zone z SET z.name = :name, z.description = :description WHERE z.dnsName = :dnsName")
    int updateZone(@Param("dnsName") String dnsName, @Param("name") String name, @Param("description") String description);

    @Modifying
    @Transactional
    @Query("DELETE FROM Zone z WHERE z.dnsName = :dnsName")
    int deleteByDnsName(@Param("dnsName") String dnsName);
}
