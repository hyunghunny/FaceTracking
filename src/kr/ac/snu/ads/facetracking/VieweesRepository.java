package kr.ac.snu.ads.facetracking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class VieweesRepository {
	HashMap<Integer, UserPresence> viewees = null;
	private String groupName = null;
	
	public VieweesRepository(String groupName) {
		this.viewees = new HashMap<>();
		this.groupName = groupName;
	}
	
	public void update(ArrayList<Integer> idList) {
		
		// create new viewee object and add to repo if idList have new id
		for (int id : idList) {
			if (this.viewees.containsKey(id) == false) {
				UserPresence presence = new UserPresence(id);
				this.viewees.put(id, presence);
			}
		}

		
		for (int key : this.viewees.keySet()) {
			UserPresence presence = this.viewees.get(key);
			
			if (idList.contains(key) == false) {
				// if current id list doesn't contain previous id,
				// set it leaved when presence is joined before.
				if (presence.getState() == PresenceState.JOINED) {
					presence.setLeaved();
				}
				
			} else {
				// if current id list contain previous id,
				// set it joined again
				presence.setJoined();
			}				
		}
	}
	
	public void printStatus() {
		System.out.println("=============================================");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println(" Updated at " + timeStamp);
		System.out.println("=============================================");
		for (int key : this.viewees.keySet()) {
			UserPresence presence = this.viewees.get(key);
			presence.printStatus();
		}
		
	}
}
