package et.com.kifiya.Buchi.Services;

import et.com.kifiya.Buchi.Dtos.Datas;
import et.com.kifiya.Buchi.Dtos.In.AdoptionIn;
import et.com.kifiya.Buchi.Dtos.In.AdoptionListIn;
import et.com.kifiya.Buchi.Dtos.Out.AdoptCountResDto;
import et.com.kifiya.Buchi.Dtos.Out.AdoptionListOut;
import et.com.kifiya.Buchi.Dtos.Out.AdoptionOut;
import et.com.kifiya.Buchi.Dtos.Out.Data;
import et.com.kifiya.Buchi.Models.Adopt;
import et.com.kifiya.Buchi.Models.Customer;
import et.com.kifiya.Buchi.Models.Pet;
import et.com.kifiya.Buchi.Repositories.AdoptionRepository;
import et.com.kifiya.Buchi.Repositories.CustomerRepository;
import et.com.kifiya.Buchi.Repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AdoptionService {


    private final AdoptionRepository adoptionRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    Integer petCount = 0;


    public AdoptionService(AdoptionRepository adoptionRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public AdoptionOut adopt(AdoptionIn adoptionIn) {
        Adopt adoption = new Adopt();
        Optional<Pet> petById = petRepository.findById(adoptionIn.getPetId());
        Optional<Customer> customerById = customerRepository.findById(adoptionIn.getCustomerId());
        if (customerById.isPresent() || petById.isPresent()) {
            adoption.setPet(petById.get());
            adoption.setCustomer(customerById.get());
            Adopt save = adoptionRepository.save(adoption);
            return new AdoptionOut(save.getId(), "Success");
        } else {
            return new AdoptionOut(null, "customer or pet id does not exit");
        }
    }

    public AdoptionListOut listAdoptions(AdoptionListIn adoptionListIn) {
        AdoptionListOut adoptionListOut = new AdoptionListOut();
        List<Data> adoptList = new ArrayList<>();
        List<Adopt> allByCreatedAtBetween = adoptionRepository.findAllByCreatedAtBetweenOrderByCreatedAt(adoptionListIn.getFromDate(), adoptionListIn.getToDate());
        allByCreatedAtBetween.forEach(adopt -> {
            Data data = new Data();
            data.setCustomerId(adopt.getCustomer().getId());
            data.setCustomerPhone(adopt.getCustomer().getPhone());
            data.setCustomerName(adopt.getCustomer().getName());
            data.setPetId(adopt.getPet().getId());
            data.setType(adopt.getPet().getType());
            data.setGender(adopt.getPet().getType());
            data.setSize(adopt.getPet().getSize());
            data.setAge(adopt.getPet().getAge());
            data.setGoodWithChildren(adopt.getPet().isGoodWithChildren());

            adoptList.add(data);
        });
        adoptionListOut.setData(adoptList);
        adoptionListOut.setStatus("Success");
        return adoptionListOut;
    }

    public AdoptCountResDto generate_report(AdoptionListIn searchDto) throws ParseException {

        AdoptCountResDto adoptCountResDto = new AdoptCountResDto();
        List<String> petTypeList = new ArrayList<>();

        petRepository.findAll().forEach(pet -> {
            if (!petTypeList.contains(pet.getType())) {
                petTypeList.add(pet.getType());
            }
        });
        System.out.println("all distinct pets -> " + petTypeList);

        HashMap<String, Integer> map1 = new HashMap<>();

        petTypeList.forEach(petType -> {

//            List<Pet> pets = petRepository.findPetsByType(petType);
//            pets.forEach(pet -> {
            petCount = adoptionRepository.countAdoptByPet_Type(petType);
            System.out.println(petCount + " " + petType);
            map1.put(petType, petCount);

//            });

        });

        long diff = searchDto.getToDate().getTime() - searchDto.getFromDate().getTime();

        TimeUnit time = TimeUnit.DAYS;
        int diffrence = (int) time.convert(diff, TimeUnit.MILLISECONDS);

        int week = diffrence / 7;
        List<Date> dates = new ArrayList<>();
        if (week != 0) {
            for (int i = 0; i <= week; i++) {
                if (i == 0) {
                    dates.add(searchDto.getFromDate());
                } else {
                    LocalDate lastDate = dates.get(dates.size() - 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate lastDatePlusSeven = lastDate.plusWeeks(1);
                    Date date = Date.from(lastDatePlusSeven.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = simpleDateFormat.parse(getDate(date.toString()));
                    System.out.println("parsed date ---> " + parsedDate);

                    dates.add(parsedDate);
                }
            }
        }
        int count = 0;
        HashMap<String, Integer> map2 = new HashMap<>();
        for (int i = 0; i < dates.size(); i++) {


            if (i == 0) {
                count = adoptionRepository.countAdoptByCreatedAtBetween(dates.get(i), dates.get(i));

            } else {
                count = adoptionRepository.countAdoptByCreatedAtBetween(dates.get(i - 1), dates.get(i));
            }
            map2.put(getDate(dates.get(i).toString()), count);
        }

        HashMap<String, Integer> result = map2.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        adoptCountResDto.setStatus("success");
        adoptCountResDto.setData(new Datas(map1, result));
        return adoptCountResDto;
    }


    private String getDate(String dateStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = (Date) formatter.parse(dateStr);
        System.out.println(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        System.out.println("formatedDate : " + formatedDate);
        return formatedDate;

    }
}
