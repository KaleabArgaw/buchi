package et.com.kifiya.Buchi.Dtos.In;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionIn {
    private Long customerId;
    private Long petId;
}
