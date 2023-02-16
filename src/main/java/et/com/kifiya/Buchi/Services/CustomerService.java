package et.com.kifiya.Buchi.Services;

import et.com.kifiya.Buchi.Dtos.Out.AddCustomerOut;
import et.com.kifiya.Buchi.Models.Customer;
import et.com.kifiya.Buchi.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public AddCustomerOut addCustomer(Customer customer){

        AddCustomerOut addCustomerOut = new AddCustomerOut();
        Customer byPhone = customerRepository.findByPhone(customer.getPhone());
        if (byPhone == null){
            Customer save = customerRepository.save(customer);
            addCustomerOut.setCustomerId(save.getId());
            addCustomerOut.setStatus("Success");
        }else {
            addCustomerOut.setCustomerId(byPhone.getId());
            addCustomerOut.setStatus("Already Exist");
        }
        return addCustomerOut;
    }


}
