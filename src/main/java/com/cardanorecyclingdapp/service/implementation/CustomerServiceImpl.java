package com.cardanorecyclingdapp.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cardanorecyclingdapp.dao.CustomerDAO;
import com.cardanorecyclingdapp.entity.Customer;
import com.cardanorecyclingdapp.repository.CustomerRepo;
import com.cardanorecyclingdapp.repository.CustomerTypeRepo;
import com.cardanorecyclingdapp.repository.IdentityTypeRepo;
import com.cardanorecyclingdapp.repository.RoleRepo;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.cardanorecyclingdapp.utils.ResponseUtils.send;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {
    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3;
    private final CustomerRepo customerRepo;
    private final RoleRepo roleRepo;
    private final IdentityTypeRepo identityTypeRepo;
    private final CustomerTypeRepo customerTypeRepo;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomerServiceImpl(ObjectMapper objectMapper, CustomerRepo customerRepo,CustomerTypeRepo customerTypeRepo,IdentityTypeRepo identityTypeRepo, AmazonS3 s3, RoleRepo roleRepo, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager) {
        this.customerRepo = customerRepo;
        this.roleRepo = roleRepo;
        this.s3 = s3;
        this.objectMapper = objectMapper;
        this.customerTypeRepo = customerTypeRepo;
        this.identityTypeRepo = identityTypeRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

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
                customer.getRoles().add(roleRepo.findById(1L).orElse(null));
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        customerRepo.save(customer);
        return send(true, customer);
    }

    @Override
    public Map<Object, Object> signinCustomer(String email, String password) {
        Customer customUser = customerRepo.findByEmail(email);
        if(customUser != null) {
            try{
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
                Authentication authentication = authenticationManager.authenticate(token);
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                Algorithm algorithm = Algorithm.HMAC256("upt".getBytes());
                Integer EXPIRE_TIME = 10 * 60 * 1000;
                String access_token = JWT.create()
                        .withSubject(email)
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                        .withIssuer("org.cardanorecyclingdapp")
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<Object, Object> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("customer", customUser);
                return tokens;
            }
            catch (Exception e) {
                return send(false, e.toString());
            }
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepo.findByEmail(username);
        if(user ==null){
            throw new UsernameNotFoundException("User Not Found in the Database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
