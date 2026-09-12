package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
class PartJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "part_number", nullable = false)
  private String partNumber;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "shelf_code", nullable = false)
  private String shelfCode;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  @Column(name = "reorder_level", nullable = false)
  private Integer reorderLevel;

  @Column(name = "list_price", nullable = false)
  private BigDecimal listPrice;

  protected PartJpaEntity() {}

  private PartJpaEntity(
      Long id,
      String partNumber,
      String name,
      String category,
      String shelfCode,
      Integer stockQuantity,
      Integer reorderLevel,
      BigDecimal listPrice) {
    this.id = id;
    this.partNumber = partNumber;
    this.name = name;
    this.category = category;
    this.shelfCode = shelfCode;
    this.stockQuantity = stockQuantity;
    this.reorderLevel = reorderLevel;
    this.listPrice = listPrice;
  }

  static PartJpaEntity fromDomain(Part value) {
    return new PartJpaEntity(
        value.id(),
        value.partNumber(),
        value.name(),
        value.category(),
        value.shelfCode(),
        value.stockQuantity(),
        value.reorderLevel(),
        value.listPrice());
  }

  Part toDomain() {
    return new Part(
        id, partNumber, name, category, shelfCode, stockQuantity, reorderLevel, listPrice);
  }

  public Long id() {
    return id;
  }

  public String partNumber() {
    return partNumber;
  }

  public String name() {
    return name;
  }

  public String category() {
    return category;
  }

  public String shelfCode() {
    return shelfCode;
  }

  public Integer stockQuantity() {
    return stockQuantity;
  }

  public Integer reorderLevel() {
    return reorderLevel;
  }

  public BigDecimal listPrice() {
    return listPrice;
  }
}
