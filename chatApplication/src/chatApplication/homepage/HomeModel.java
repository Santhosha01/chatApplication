package chatApplication.homepage;

import java.util.List;
import java.util.Scanner;

import chatApplication.database.Database;
import chatApplication.model.Messages;
import chatApplication.model.User;

public class HomeModel {

	private HomeView homeView;
	static Scanner sc = new Scanner(System.in);

	public HomeModel(HomeView homeView) {
		this.homeView = homeView;
	}

	public void storeSendedMsg(String name, String frnd, String msg) {
		Messages m = new Messages();
		m.setSendUserName(name);
		m.setRecievedUserName(frnd);
		m.setMessage(msg);
		User sender = getUser(name);
		User receiver = getUser(frnd);
		if(receiver==null) {
			System.out.println(frnd +" didnt Accept the friends request");
		}
		else {
		sender.addSendedmessages(m);
        receiver.addRecievedmessages(m);
        System.out.println("Message Sent Successfully");
		}
	}
	public User getUser(String name) {
		for(User user : Database.getInstance().getUsers()) {
			if(user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	public boolean checkFriend(String name, String frnd) {
		List<User> users = Database.getInstance().getUsers();
		for (User u : users) {
			if (u.getName().equals(name)) {
				for (String s : u.getFriends()) {
					if (s.equals(frnd)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void friendRequest(String name) {
		System.out.println("Enter friend Name you want to Make a Request");
		String frnd = sc.nextLine();
		List<User> users = Database.getInstance().getUsers();
		for (User u : users) {
			if (u.getName().equals(name)) {
				u.setFriends(frnd);
				Database.getInstance().setUsersfriends(u);
			}
		}
		System.out.println("Friends Request Sent Successfully");
	}
}
