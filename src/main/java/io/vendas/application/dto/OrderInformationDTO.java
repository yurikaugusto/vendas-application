package io.vendas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInformationDTO {

    private Integer code;

    private String cpf;

    private String customerName;

    private BigDecimal total;

    private String orderDate;

    private String status;

    private List<OrderItemInformationDTO> items;

}
