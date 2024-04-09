package com.shivam.sv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "check_In")
    private LocalDate checkInDate;

    @Column(name = "check_Out")
    private LocalDate checkOutDate;

    @Column(name = "total_guests")
    private int totalNumberOfGuests;

    @Column(name = "adults")
    private int numberOfAdults;

    @Column(name = "children")
    private int numberOfChildren;

    @Column(name = "guest_FullName")
    private String guestName;

    @Column(name = "guest_Email")
    private String guestEmail;

    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    // Method to calculate the total number of guests
    private void calculateNumberOfGuests() {
        this.totalNumberOfGuests = this.numberOfAdults + this.numberOfChildren;
    }

    // Setter for the number of adults, also recalculates the total number of guests
    public void setNumOfAdults(int numOfAdults) {
        this.numberOfAdults = numOfAdults;
        this.calculateNumberOfGuests();
    }

    // Setter for the number of children, also recalculates the total number of guests
    public void setNumOfChildren(int numOfChildren) {
        this.numberOfChildren = numOfChildren;
        this.calculateNumberOfGuests();
    }

    // Setter for the booking confirmation code
    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
