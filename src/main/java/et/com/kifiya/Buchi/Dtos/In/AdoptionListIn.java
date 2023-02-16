package et.com.kifiya.Buchi.Dtos.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdoptionListIn {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fromDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date toDate;
    private int limit;
}
