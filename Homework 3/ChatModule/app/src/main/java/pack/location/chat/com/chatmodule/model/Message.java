package pack.location.chat.com.chatmodule.model;

public class Message {
	
	private String message_id;
	private int senderType;
	private String timestamp;
	private String message;
	private int messageIsSent;
	private String user_id;
	private String nickname;



	public int getMessageIsSent() {
		return messageIsSent;
	}
	public void setMessageIsSent(int messageIsSent) {
		this.messageIsSent = messageIsSent;
	}

	public int getSenderType() {
		return senderType;
	}
	public void setSenderType(int senderType) {
		this.senderType = senderType;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
