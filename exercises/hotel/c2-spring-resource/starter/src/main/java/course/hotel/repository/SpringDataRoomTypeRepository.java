package course.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataRoomTypeRepository extends JpaRepository<RoomTypeJpaEntity, Long> {
  boolean existsByTypeCode(String value);

  boolean existsByTypeCodeAndIdNot(String value, long id);
}
