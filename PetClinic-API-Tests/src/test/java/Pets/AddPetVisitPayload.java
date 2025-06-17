package Pets;

public class AddPetVisitPayload {
        public String date;
        public String description;

        public AddPetVisitPayload(String date, String description) {
            this.date = date;
            this.description = description;
        }
}
