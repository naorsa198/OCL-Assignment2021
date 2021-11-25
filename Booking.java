import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Booking implements  ITestable {
    private Date date;
    private Room room;
    private ArrayList<HotelService> services;
    private Reservation reservation;
    private Review review;


    public Booking(Date a_date, Room a_room) {
        date = a_date;
        room = a_room;
        services = new ArrayList<HotelService>();
    }

    public void addService(HotelService s) {
        services.add(s);
    }

    public void addReview(Review a_review) {
        review = a_review;
    }

    public void addReservation(Reservation r) {
        reservation = r;
    }

    public void assignRoom(Room room) {
        this.room = room;
    }


    // getters

    public Date getDate() {
        return date;
    }

    public Room getRoom() {
        return room;
    }

    public ArrayList<HotelService> getServices() {
        return services;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Review getReview() {
        return review;
    }


    @Override
    public boolean checkConstraints() {
        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        HashSet<Booking> allInstances = model.BookingAllInstances();
        for(Booking booking : allInstances){
            if(!booking.checkConstraints()){
                return false;
            }
        }
        return true;
    }


    private boolean constrain5() {
        if (this.room == null || this.room.getRoomCategory() == null) {
            return false;
        }

        if (this.room.getRoomCategory().getType() == RoomCategory.RoomType.VIP)
            for (HotelService hotelService : this.services) {
                Service serv = hotelService.getService();
                if (!(serv instanceof VipService))
                    return false;
            }
        return true;
    }

    private boolean constrain8() {
        if (this.reservation.getRoomCategory()== null){return false;}
        RoomCategory.RoomType orderedRoom = this.reservation.getRoomCategory().getType();
        RoomCategory.RoomType providedRoom = this.reservation.getBookings().getRoom().getRoomCategory().getType();

        if (orderedRoom == RoomCategory.RoomType.BASIC &&
                (providedRoom == RoomCategory.RoomType.VIP || providedRoom == RoomCategory.RoomType.SUITE)){
            return true;
        }
        if (orderedRoom == RoomCategory.RoomType.SUITE &&
                providedRoom == RoomCategory.RoomType.BASIC){
            return false;
        }

        if (orderedRoom == RoomCategory.RoomType.BASIC &&
                providedRoom == RoomCategory.RoomType.SUITE){
            return true;
        }
        if (orderedRoom == RoomCategory.RoomType.VIP &&
                (providedRoom == RoomCategory.RoomType.BASIC || providedRoom == RoomCategory.RoomType.SUITE)){
            return false;
        }
        if (orderedRoom == RoomCategory.RoomType.BASIC &&
                (providedRoom == RoomCategory.RoomType.VIP || providedRoom == RoomCategory.RoomType.SUITE)){
            return true;
        }
        return true;
    }



}