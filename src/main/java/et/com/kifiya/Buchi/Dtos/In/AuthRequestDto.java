package et.com.kifiya.Buchi.Dtos.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequestDto {


    private String grant_type;
    private String client_id;
    private String client_secret;
}