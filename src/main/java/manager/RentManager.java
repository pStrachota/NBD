package manager;

import model.Rent;
import repository.RentRepository;

public class RentManager {

    RentRepository archiveRents = new RentRepository();
    RentRepository currentRents = new RentRepository();


    public void registerRent(Rent rent) {
        currentRents.addCurrentRent(rent);
    }

    public void unregisterRent(Rent rent) {
        currentRents.removeRent(rent);
        archiveRents.addArchiveRent(rent);
    }

    public String getReport() {
        return currentRents.getReport();
    }

    public Rent findById(Long id) {
        return currentRents.findByID(id);
    }

    public String getArchiveReport() {
        return archiveRents.getReport();
    }

    public Rent findArchiveById(Long id) {
        return archiveRents.findByID(id);
    }

}
