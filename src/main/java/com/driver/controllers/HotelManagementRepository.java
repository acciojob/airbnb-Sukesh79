package com.driver.controllers;

import com.driver.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class HotelManagementRepository {
    private Map<String, Hotel> hotelMap = new HashMap<>();
    private Map<Integer, User> userMap = new HashMap<>();
    private Map<String, Booking> bookingMap = new HashMap<>();
    public void addHotel(Hotel hotel){
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public Optional<Hotel> getHotelByName(String hotelName){
        if(hotelMap.containsKey(hotelName))
            return Optional.of(hotelMap.get(hotelName));
        else return Optional.empty();
    }
    public Map<String, Hotel> getHotelMap(){
        return hotelMap;
    }
    public void addUser(User user){
        userMap.put(user.getaadharCardNo(), user);
    }

    public Optional<User> getUserByAadhar(Integer aadharNo){
        if(userMap.containsKey(aadharNo))
            return Optional.of(userMap.get(aadharNo));
        else return Optional.empty();
    }

    public void addBooking(Booking booking){
        bookingMap.put(booking.getBookingId(), booking);
        Hotel hotel = hotelMap.get(booking.getHotelName());
        hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public Map<String, Booking> getBookingMap(){
        return bookingMap;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelMap.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        Set<Facility> set = new HashSet<>(oldFacilities);

        for(Facility facility : newFacilities){
            if(!set.contains(facility)){
                oldFacilities.add(facility);
                set.add(facility);
            }
        }

        hotel.setFacilities(oldFacilities);
        hotelMap.put(hotelName, hotel);
        return hotel;
    }
}
