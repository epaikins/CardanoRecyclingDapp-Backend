package com.cardanorecyclingdapp.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cardanorecyclingdapp.dao.CustomerDAO;
import com.cardanorecyclingdapp.entity.Customer;
import com.cardanorecyclingdapp.repository.CustomerRepo;
import com.cardanorecyclingdapp.repository.CustomerTypeRepo;
import com.cardanorecyclingdapp.repository.IdentityTypeRepo;
import com.cardanorecyclingdapp.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.cardanorecyclingdapp.utils.ResponseUtils.send;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3;
    private final CustomerRepo customerRepo;
    private final IdentityTypeRepo identityTypeRepo;
    private final CustomerTypeRepo customerTypeRepo;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Override
    public Map<Object, Object> saveCustomer(MultipartFile file, String json){
        Customer customer = new Customer();
        customer.setImageUrl(null);
        if(json != null){
            try{
                CustomerDAO customerDAO = objectMapper.readValue(json, CustomerDAO.class);
                if(!customerDAO.getPassword().equalsIgnoreCase(customerDAO.getConfirmPassword())){
                    return send(false, "Password does not match Confirm Password");
                }
                String originalFileName = System.currentTimeMillis()+ "_" + getUsernameFromEmail(customerDAO.getEmail());
                if(file != null){
                    try {
                        File convFile = convertMultiPartToFile(file);
                        s3.putObject(bucketName, originalFileName, convFile);
                        customer.setImageUrl(s3.getUrl(bucketName, originalFileName).toString());
                        convFile.delete();
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
                customer.setPassword(passwordEncoder.encode(customerDAO.getPassword()));
                customer.setEmail(customerDAO.getEmail());
                customer.setName(customerDAO.getName());
                customer.setIdentityNumber(customerDAO.getIdentityNumber());
                customer.setPhonenumber(customerDAO.getPhonenumber());
                customer.setDigitalAddress(customerDAO.getDigitalAddress());
                customer.setIdentityType(identityTypeRepo.findById(customerDAO.getIdentityTypeId()).orElse(null));
                customer.setCustomerType(customerTypeRepo.findById(customerDAO.getCustomerTypeId()).orElse(null));

            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        customerRepo.save(customer);
        return send(true, customer);
    }

    @Override
    public Map<String, Object> signinCustomer(String email, String password) {
        Customer customUser = customerRepo.findByEmail(email);
        if(customUser != null) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("upt".getBytes());
            Integer EXPIRE_TIME = 10 * 60 * 1000;
            String access_token = JWT.create()
                    .withSubject(email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .withIssuer("org.cardanorecyclingdapp")
                    .sign(algorithm);
            Map<String, Object> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("customer", customUser);
            return tokens;
        }
        return null;
    }

    @Override
    public Customer getCustomer(Long id) {
        return null;
    }

    @Override
    public Page<Customer> getCustomers(boolean isActive, int page, int size) {
        return null;
    }

    @Override
    public List<Customer> getCustomers(Boolean isActive) {
        return null;
    }

    @Override
    public Customer editCustomer(Long id, Customer customer) {
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String getUsernameFromEmail(String email){
        return email.split("@")[0];
    }
}
