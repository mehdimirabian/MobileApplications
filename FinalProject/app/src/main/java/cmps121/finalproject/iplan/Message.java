package cmps121.finalproject.iplan;

/**
 * Created by mahdi_000 on 3/4/2016.
 */
import java.io.Serializable;
public class Message implements Serializable {

        String name;
        String date;
        String time;
        String address;
        String description;

        public Message(){
            name = null;
            date = null;
            time = null;
            address = null;
            description = null;
        }

    }


