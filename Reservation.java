import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;

public class Reservation implements  ITestable {
    private int id;
    private RoomCategory roomCategory;
    private Date orderDate;
    private Date requestDate;
    private Booking booking;
    private ReservationSet reservationSet;


    public Reservation(Date ordDate, Date reqDate, int id) {
        this.id = id;
        orderDate = ordDate;
        requestDate = reqDate;
    }

    public void setReservationSet(ReservationSet reservationSet){
        this.reservationSet = reservationSet;
    }


    public void addRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public void addBooking(Booking _booking) {
        booking = _booking;
    }


    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Booking getBookings() {
        return booking;
    }

    public int getId() {
        return id;
    }

    public ReservationSet getReservationSet(){return reservationSet;}

    @Override
    public boolean checkConstraints() {

        return constraint3();
    }

    public static boolean checkAllIntancesConstraints(Model model) {
        HashSet<Reservation> allInstances = model.ReservationAllInstances();
        for (Reservation reservation : allInstances) {
            if (!reservation.checkConstraints()) {
                return false;
            }
        }
        return true;
    }

    private boolean constraint3() {
        if (this.reservationSet == null ||
                this.reservationSet.getHotel() == null ||
                this.booking == null ||
                this.booking.getRoom() == null ||
                this.booking.getRoom().getHotel() == null||
                this.reservationSet.getHotel().getName() == null) return false;

        Hotel rsHotel = this.reservationSet.getHotel();
        Hotel rsBooking = this.booking.getRoom().getHotel();
        return rsHotel == rsBooking;
    }
}
    