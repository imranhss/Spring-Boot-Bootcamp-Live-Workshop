package com.emranhss.hotebooking.restcontroller;


import com.emranhss.hotebooking.entity.Hotel;
import com.emranhss.hotebooking.entity.Location;
import com.emranhss.hotebooking.service.HotelService;
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
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getAllHotels(){
            List<Hotel> hotels=hotelService.getAllHotels();

            return ResponseEntity.ok(hotels);
    }


    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveHotel(
            @RequestPart(value = "hotel") String hotelJson,
            @RequestParam(value = "image") MultipartFile file
            ) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Hotel hotel=objectMapper.readValue(hotelJson, Hotel.class);

        try{
            hotelService.saveHotel(hotel, file);
            Map<String, String> response=new HashMap<>();
            response.put("Message", "Hotel Added Successfully ");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){

            Map<String, String> errorResponse=new HashMap<>();
            errorResponse.put("Message", "Hotel Add Faild ");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable int id){
        try{

            Hotel hotel=hotelService.findHotelById(id);
            return ResponseEntity.ok(hotel);
        }
        catch (RuntimeException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);


        }

    }


    @GetMapping("/h/searchhotel")
    public ResponseEntity<List<Hotel>> findHotelByLocationName(
            @RequestParam(value = "locationName"
            ) String locationName){

        List<Hotel> hotels=hotelService.findHotelByLocationName(locationName);

        return ResponseEntity.ok(hotels);

    }


 @GetMapping("/h/searchhotelname")
    public ResponseEntity<Hotel> findHotelByName(
            @RequestParam(value = "name"
            ) String name){

        Hotel hotel=hotelService.findHotelByName(name);

     return ResponseEntity.ok(hotel);

    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable int id) {

        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok("Hotel with this ID " + id + " has been Deleted");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Hotel> updateHotel(
            @PathVariable int id,
            @RequestPart Hotel hotel,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {

        Hotel updateHotel=hotelService.updateHotel(id, hotel,file);

        return ResponseEntity.ok(updateHotel);

    }



}
