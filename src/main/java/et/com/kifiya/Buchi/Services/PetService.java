package et.com.kifiya.Buchi.Services;

import et.com.kifiya.Buchi.Dtos.In.AuthRequestDto;
import et.com.kifiya.Buchi.Dtos.In.SearchPetIn;
import et.com.kifiya.Buchi.Dtos.Out.*;
import et.com.kifiya.Buchi.Models.Pet;
import et.com.kifiya.Buchi.Repositories.PetRepository;
import et.com.kifiya.Buchi.Utils.Constants;
import et.com.kifiya.Buchi.Utils.FileUploadUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {


    private final PetRepository petRepository;

    RestTemplate restTemplate = new RestTemplate();

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public AddPetOut addPet(Pet pet, MultipartFile multipartFile) throws IOException {

        AddPetOut addPetOut = new AddPetOut();
        Pet newPet = new Pet();
        newPet.setAge(pet.getAge());
        newPet.setGender(pet.getGender());
        newPet.setSize(pet.getSize());
        newPet.setType(pet.getType());
        newPet.setGoodWithChildren(pet.isGoodWithChildren());
        String originalFilename = multipartFile.getOriginalFilename();
        String dir = "Images/" + newPet.getId();
        FileUploadUtil.saveFile(dir, originalFilename, multipartFile);
        newPet.setPhotos(dir);
        Pet save = petRepository.save(newPet);
        addPetOut.setPetId(save.getId());
        addPetOut.setStatus("Success");

        return addPetOut;
    }

    public AuthResponseDto auth() {
        String authUrl = Constants.authUrl;
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setClient_id(Constants.client_id);
        authRequestDto.setClient_secret(Constants.client_secret);
        authRequestDto.setGrant_type(Constants.grant_type);
        HttpEntity<AuthRequestDto> entity = new HttpEntity<>(authRequestDto);
        ResponseEntity<AuthResponseDto> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, entity, AuthResponseDto.class);

        return exchange.getBody();
    }

    public List<PetResponseDto> get_pets(SearchPetIn petRequestDto) {

        List<PetResponseDto> petResponseDtos = new ArrayList<>();
        List<Pet> pets = petRepository.findByParameters(petRequestDto.getType(), petRequestDto.getAge(),
                petRequestDto.getSize(),
                petRequestDto.isGoodWithChildren(),
                petRequestDto.getGender(), PageRequest.of(0, petRequestDto.getLimit()));
        pets.forEach(pet -> {
            PetResponseDto petRes = new PetResponseDto();
            petRes.setPetId(pet.getId());
            petRes.setSource("Local");
            petRes.setAge(pet.getAge());
            petRes.setSize(pet.getSize());
            petRes.setStatus("adoptable");
            petRes.setPhotos(new Photos[]{new Photos(pet.getPhotos(), null, null)});
            petRes.setType(pet.getType());
            petRes.setGender(pet.getGender());
            petRes.setGoodWithChildern(pet.isGoodWithChildren());
            petResponseDtos.add(petRes);
        });

        if (pets.size() >= petRequestDto.getLimit()) {

        } else {
            Long remaining = (long) petRequestDto.getLimit() - pets.size();
            try {
                String token = auth().getAccess_token();
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                String t = Constants.getPetsUrl;
                String builder = UriComponentsBuilder.fromUriString(t)
                        // Add query parameter
                        .queryParam("type", petRequestDto.getType())
                        .queryParam("age", petRequestDto.getAge())
                        .queryParam("size", petRequestDto.getSize())
                        .queryParam("gender", petRequestDto.getGender())
                        .queryParam("limit", remaining)
                        .queryParam("environment.children", String.valueOf(petRequestDto.isGoodWithChildren())).toUriString();


                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<ResponseDto> exchange =
                        restTemplate.exchange(builder, HttpMethod.GET, entity, ResponseDto.class);

                exchange.getBody().getAnimals().forEach(animals -> {
                    if (animals.getPhotos() != null && animals.getPhotos().length > 0) {
                        //mapping
                        PetResponseDto petResponseDto = new PetResponseDto();
                        petResponseDto.setPhotos(animals.getPhotos());
                        petResponseDto.setPetId(animals.getId());
                        petResponseDto.setType(animals.getType());
                        petResponseDto.setGender(animals.getGender());
                        petResponseDto.setAge(animals.getAge());
                        petResponseDto.setSize(animals.getSize());
                        petResponseDto.setStatus(animals.getStatus());
                        petResponseDto.setGoodWithChildern(animals.getEnvironment().isChildren());
                        petResponseDto.setSource("Pet Finder");


                        petResponseDtos.add(petResponseDto);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petResponseDtos;

    }
}
