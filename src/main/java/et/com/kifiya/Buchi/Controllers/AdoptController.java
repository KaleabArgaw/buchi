package et.com.kifiya.Buchi.Controllers;

import et.com.kifiya.Buchi.Dtos.In.AdoptionIn;
import et.com.kifiya.Buchi.Dtos.In.AdoptionListIn;
import et.com.kifiya.Buchi.Dtos.Out.AdoptCountResDto;
import et.com.kifiya.Buchi.Dtos.Out.AdoptionListOut;
import et.com.kifiya.Buchi.Dtos.Out.AdoptionOut;
import et.com.kifiya.Buchi.Services.AdoptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/adoptions")
@Tag(name = "Adoption Related")
public class AdoptController {
    private final AdoptionService adoptionService;

    public AdoptController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @PostMapping
    public ResponseEntity<AdoptionOut> adopt(@RequestBody AdoptionIn adoptionIn) {
        return ResponseEntity.ok().body(adoptionService.adopt(adoptionIn));
    }

    @GetMapping
    public AdoptionListOut list_adoption_requests(AdoptionListIn adoptionListIn) {
        return adoptionService.listAdoptions(adoptionListIn);
    }
    @GetMapping("/generateReport")
    public AdoptCountResDto generate_report(AdoptionListIn adoptionListIn) throws ParseException {
        return adoptionService.generate_report(adoptionListIn);
    }
}
