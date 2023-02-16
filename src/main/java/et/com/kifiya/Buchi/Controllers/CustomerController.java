package et.com.kifiya.Buchi.Controllers;


import et.com.kifiya.Buchi.Dtos.Out.AddCustomerOut;
import et.com.kifiya.Buchi.Models.Customer;
import et.com.kifiya.Buchi.Services.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/customer")
@Tag(name = "Customer Related")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<AddCustomerOut> add_customer(@RequestBody Customer customer) {
        return ResponseEntity.ok().body(customerService.addCustomer(customer));
    }
}
