package kr.ac.snu.ads.facetracking;

public class FaceTrackingMain {
    public static void main(String s[]) throws java.io.IOException
    {
    	String groupName = "test";
    	FaceDetectingRunnerInterface detector =  new VieweeCounter(); //new FaceTrackingDemo();
    	
    	VieweesRepository repo = new VieweesRepository(groupName);
    	detector.setRepository(repo);
    	
    	detector.run();
        System.exit(0);
    } 
}
