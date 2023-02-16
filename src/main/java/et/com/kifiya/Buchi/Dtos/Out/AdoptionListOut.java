package et.com.kifiya.Buchi.Dtos.Out;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class AdoptionListOut {
    private String status;
    private List<Data> data;
}
