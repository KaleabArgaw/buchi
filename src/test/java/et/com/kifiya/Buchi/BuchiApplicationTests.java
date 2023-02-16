package et.com.kifiya.Buchi;

import et.com.kifiya.Buchi.Models.Adopt;
import et.com.kifiya.Buchi.Models.Customer;
import et.com.kifiya.Buchi.Models.Pet;
import et.com.kifiya.Buchi.Repositories.AdoptionRepository;
import et.com.kifiya.Buchi.Repositories.CustomerRepository;
import et.com.kifiya.Buchi.Repositories.PetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BuchiApplicationTests {

    @Test
    void contextLoads() {
    }

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private AdoptionRepository adoptRepository;

    @Test
    public void addCustomerTest() {
        Long id = 1L;
        Customer customer = new Customer(id, "Fekes", "0942137657", null, null);

        when(customerRepository.save(customer)).thenReturn(customer);
    }

    @Test
    public void saveAdoptTest() {
        Long id = 1L;

        Pet pet = new Pet(id, "local", "Dog", "male", "small", "young", true, "testpict", null, null);
        Customer customer = new Customer(id, "Fekes", "0942137657", null, null);
        Adopt adoption = new Adopt(id, customer, pet, new Date(), new Date(), new Date());

        when(adoptRepository.save(adoption)).thenReturn(adoption);

    }


	@Test
	public void savePetTest() {
		Pet pet = new Pet();
		pet.setType("dog");
		pet.setSize("small");
		pet.setAge("Baby");
		pet.setGender("male");
		pet.setGoodWithChildren(true);
		pet.setPhotos("lion-theme-wx2.jpg");

        when(petRepository.save(pet)).thenReturn(pet);
	}

	@Test
	public void getListOfPetTest(){

		when(petRepository.findAll()).thenReturn(Stream.of(new Pet(1L,"name","name","type",null,null,true,null,new Date(),new Date())).collect(Collectors.toList()));
		assertEquals(1,petRepository.findAll().size());

	}


//	@Test
//	public void getListOfAdoptTest(){
//		List<Adopt> adopts = adoptRepository.findAll();
//		Assertions.assertThat(adopts.size()).isGreaterThan(0);
//
//	}

//	@Test
//	@Order(6)
//	@Rollback(value = false)
//	public void getListOfAdoptByweekTest(){
//		List<Adopt> adopts = adoptRepository.findAll();
//		Assertions.assertThat(adopts.size()).isGreaterThan(0);
//	}
}
