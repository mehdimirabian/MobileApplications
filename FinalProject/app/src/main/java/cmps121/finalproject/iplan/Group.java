package cmps121.finalproject.iplan;

/**
 * Created by sushilpatel on 3/8/16.
 */

import java.util.List;
import java.util.ArrayList;

public class Group {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string){
        this.string = string;
    }


}
