package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Address;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.AddressRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Address addAddress(Address address) {
        String username = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
        if (userInfoOptional.isPresent()) {
            address.setUser(userInfoOptional.get());
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Address updateAddress(Address address) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(address.getUser().getId());
        if (userInfoOptional.isPresent()) {
            address.setUser(userInfoOptional.get());
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    public Optional<Address> getAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    public Page<Address> getAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public List<Address> getAddressesForCurrentUser() {
        String username = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
        if (userInfoOptional.isPresent()) {
            UserInfo user = userInfoOptional.get();
            return addressRepository.findByUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
