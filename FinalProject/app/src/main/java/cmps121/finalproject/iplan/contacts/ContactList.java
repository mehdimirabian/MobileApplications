package cmps121.finalproject.iplan.contacts;

import java.util.ArrayList;

/**
 * Created by katy021teh on 3/6/16.
 */
public class ContactList {


    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    //function to get the size of the current guest list
    public int getCount()
    {
        return this.contacts.size();
    }

    //function to add a contact to the guest list
    public void addContact(Contact c)
    {
        this.contacts.add(c);
    }

    //function to remove a contact from the current guest list

    public void removeContact(String number)
    {
        for(int i=0;i<this.getCount();i++)
        {
            if(number.equals(this.contacts.get(i).number))
            {
                this.contacts.remove(this.contacts.get(i));
            }
        }
    }


    //map each contact using it's id
    public Contact getContact(int id)
    {
        Contact tmp=null;
        for(int i=0;i<this.getCount();i++)
        {
            if(id==Integer.parseInt(this.contacts.get(i).id))
            {
                tmp = new Contact(this.contacts.get(i).id,this.contacts.get(i).name,this.contacts.get(i).number);
            }
        }
        return tmp;
    }


    //retrieve contacts from the array list
    public ArrayList<Contact> getContacts()
    {
        return contacts;
    }


    //store all  contact list in an array
    public void setContacts(ArrayList<Contact> c)
    {
        this.contacts=c;
    }


}
