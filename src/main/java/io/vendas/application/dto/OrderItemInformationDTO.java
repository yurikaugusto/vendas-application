package io.vendas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemInformationDTO {

    private String productDescription;

    private BigDecimal unityPrice;

    private Integer quantity;

}
