package com.emranhss.hotebooking.service;

import com.emranhss.hotebooking.entity.Hotel;
import com.emranhss.hotebooking.entity.Location;
import com.emranhss.hotebooking.entity.Room;
import com.emranhss.hotebooking.repository.HotelRepository;
import com.emranhss.hotebooking.repository.RoomRepository;
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
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;


    @Value("src/main/resources/static/images")
    private String uploadDir;


    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }


    public void saveRoom(Room room, MultipartFile imageFile) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = saveImage(imageFile, room);
            room.setImage(imageFileName);
        }


        roomRepository.save(room);

    }

    public Room getRoomById(int id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with Id: " + id));
    }


    public void deleteRoom(int id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with Id: " + id);
        } else {
            roomRepository.deleteById(id);
        }

    }


    public Room updateRoom(int id, Room updateRoom, MultipartFile image) throws IOException {

        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with Id: " + id));

        existingRoom.setName(updateRoom.getName());
        existingRoom.setPrice(updateRoom.getPrice());
        existingRoom.setArea(updateRoom.getArea());
        existingRoom.setAdultNo(updateRoom.getAdultNo());
        existingRoom.setChildNo(updateRoom.getChildNo());

        // update Location
        Hotel hotel = hotelRepository.findById(updateRoom.getHotel().getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with Id: " + updateRoom.getHotel().getId()));

        existingRoom.setHotel(hotel);

        //update image

        if (image != null && !image.isEmpty()) {

            String fileName = saveImage(image, existingRoom);
            existingRoom.setImage(fileName);
        }


        return roomRepository.save(existingRoom);

    }


    public List<Room> findRoomByHotelName(String hotelName) {

        return roomRepository.findRoomByHotelName(hotelName);

    }


    public List<Room> findRoomByHotelId(int hotelId) {

        return roomRepository.findRoomByHotelId(hotelId);

    }


    private String saveImage(MultipartFile file, Room room) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/rooms");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = room.getName() + "_" + UUID.randomUUID().toString();
        // sazid mahmus_gsdfhgsdjgfsdgfjfhj
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }


}
