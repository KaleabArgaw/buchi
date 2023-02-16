package et.com.kifiya.Buchi.Repositories;

import et.com.kifiya.Buchi.Models.Adopt;
import et.com.kifiya.Buchi.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdoptionRepository extends JpaRepository<Adopt,Long> {
    List<Adopt> findAllByCreatedAtBetweenOrderByCreatedAt(Date from,Date upto);
    Integer countAdoptByCreatedAtBetween(Date from, Date upto);
    Integer countAdoptByPet_Type(String type);

}
