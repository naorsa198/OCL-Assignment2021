import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

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
        // check for 2 hotels same group

    }

    public static boolean checkAllIntancesConstraints(Model model) {
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

}