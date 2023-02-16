package et.com.kifiya.Buchi.Dtos.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerOut {
    private Long customerId;
    private String status;
}
