package com.tracker.inventory.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;


@Entity
@Table
@SQLDelete(sql = "UPDATE item SET is_deleted = true WHERE id=?")
@FilterDef(
    name = "deletedItemFilter",
    parameters = @ParamDef(name = "is_deleted", type = "boolean")
)
@Filter(
    name = "deletedItemFilter",
    condition = "is_deleted = :is_deleted"
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    private UUID id;
    private String name;
    private Integer quantity;
    private String location;
    private boolean isDeleted;
    private String deletionMessage;

    public Item() {
    }

    public Item(UUID id, String name, Integer quantity, String location) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public Item(String name, Integer quantity, String location) {

        this.id = UUID.randomUUID();
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }


    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDeletionMessage() {
        return deletionMessage;
    }

    public void setDeletionMessage(String deletionMessage) {
        this.deletionMessage = deletionMessage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", quantity=" + quantity +
            ", location='" + location + '\'' +
            '}';
    }
}
