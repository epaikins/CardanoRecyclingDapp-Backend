package com.cardanorecyclingdapp.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.cardanorecyclingdapp.dao.CustomerDAO;
import com.cardanorecyclingdapp.entity.Customer;
import com.cardanorecyclingdapp.repository.CustomerRepo;
import com.cardanorecyclingdapp.repository.CustomerTypeRepo;
import com.cardanorecyclingdapp.repository.IdentityTypeRepo;
import com.cardanorecyclingdapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.cardanorecyclingdapp.utils.ResponseUtils.send;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3;
    private final CustomerRepo customerRepo;
    private final IdentityTypeRepo identityTypeRepo;
    private final CustomerTypeRepo customerTypeRepo;

    @Override
    public Map<Object, Object> saveCustomer(MultipartFile file, CustomerDAO customerDAO){
        String originalFileName = System.currentTimeMillis()+ "_" + getUsernameFromEmail(customerDAO.getEmail());
        Customer customer = new Customer();
        customer.setImageUrl(null);
        if(file != null){
            try {
                File convFile = convertMultiPartToFile(file);
                s3.putObject(bucketName, originalFileName, convFile);
                customer.setImageUrl(s3.getUrl(bucketName, originalFileName).toString());
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if(!customerDAO.getPassword().equalsIgnoreCase(customerDAO.getConfirmPassword())){
            return send(false, "Password does not match Confirm Password");
        }
        customer.setPassword(customerDAO.getPassword());
        customer.setEmail(customerDAO.getEmail());
        customer.setName(customerDAO.getName());
        customer.setIdentityNumber(customerDAO.getIdentityNumber());
        customer.setPhonenumber(customerDAO.getPhonenumber());
        customer.setDigitalAddress(customerDAO.getDigitalAddress());
        customer.setIdentityType(identityTypeRepo.findById(customerDAO.getIdentityTypeId()).orElse(null));
        customer.setCustomerType(customerTypeRepo.findById(customerDAO.getCustomerTypeId()).orElse(null));


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
