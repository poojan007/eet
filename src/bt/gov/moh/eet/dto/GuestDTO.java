package bt.gov.moh.eet.dto;

public class GuestDTO {
    private Integer guest_id;
    private Integer identification_no;
    private Integer identification_type_id;
    private Integer nationality_id;
    private String guest_name;
    private Character gender;
    private Integer age;
    private String present_address;
    private Integer contact_no;
    private Character residing_across_border;

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public Integer getIdentification_no() {
        return identification_no;
    }

    public void setIdentification_no(Integer identification_no) {
        this.identification_no = identification_no;
    }

    public Integer getIdentification_type_id() {
        return identification_type_id;
    }

    public void setIdentification_type_id(Integer identification_type_id) {
        this.identification_type_id = identification_type_id;
    }

    public Integer getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(Integer nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public Integer getContact_no() {
        return contact_no;
    }

    public void setContact_no(Integer contact_no) {
        this.contact_no = contact_no;
    }

    public Character getResiding_across_border() {
        return residing_across_border;
    }

    public void setResiding_across_border(Character residing_across_border) {
        this.residing_across_border = residing_across_border;
    }
}
