package et.com.kifiya.Buchi.Dtos.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PetResponseDto {
    private Long petId;
    private String source;
    private String type;
    private String age;
    private Photos[] photos;
    private String size;
    private String status;
    private String gender;
    private Boolean goodWithChildern;
}