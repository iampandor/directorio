package com.tcs.demo.directorio.restServices;

import com.tcs.demo.directorio.businessObjects.AddressBO;
import com.tcs.demo.directorio.dataObjects.Domicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AddressService {
    @Autowired
    AddressBO addressBO;

    @GetMapping("/address")
    public ResponseEntity<List<Domicilio>> getAllAddresses() {
        List<Domicilio> addressList = addressBO.getAllAddresses();
        if (addressList != null && !addressList.isEmpty()) {
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        }
        return new ResponseEntity<>(addressList, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<Domicilio> getAddress(@PathVariable String addressId) {
        try {
            Domicilio address = addressBO.getAddressById(addressId);
            if (address != null) {
                return new ResponseEntity<Domicilio>(address, HttpStatus.OK);
            }
        } catch (NoSuchElementException nse) {
            System.out.println(nse.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/address")
    public ResponseEntity<Domicilio> createAddress(@RequestBody Domicilio address) {
        try {
            Domicilio savedAddress = addressBO.createAddress(address);
            if (savedAddress != null) {
                return new ResponseEntity<>(savedAddress, HttpStatus.OK);
            }
        } catch (EntityNotFoundException enfe) {
            System.out.println(enfe.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
}
