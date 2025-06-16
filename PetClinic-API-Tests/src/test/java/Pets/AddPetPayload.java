package Pets;

public class AddPetPayload {
    public String name;
    public int typeId;
    public String birthDate;

    public AddPetPayload(String name, int typeId, String birthDate) {
        this.name = name;
        this.typeId = typeId;
        this.birthDate = birthDate;
    }
}
