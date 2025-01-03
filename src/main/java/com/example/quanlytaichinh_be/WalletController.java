package com.example.quanlytaichinh_be;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.moneymanagement.model.Wallet;
import vn.codegym.moneymanagement.service.wallet.WalletService;

import java.util.Optional;


@RestController
@RequestMapping("/api/wallets")
@CrossOrigin(origins = "*")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/fix")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        walletService.save(wallet);
        return new ResponseEntity<>(wallet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wallet>> findById(@PathVariable Long id) {
       Optional<Wallet> wallet = walletService.findById(id);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Wallet>> findAll() {
        Iterable<Wallet> walletList = walletService.findAll();
        return new ResponseEntity<>(walletList,HttpStatus.OK);
    }
}
