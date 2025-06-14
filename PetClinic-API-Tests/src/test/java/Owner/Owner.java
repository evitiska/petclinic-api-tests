package Owner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String telephone;

    public Owner(String firstName, String lastName, String address, String city, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public Owner() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (!firstName.equals(owner.firstName)) return false;
        if (!lastName.equals(owner.lastName)) return false;
        if (!address.equals(owner.address)) return false;
        if (!city.equals(owner.city)) return false;
        return telephone.equals(owner.telephone);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + telephone.hashCode();
        return result;
    }
}
