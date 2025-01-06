package com.example.quanlytaichinh_be.model.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddMoneyDTO {
    private Long walletId;
    private BigDecimal amount;
    private String note;
}
