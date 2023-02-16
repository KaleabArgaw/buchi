package et.com.kifiya.Buchi.Dtos.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdoptionOut {
    private Long adoptionId;
    private String status;
}
