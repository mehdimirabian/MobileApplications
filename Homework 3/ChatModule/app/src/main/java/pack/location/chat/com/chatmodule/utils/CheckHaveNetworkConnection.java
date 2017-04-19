package pack.location.chat.com.chatmodule.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckHaveNetworkConnection 
{

	public static  boolean haveConnectedWifi = false;
	public static  boolean haveConnectedMobile = false;
	   
	public static boolean haveNetworkConnection(Context context) 
	{
	
		haveConnectedWifi = false;
		haveConnectedMobile = false;
		
	    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedWifi = true;
	            }
	        }
	        
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedMobile = true;
	            }
	        }
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	  
	}
	
	public static boolean pingServer(Context context,String url)
	{
		
		boolean pinged = false;
		
		 HttpURLConnection connection = null;
		    try 
		    {
		    	
		    	 HttpURLConnection.setFollowRedirects(false);
		    	   HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		    	   con.setRequestMethod("HEAD");

		    	   con.setConnectTimeout(5000); 

		    	   
		    	   if (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		    	   {
		    		   pinged = true;
		    	   }
		    } 
		    catch (MalformedURLException e) 
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        pinged = false;
		    } 
		   
		    catch (IOException e) 
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        pinged = false;
		    } 
		    catch (Exception e) 
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        pinged = false;
		    } 
		    finally
		    {
		        if (connection != null) 
		        {
		            connection.disconnect();
		        }
		    }
		return pinged;
	}
}
