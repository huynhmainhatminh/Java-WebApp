package com.restapi.api.model.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter


public class RegisterPackageRequests {
    Integer userId;
    String name;
    BigDecimal price;
    Integer duration_days;


    @Override
    public String toString() {
        return "RegisterPackageRequests{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", duration_days=" + duration_days +
                '}';
    }
}
