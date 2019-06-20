package org.openbravo.auction.model;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

public class Item {

  private String name;
  private String description;
  private String category;
  private Double volume;
  private Double weight;

  public Item(String name, String description, String category, Double volume, Double weight) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.volume = volume;
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Double getVolume() {
    return volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "Nombre: " + name + "\nCategoría: " + category + "\nDescripción: " + description
        + "\nVolumen: " + volume + "\nPeso: " + weight;
  }

}
