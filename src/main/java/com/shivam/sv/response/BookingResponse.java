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
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalNumberOfGuests;
    private int numOfAdults;
    private int numOfChildren;
    private String bookingConfirmationCode;
    private Room room;

    public BookingResponse(Long bookingId, LocalDate checkInDate,
                           LocalDate checkOutDate, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
