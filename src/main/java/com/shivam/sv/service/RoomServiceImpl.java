package com.shivam.sv.service;

import com.shivam.sv.model.Room;
import com.shivam.sv.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final  RoomRepository roomRepository;
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room=new Room();

        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if(!photo.isEmpty()){
            byte[] photoytes=photo.getBytes();
            Blob photoBlob=new  SerialBlob(photoytes);

            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.finDistinctRoomTypes();
    }
}
