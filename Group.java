import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Group implements  ITestable{
    private int groupId;
    HashSet<Hotel> hotels;

    public Group(int id){
        hotels = new HashSet<Hotel>();
        groupId = id;
    }



    public void addHotelToGroup(Hotel hotel){
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
        Iterator<Hotel> it = hotels.iterator();
        int numOfHotels = hotels.size();
        ArrayList <Integer> hotelGroupsID  = new ArrayList<Integer>();
        Hotel hot = null;
        while(it.hasNext()){
            hot = it.next();
            if(hotelGroupsID.contains(hot.getGroup().groupId)){
                return false ;
            }
            hotelGroupsID.add(hot.getGroup().groupId);
        }
        return  true;
    }
    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}
