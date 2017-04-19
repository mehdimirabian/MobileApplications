package pack.location.chat.com.chatmodule.adapter;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import pack.location.chat.com.chatmodule.R;
import pack.location.chat.com.chatmodule.model.Message;
import pack.location.chat.com.chatmodule.utils.SharePreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ChatAdapter extends ArrayAdapter<Message>
{
	public ArrayList<Message> chatMessages=new ArrayList<Message>();
	
	
	boolean incoming=false;
	boolean uploadImage=false;
	LayoutInflater vi;
	private SharePreference set;
	
	
	

	private Context mContext;

	public ChatAdapter(Context context, ArrayList<Message> chatMessages)
	{
		super(context, R.layout.list_item_message,chatMessages);

		this.mContext = context;
		
		set = new SharePreference(context);
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//System.out.println(" in constructor of chat adapter");
		this.chatMessages = chatMessages;
	


	}

	@Override
	public int getCount() {
		if (chatMessages != null) 
		{
			//System.out.println("size of chatmessage" +chatMessages.size());
			return chatMessages.size();
		} else {
			return 0;
		}
	}

	

	@Override
	public long getItemId(int position) 
	{
		return position;
	}


	public  void notifyData() 
	{
		// TODO Auto-generated method stub
		notifyDataSetChanged();

	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//MessageData chatMessage = getItem(position);


		if (convertView == null) {
			convertView = vi.inflate(R.layout.list_item_message, null);
			holder = createViewHolder(convertView);
			convertView.setTag(holder);
			 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(chatMessages.get(position).getUser_id().trim().equalsIgnoreCase(set.getString(set.userId)))
		{
			incoming= true;
			holder.txtMessagePerson.setText("me");
		}
		else
		{
			incoming=false;
			holder.txtMessagePerson.setText(""+chatMessages.get(position).getNickname());
		}


		

		holder.txtMessage.setText(chatMessages.get(position).getMessage());
		holder.txtMessageDate.setText(getTimeFormate(chatMessages.get(position).getTimestamp()));


		setAlignment(holder,incoming);
		
		

		return convertView;
	}



	private void setAlignment(final ViewHolder holder,boolean incoming) 
	{

		if(incoming)
		{
			holder.contentWithBG.setBackgroundResource(R.drawable.round_corner_text_view_one);
			holder.txtMessageDate.setGravity(Gravity.RIGHT);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			holder.content.setLayoutParams(lp);

			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
			layoutParams.gravity = Gravity.RIGHT;
			holder.contentWithBG.setLayoutParams(layoutParams);
			holder.contentWithBG.setPadding(5,5,5,5);
			//int maxwidth=(int) (300);
			//holder.txtMessage.setMaxWidth(maxwidth);

			holder.txtMessage.setTextColor(Color.BLACK);
			holder.txtMessageDate.setTextColor(Color.BLACK);
			holder.txtMessageDate.setAlpha(0.6f);
			// holder.txtMessage.setPadding(50, 10, 50, 10);
			holder.txtMessage.setTextSize(16);
			holder.txtMessageDate.setTextSize(10);
		}
		else 
		{
			holder.contentWithBG.setBackgroundResource(R.drawable.round_corner_text_view_two);
			holder.txtMessageDate.setGravity(Gravity.RIGHT);

			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
			layoutParams.gravity = Gravity.LEFT;
			holder.contentWithBG.setLayoutParams(layoutParams);
			//int maxwidth=(int) (300);
			//holder.txtMessage.setMaxWidth(maxwidth);
			holder.contentWithBG.setPadding(5,5,5,5);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			holder.content.setLayoutParams(lp);

			holder.txtMessage.setTextColor(Color.BLACK);
			holder.txtMessageDate.setTextColor(Color.BLACK);
			holder.txtMessageDate.setAlpha(0.6f);
			// holder.txtMessage.setPadding(20, 10, 150, 10);
			holder.txtMessage.setTextSize(16);
			holder.txtMessageDate.setTextSize(10);

		}
	}

	private ViewHolder createViewHolder(View v) 
	{
		ViewHolder holder = new ViewHolder();
		holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
		holder.txtMessagePerson = (TextView) v.findViewById(R.id.txtMessagePerson);
		
		holder.txtMessageDate = (TextView) v.findViewById(R.id.txtMessageDate);
		
		
		holder.progressbar = (ProgressBar) v.findViewById(R.id.progress);
		holder.progressbar.setVisibility(View.GONE);

		holder.content = (LinearLayout) v.findViewById(R.id.content);

		holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView txtMessage;
		private TextView txtMessagePerson;
		private TextView txtMessageDate; 

		private ProgressBar  progressbar; 
		private LinearLayout content;
		private LinearLayout contentWithBG;
		
	}







	private String getTimeFormate(String input)
	{
		//Input date in String format
		//String input = "15/02/2014 22:22:12";
		//Date/time pattern of input date
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
		//Date/time pattern of desired output date
		DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");

		/*Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		Log.d("Time zone", "=" + tz.getID());
*/
		outputformat.setTimeZone(TimeZone.getDefault());
		Date date = null;
		String output = null;
		try{
			//Conversion of input String to date
			date= df.parse(input);
			//old date format to new date format
			output = outputformat.format(date);
			System.out.println(output);
		}catch(ParseException pe){
			pe.printStackTrace();
		}

//		Date fDate = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		fDate = simpleDateFormat.parse(date);
//		CharSequence tttt = DateUtils.getRelativeTimeSpanString(mContext, System.currentTimeMillis() - date.getTime());


		return output;
	}





















}
