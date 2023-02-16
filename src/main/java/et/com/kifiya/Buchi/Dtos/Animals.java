package et.com.kifiya.Buchi.Dtos;

import et.com.kifiya.Buchi.Dtos.Out.Photos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animals {
    private Long id;
    private String type;
    private String age;
    private String size;
    private String status;
    private String gender;
    private Date createdOn;
    private Date updatedOn;
    private Photos[] photos;
    private Environment environment;
}

