package et.com.kifiya.Buchi.Dtos.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPetIn {
    private String type;
    private String gender;
    private String size;
    private String age;
    private boolean goodWithChildren;
    private int limit;
}
