package com.shivam.sv.service;

import com.shivam.sv.exception.InternalServerException;
import com.shivam.sv.exception.ResourceNotFoundException;
import com.shivam.sv.model.Room;
import com.shivam.sv.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoById(Long roomId) throws SQLException {
        Optional<Room> theRoom=roomRepository.findById(roomId);
        if (theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry, room not found");
        }
        Blob photoBlob=theRoom.get().getPhoto();
        if(photoBlob!=null){
            return photoBlob.getBytes(1,(int)photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoomById(Long roomId) {
        Optional<Room> theRoom=roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) throws InternalServerException {
        Room room=roomRepository.findById(roomId).
                orElseThrow(()->new ResourceNotFoundException("Room not found"));
        if(roomType!=null){
            room.setRoomType(roomType);
        }
        if(roomPrice!=null){
            room.setRoomPrice(roomPrice);
        }
        if(photoBytes!=null && photoBytes.length>0){
            try{
                room.setPhoto(new SerialBlob(photoBytes));
            }catch (SQLException e) {
                throw new InternalServerException("Error updating room");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }
}
