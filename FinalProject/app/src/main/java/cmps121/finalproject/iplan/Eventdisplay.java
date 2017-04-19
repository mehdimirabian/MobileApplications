package cmps121.finalproject.iplan;

import android.util.SparseArray;

/**
 * Created by sushilpatel on 3/8/16.
 */
public class Eventdisplay {
    public static SparseArray<Group> groups = new SparseArray<Group>();
    public static int listCounter =0;

    public static void displayItems(Message objMess){

        Group group = new Group(objMess.name);
        group.children.add(objMess.date);
        group.children.add(objMess.time);
        group.children.add(objMess.address);
        group.children.add(objMess.description);

        groups.append(listCounter, group);
        listCounter++;


    }
}
