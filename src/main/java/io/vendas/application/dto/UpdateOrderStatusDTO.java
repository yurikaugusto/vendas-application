package io.vendas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderStatusDTO {

    private String newStatus;

}
