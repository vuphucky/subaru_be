package com.example.quanlytaichinh_be.model.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletDTO {
    private Long id;
    private String name;
    private BigDecimal balance;
    private String iconUrl;
    private String currency;
    private String description;
    private Long userId;
}
