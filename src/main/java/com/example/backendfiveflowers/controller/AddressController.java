package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Address;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public Page<Address> getAllAddresses(Pageable pageable) {
        return addressService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Address> getAddressById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        Address address = addressService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        address.setUser(addressDetails.getUser());
        address.setAddress_line1(addressDetails.getAddress_line1());
        address.setAddress_line2(addressDetails.getAddress_line2());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setPostal_code(addressDetails.getPostal_code());
        address.setCountry(addressDetails.getCountry());
        return addressService.save(address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
    }
}
