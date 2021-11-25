import java.lang.reflect.Array;
import java.util.*;

public class Group implements  ITestable {
    private int groupId;
    HashSet<Hotel> hotels;

    public Group(int id) {
        hotels = new HashSet<Hotel>();
        groupId = id;
    }


    public void addHotelToGroup(Hotel hotel) {
        hotels.add(hotel);
    }

    //getters

    public int getGroupId() {
        return groupId;
    }

    public HashSet<Hotel> getHotels() {
        return hotels;
    }

    @Override
    public boolean checkConstraints() {
        return const1() && const4();

    }

    public static boolean checkAllIntancesConstraints(Model model){
        HashSet<Group> allInstances = model.GroupAllInstances();
        for(Group group : allInstances){
            if(!group.checkConstraints()){
                return false;
            }
        }
        return true;
    }

    // check for 2 hotels same group
    private boolean const1() {
        Iterator<Hotel> it = hotels.iterator();
        int numOfHotels = hotels.size();
        ArrayList<String> hotelGroupsID = new ArrayList<String>();
        Hotel hot = null;
        while (it.hasNext()) {
            hot = it.next();
            if (hotelGroupsID.contains(hot.getCity().toLowerCase())) {
                return false;
            }
            hotelGroupsID.add(hot.getCity().toLowerCase());
        }
        return true;
    }


    private boolean const4() {
        List<Set<Service>> hotelsServices = new ArrayList<>();
        for (Hotel hotel : hotels) {
            hotelsServices.add(hotel.getServices().keySet());
        }

        if( hotelsServices.stream().distinct().count() <= 1)
            return true;
        return false;
    }
}