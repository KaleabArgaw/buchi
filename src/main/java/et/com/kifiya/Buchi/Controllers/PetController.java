package et.com.kifiya.Buchi.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.kifiya.Buchi.Dtos.In.SearchPetIn;
import et.com.kifiya.Buchi.Dtos.Out.AddPetOut;
import et.com.kifiya.Buchi.Dtos.Out.AuthResponseDto;
import et.com.kifiya.Buchi.Dtos.Out.PetResponseDto;
import et.com.kifiya.Buchi.Models.Pet;
import et.com.kifiya.Buchi.Services.PetService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/pet")
@Tag(name = "Pet Related")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<AddPetOut> create_pet(@RequestParam(value = "pet", required = false) String pet,
                                                @RequestParam("image") MultipartFile multipartFile) throws IOException {
        {
            ObjectMapper om = new ObjectMapper();
            Pet pet1 = null;
            try {
                pet1 = om.readValue(pet, Pet.class);   //string st -> MyInput input
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert pet1 != null;
            return ResponseEntity.ok().body(petService.addPet(pet1, multipartFile));
        }
    }

    @GetMapping
    public List<PetResponseDto> get_pets(SearchPetIn petRequestDto) {
        return petService.get_pets(petRequestDto);
    }

}
