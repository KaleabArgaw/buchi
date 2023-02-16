package et.com.kifiya.Buchi.Dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Datas {
    private HashMap<String,Integer> adopted_pet_types;
    private HashMap<String,Integer> weekly_adoption_requests;
}