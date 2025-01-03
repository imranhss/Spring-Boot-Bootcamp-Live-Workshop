package com.emranhss.hotebooking.restcontroller;


import com.emranhss.hotebooking.entity.Hotel;
import com.emranhss.hotebooking.entity.Location;
import com.emranhss.hotebooking.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {


    @Autowired
    private LocationService locationService;



    @GetMapping("/")
    public ResponseEntity<List<Location>> getAllLocations(){

        List<Location> studentsList=locationService.getAllLocations();

        return ResponseEntity.ok(studentsList);

    }


//    @PostMapping("/save")
//    public ResponseEntity<String> saveLocation(
//            @RequestPart Location location,
//            @RequestParam(value = "image", required = true) MultipartFile file
//    ) throws IOException {
//        locationService.saveLocation(location, file);
//
//        return  new ResponseEntity<>("Location saved Successfully", HttpStatus.CREATED);
//    }


    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveLocation(
            @RequestPart(value = "location") String locationJson,
            @RequestParam(value = "image") MultipartFile file
    ) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Location location=objectMapper.readValue(locationJson, Location.class);

        try{
            locationService.saveLocation(location, file);
            Map<String, String> response=new HashMap<>();
            response.put("Message", "Location Added Successfully ");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){

            Map<String, String> errorResponse=new HashMap<>();
            errorResponse.put("Message", "Location Add Failed ");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable int id) {

        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Location with this ID " + id + " has been Deleted");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable int id,
            @RequestPart Location l,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {

        Location updateLocation=locationService.updateLocation(id, l,file);

        return ResponseEntity.ok(updateLocation);

    }

}
