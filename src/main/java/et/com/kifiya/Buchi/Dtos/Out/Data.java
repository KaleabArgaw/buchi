package et.com.kifiya.Buchi.Dtos.Out;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private Long customerId;
    private String customerPhone;
    private String customerName;
    private Long petId;
    private String type;
    private String gender;
    private String size;
    private String age;
    private boolean goodWithChildren;
}
