package com.shivam.sv.repository;

import com.shivam.sv.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("Select distinct r.roomType from Room r")
    List<String> finDistinctRoomTypes();
}
