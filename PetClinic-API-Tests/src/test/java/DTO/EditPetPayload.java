package DTO;

public class EditPetPayload {
    public int id;
    public String name;
    public int typeId;
    public String birthDate;

    public EditPetPayload(int id, String name, int typeId, String birthDate) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.birthDate = birthDate;
    }
}
