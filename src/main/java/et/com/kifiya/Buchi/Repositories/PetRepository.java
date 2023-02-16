package et.com.kifiya.Buchi.Repositories;

import et.com.kifiya.Buchi.Models.Pet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Long> {

    @Query("select p from Pet p where p.type=:type or p.age=:age or p.size =:size or p.goodWithChildren=:good or p.gender=:gender")
    List<Pet> findByParameters(@Param("type") String type,
                               @Param("age") String age,
                               @Param("size") String size,
                               @Param("good") Boolean goodWithChildren,
                               @Param("gender") String gender,
                               PageRequest pageable);

    List<Pet> findPetsByType(String type);
    Integer countByType(String type);
}
