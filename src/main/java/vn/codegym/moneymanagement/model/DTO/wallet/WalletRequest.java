package vn.codegym.moneymanagement.model.DTO.wallet;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class WalletRequest {
    private String name;
    private BigDecimal balance;
    private String currency;
    private String description;
    private MultipartFile icon;
}
