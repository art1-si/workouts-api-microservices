package co.bytesarc.workoutsapi.setentries.services;

import java.util.List;
import org.springframework.stereotype.Service;
import co.bytesarc.workoutsapi.setentries.dto.CreateSetEntryRequest;
import co.bytesarc.workoutsapi.setentries.dto.UpdateSetEntryRequest;
import co.bytesarc.workoutsapi.setentries.models.SetEntry;
import co.bytesarc.workoutsapi.setentries.repositories.SetEntryRepository;

@Service
public class SetEntryService {
    private final SetEntryRepository setEntryRepository;

    public SetEntryService(SetEntryRepository setEntryRepository) {
        this.setEntryRepository = setEntryRepository;
    }


    public List<SetEntry> getSetEntries(int userId) {
        return setEntryRepository.getSetEntries(userId);
    }

    public SetEntry createSetEntry(CreateSetEntryRequest setEntry) {
        return setEntryRepository.createSetEntry(setEntry.getUserId(), setEntry.getExerciseId(),
                setEntry.getWeight(), setEntry.getReps(), setEntry.getNote());
    }

    public SetEntry updateSetEntry(int setEntryId, UpdateSetEntryRequest data) {
        return setEntryRepository.updateSetEntry(setEntryId, data.getWeight(), data.getReps());
    }

    public void deleteSetEntry(int id) {
        setEntryRepository.deleteSetEntry(id);
    }
}
