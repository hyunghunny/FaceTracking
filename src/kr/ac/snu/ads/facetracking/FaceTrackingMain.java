package kr.ac.snu.ads.facetracking;

public class FaceTrackingMain {
    public static void main(String s[]) throws java.io.IOException
    {
    	String groupName = "test";
    	if (s.length > 0) {
    		groupName = s[0];
    	}
    	FaceDetectingRunnerInterface detector =  new VieweeCounter(); //new FaceTrackingDemo();
    	
    	VieweesRepository repo;
		try {
			repo = new VieweesRepository(groupName);
	    	detector.setRepository(repo);
	    	
	    	detector.run();
	        		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);	
    } 
}
