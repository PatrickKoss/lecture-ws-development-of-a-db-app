package course.pizzadelivery.repository;

import course.pizzadelivery.domain.Pizza;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
class PizzaJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "pizza_number", nullable = false)
  private String pizzaNumber;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "oven_station", nullable = false)
  private String ovenStation;

  @Column(name = "base_price", nullable = false)
  private BigDecimal basePrice;

  @Column(name = "active", nullable = false)
  private Boolean active;

  protected PizzaJpaEntity() {}

  private PizzaJpaEntity(
      Long id,
      String pizzaNumber,
      String name,
      String category,
      String ovenStation,
      BigDecimal basePrice,
      Boolean active) {
    this.id = id;
    this.pizzaNumber = pizzaNumber;
    this.name = name;
    this.category = category;
    this.ovenStation = ovenStation;
    this.basePrice = basePrice;
    this.active = active;
  }

  static PizzaJpaEntity fromDomain(Pizza value) {
    return new PizzaJpaEntity(
        value.id(),
        value.pizzaNumber(),
        value.name(),
        value.category(),
        value.ovenStation(),
        value.basePrice(),
        value.active());
  }

  Pizza toDomain() {
    return new Pizza(id, pizzaNumber, name, category, ovenStation, basePrice, active);
  }

  public Long id() {
    return id;
  }

  public String pizzaNumber() {
    return pizzaNumber;
  }

  public String name() {
    return name;
  }

  public String category() {
    return category;
  }

  public String ovenStation() {
    return ovenStation;
  }

  public BigDecimal basePrice() {
    return basePrice;
  }

  public Boolean active() {
    return active;
  }
}
