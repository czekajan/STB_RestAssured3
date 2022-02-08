package jsonplaceholder;

import lombok.Data;

@Data
public class AddressLombok {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoLombok geolombok;


}
