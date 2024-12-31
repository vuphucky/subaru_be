package vn.codegym.moneymanagement.model.DTO.wallet;

import lombok.Data;

@Data
public class WalletResponse {
    private Long id;
    private String name;
    private String iconUrl;
    private String currency;
    private String description;
    private String balance;

}
