package com.shivam.sv.response;

import com.shivam.sv.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    // Fields representing booking information
    private String guestEmail;
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalNumberOfGuests;
    private int numOfAdults;
    private int numOfChildren;
    private String bookingConfirmationCode;

    // Changed the type of room field to RoomResponse for consistency
    private RoomResponse room; // Changed from Room to RoomResponse

    private String guestFullName;

    // Constructor for basic booking information
    public BookingResponse(Long bookingId, LocalDate checkInDate,
                           LocalDate checkOutDate, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    // Constructor for complete booking information including guest details and room information
    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName,
                           String guestEmail, int numOfAdults, int numOfChildren,
                           int totalNumberOfGuests, String bookingConfirmationCode, RoomResponse room) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.guestFullName=guestFullName;
        this.guestEmail=guestEmail;
        this.numOfAdults=numOfAdults;
        this.numOfChildren=numOfChildren;
        this.totalNumberOfGuests=totalNumberOfGuests;
        this.room = room; // Setting RoomResponse object for room field
    }
}
