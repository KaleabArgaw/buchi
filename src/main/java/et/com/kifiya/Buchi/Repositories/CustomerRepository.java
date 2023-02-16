package et.com.kifiya.Buchi.Repositories;

import et.com.kifiya.Buchi.Models.Customer;
import et.com.kifiya.Buchi.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByPhone(String phone);

}
