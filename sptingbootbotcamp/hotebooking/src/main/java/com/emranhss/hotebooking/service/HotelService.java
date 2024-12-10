package com.emranhss.hotebooking.service;

import com.emranhss.hotebooking.entity.Hotel;
import com.emranhss.hotebooking.entity.Location;
import com.emranhss.hotebooking.repository.HotelRepository;
import com.emranhss.hotebooking.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;



    @Value("src/main/resources/static/images")
    private  String uploadDir;


    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();
    }


    public void saveHotel(Hotel h, MultipartFile imageFile) throws IOException {

        if(imageFile != null && !imageFile.isEmpty()){
            String imageFileName=saveImage(imageFile, h);
            h.setImage(imageFileName);
        }

        Location location=locationRepository.findById(h.getLocation().getId())
                .orElseThrow(()-> new EntityNotFoundException("Location not found with Id: "+h.getLocation().getId()));


        h.setLocation(location);

        hotelRepository.save(h);

    }

    public Hotel findHotelById(int id){

        return  hotelRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Hotel not found with Id: "+id));

    }

    public Hotel findHotelByName(String  name){

        return  hotelRepository.findByName(name)
                .orElseThrow(()-> new EntityNotFoundException("Hotel not found with name : "+name));

    }


    public void updateHotel(int id, Hotel updateHotel, MultipartFile image) throws IOException {

            Hotel existingHotel=hotelRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Hotel not found with Id: "+id));

            existingHotel.setName(updateHotel.getName());
            existingHotel.setAddress(updateHotel.getAddress());
            existingHotel.setRating(updateHotel.getRating());
            existingHotel.setMaximumPrice(updateHotel.getMaximumPrice());
            existingHotel.setMinimumPrice(updateHotel.getMinimumPrice());

        // update Location
        Location location=locationRepository.findById(updateHotel.getLocation().getId())
                .orElseThrow(()-> new EntityNotFoundException("Location not found with Id: "+updateHotel.getLocation().getId()));

        existingHotel.setLocation(location);

        //update image

        if(image !=null && !image.isEmpty()){

            String fileName=saveImage(image,existingHotel);
            existingHotel.setImage(fileName);
        }


    }


    public  List<Hotel> findHotelByLocationName(String locationName){

            return  hotelRepository.findHotelByLocationName(locationName);

    }





    private  String saveImage(MultipartFile file, Hotel h) throws IOException {
        Path uploadPath= Paths.get(uploadDir+"/hotels");
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileName=h.getName()+"_"+ UUID.randomUUID().toString();
        // sazid mahmus_gsdfhgsdjgfsdgfjfhj
        Path filePath=uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

}
