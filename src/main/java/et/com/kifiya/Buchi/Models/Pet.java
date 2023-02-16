package et.com.kifiya.Buchi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String type;
    private String gender;
    private String size;
    private String age;
    private boolean goodWithChildren;
    private String photos;
    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;

}
