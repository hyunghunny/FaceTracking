package kr.ac.snu.ads.facetracking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import intel.rssdk.PXCMFaceConfiguration;
import intel.rssdk.PXCMFaceData;
import intel.rssdk.PXCMFaceData.Face;
import intel.rssdk.PXCMFaceData.RecognitionData;
import intel.rssdk.PXCMFaceModule;
import intel.rssdk.PXCMSenseManager;
import intel.rssdk.pxcmStatus;

public class VieweeCounter implements FaceDetectingRunnerInterface {
	private int nFaces = 0; 
	private VieweesRepository repo = null;
	
	public void setRepository (VieweesRepository repo) {
		this.repo = repo;
	}
	
	public void run() {
        PXCMSenseManager senseMgr = PXCMSenseManager.CreateInstance();
        
        senseMgr.EnableFace(null); // XXX:I couldn't know what is exact argument.
        
        PXCMFaceModule face1 = senseMgr.QueryFace();
                
        // Retrieve the input requirements
        //pxcmStatus sts = pxcmStatus.PXCM_STATUS_DATA_UNAVAILABLE; 
        PXCMFaceConfiguration faceConfig = face1.CreateActiveConfiguration();
        faceConfig.SetTrackingMode(PXCMFaceConfiguration.TrackingModeType.FACE_MODE_COLOR_PLUS_DEPTH); //PXCMFaceConfiguration.TrackingModeType.FACE_MODE_COLOR
        faceConfig.detection.isEnabled = true;
        
        //faceConfig.landmarks.isEnabled = true; 
        //faceConfig.pose.isEnabled = true;
        
        
        faceConfig.ApplyChanges();
        faceConfig.Update();
        
        senseMgr.Init();
        
        PXCMFaceData fdata = face1.CreateOutput();
        while (senseMgr.AcquireFrame(true) == pxcmStatus.PXCM_STATUS_NO_ERROR) {
        	
        	// retrieve the face tracking results
        	fdata.Update();
        	
        	
        	int nfaces = fdata.QueryNumberOfDetectedFaces();
        	        	
        	if (nfaces != this.nFaces) {
        		ArrayList<Integer> userIdList = getUserIdList(fdata);
        		if (repo != null) {
            		repo.update(userIdList);
            		repo.printStatus();
        		} else {
            		String timeStamp = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(new Date());
                	System.out.println(nfaces + " faces detected at " + timeStamp);
                	
                	String idString = getIdListString(userIdList);
                	System.out.println("user IDs: " + idString);       			
        		}

            	
        		this.nFaces = nfaces;
        	}        	
        	
        	
        	// resume next frame processing
        	senseMgr.ReleaseFrame();
        }
        fdata.close();
        
	}

	private String getIdListString(ArrayList<Integer> userIdList) {
		String idString = "";
		for (int i = 0; i < userIdList.size(); i++) {
			if (i != 0) {
				idString += ", ";
			}
			int userId = userIdList.get(i);
			idString += userId;
		}
		return idString;
	}

	private ArrayList<Integer> getUserIdList(PXCMFaceData fdata) {
		int nfaces = fdata.QueryNumberOfDetectedFaces();
		ArrayList<Integer> userIdList = new ArrayList<Integer>();        		        		
		for (int i = 0; i < nfaces; i++) {
			Face face = fdata.QueryFaceByIndex(i);
			RecognitionData rdata = face.QueryRecognition();
			
			int userId = face.QueryUserID();
			userIdList.add(userId);                	
		}
		return userIdList;
	}
} 
