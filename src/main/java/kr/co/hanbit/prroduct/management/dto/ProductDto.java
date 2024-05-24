package kr.co.hanbit.prroduct.management.dto;

import jakarta.validation.constraints.NotNull;

public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Integer getPrice() {
        return price;
    }

    @NotNull
    public Integer getAmount() {
        return amount;
    }
}
