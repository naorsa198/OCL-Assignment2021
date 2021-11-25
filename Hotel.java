import java.util.*;

public class Hotel implements  ITestable{
    private String name;
    private HashMap<Client, ReservationSet> allReservation;
    private HashMap<Service, HotelService> services;
    private HashMap<Integer,Room> rooms;
    private String city;
    private Group group;
    private int rate;



    public Hotel(String city, String name,int rate){
        this.city = city;
        this.name = name;
        this.rate = rate;
        rooms = new HashMap<Integer,Room>();
        allReservation = new HashMap<Client, ReservationSet>();
        services = new HashMap<Service, HotelService>();

    }

    public void addReservationSet(Client client,ReservationSet reservationSet){
        allReservation.put(client,reservationSet);
    }

    public void addService(Service service, HotelService hotelService){
        services.put(service,hotelService);
    }

    public void addRoom(int roomNumber, Room room){
        rooms.put(roomNumber,room);
    }


    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public HashMap<Client, ReservationSet> getAllReservation(){return allReservation;}

    public HashMap<Service, HotelService> getServices(){return services;}

    public int getRate(){return rate;}

    @Override
    public boolean checkConstraints() {

    return Constraint2() && Constraint6() && Constraint7() && Constraint10() && Constraint11();

    }

    public static boolean checkAllIntancesConstraints(Model model){
        HashSet<Hotel> allInstances = model.HotelAllInstances();
        for (Hotel hotel : allInstances) {
            if (!hotel.checkConstraints()) {
                return false;
            }
        }
        return true;
    }


    private boolean Constraint2() {
        HashMap<Client, Integer> reservationCount = new HashMap<>();
        Set<Client> clientSet;
        clientSet = allReservation.keySet();
        for (Client client : clientSet) {
            reservationCount.put(client, allReservation.get(client).getReservations().size());
        }
        ArrayList<Client> aboveFive = new ArrayList<>();
        for (Client client : reservationCount.keySet()) {
            if (reservationCount.get(client) >= 5)
                aboveFive.add(client);
        }
        int counter = 0;
        for (Client client : aboveFive) {
            ReservationSet reservationSet = this.allReservation.get(client);
            for (Reservation reservation : reservationSet.getReservations())
                if (reservation.getRoomCategory().getType() == RoomCategory.RoomType.VIP) {
                    counter++;
                    break;
                }
        }
        return counter == aboveFive.size();
    }

    private boolean Constraint6() {
        int numRoomsInHotel = this.rooms.size();
        int VipRooms = 0;
        for (Room room : this.rooms.values()) {
            if (room.getRoomCategory() == null || room.getRoomCategory().getType() == null)
                return false;
            VipRooms= (room.getRoomCategory().getType() == RoomCategory.RoomType.VIP) ? VipRooms+1 : VipRooms+0;
        }
        return VipRooms <= 0.1 * numRoomsInHotel;
    }

    private boolean Constraint7() {
        String cityName = "las vegas";
        if (!this.city.equals(cityName.toUpperCase()) && !this.city.equals(cityName)) return true;
        for (Client client : this.allReservation.keySet()) {
            if (client.getAge() < 21) return false;
        }
        return true;
    }


    private boolean Constraint10() {
        if (this.rate > 5){
            if (this.allReservation.values().size() == 0) return true;
        ArrayList<Review> rlst = new ArrayList<>();
        for (ReservationSet reservationSet : this.allReservation.values()) {
            for (Reservation reservation : reservationSet.getReservations()) {
                if (reservation.getBookings() == null) break;
                if (reservation.getBookings().getReview() == null) break;
                rlst.add(reservation.getBookings().getReview());
            }
        }
        float sum = 0;
        for (Review review : rlst) {
            sum += review.getRank();
        }
        return sum / rlst.size() > 7.5;
    }
    else
        return true;
    }

    private boolean Constraint11() {
        Iterator<Service> it = services.keySet().iterator();
        ArrayList<String> hotelserv = new ArrayList<String>();
        Service ser = null;
        while (it.hasNext()) {
            ser = it.next();
            if (hotelserv.contains(ser.getServiceName().toLowerCase())) {
                return false;
            }
            hotelserv.add(ser.getServiceName().toLowerCase());
        }
        return true;
    }





}