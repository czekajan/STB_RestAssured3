package jsonplaceholder;

import lombok.Data;

@Data
public class UserLombok {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private AddressLombok address;
    private String phone;
    private String website;
    private CompanyLombok company;

}
