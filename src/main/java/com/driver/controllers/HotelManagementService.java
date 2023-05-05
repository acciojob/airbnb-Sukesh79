package com.driver.controllers;
import com.driver.model.*;
import com.driver.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class HotelManagementService {
    @Autowired
    HotelManagementRepository hotelManagementRepository;

    UUID uuid = UUID.randomUUID();
    public String addHotel(Hotel hotel) {
        if(hotel == null || hotel.getHotelName() == null) return "FAILURE";

        Optional<Hotel> hotelOptional = hotelManagementRepository.getHotelByName(hotel.getHotelName());

        if(hotelOptional.isEmpty()){
            hotelManagementRepository.addHotel(hotel);
            return "SUCCESS";
        }
        else return "FAILURE";
    }

    public Integer addUser(User user) {
        hotelManagementRepository.addUser(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        Map<String, Hotel> hotelMap = hotelManagementRepository.getHotelMap();
        int facilites = 0;
        String HotelName = "";

        for(Hotel hotel : hotelMap.values()){
            int size = hotel.getFacilities().size();
            if(size > facilites){
                facilites = size;
                HotelName = hotel.getHotelName();
            }
            else if(size == facilites){
                if(HotelName.equals("")){
                    HotelName = hotel.getHotelName();
                }
                else HotelName = hotel.getHotelName().compareTo(HotelName) < 0 ? hotel.getHotelName() : HotelName;
            }
        }

        return HotelName;
    }

    public int bookARoom(Booking booking) {
        Optional<Hotel> hotelOpt = hotelManagementRepository.getHotelByName(booking.getHotelName());
        if(hotelOpt.isEmpty()) return -1;
        Hotel hotel = hotelOpt.get();

        if(hotel.getAvailableRooms() < booking.getNoOfRooms()) return -1;

        String randomString = uuid.toString();
        booking.setBookingId(randomString);

        int amountToBePaid = booking.getNoOfRooms() * hotel.getPricePerNight();
        booking.setAmountToBePaid(amountToBePaid);

        hotelManagementRepository.addBooking(booking);
        return amountToBePaid;
    }

    public int getBookings(Integer aadharCard) {
        Map<String, Booking> bookingMap = hotelManagementRepository.getBookingMap();
        int count = 0;

        for(Booking booking : bookingMap.values()){
            if(booking.getBookingAadharCard() == aadharCard) count++;
        }

        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}
