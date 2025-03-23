package co.bytesarc.workoutsapi.setentries.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import co.bytesarc.workoutsapi.setentries.dto.CreateSetEntryRequest;
import co.bytesarc.workoutsapi.setentries.dto.UpdateSetEntryRequest;
import co.bytesarc.workoutsapi.setentries.models.SetEntry;
import co.bytesarc.workoutsapi.setentries.services.SetEntryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class SetEntryController {
    private final SetEntryService setEntryService;

    public SetEntryController(SetEntryService setEntryService) {
        this.setEntryService = setEntryService;
    }

    @GetMapping("/set-entries")
    public ResponseEntity<?> getSetEntries(@RequestParam int userId) {
        List<SetEntry> setEntries = setEntryService.getSetEntries(userId);
        return ResponseEntity.ok().body(setEntries);

    }

    @PostMapping("/set-entries")
    public ResponseEntity<?> createSetEntry(@RequestBody CreateSetEntryRequest request) {
        SetEntry createdSetEntry = setEntryService.createSetEntry(request);
        return new ResponseEntity<>(createdSetEntry, HttpStatus.CREATED);
    }

    @PutMapping("/set-entries/{id}")
    public ResponseEntity<?> updateSetEntry(@PathVariable int id,
            @RequestBody UpdateSetEntryRequest request) {
        SetEntry updatedSetEntry = setEntryService.updateSetEntry(id, request);
        return ResponseEntity.ok(updatedSetEntry);
    }


    @DeleteMapping("/set-entries/{id}")
    public ResponseEntity<?> deleteSetEntry(@PathVariable int id) {
        setEntryService.deleteSetEntry(id);
        return ResponseEntity.ok().build();
    }

}
