package kr.ac.snu.ads.facetracking;

import java.lang.System.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;

public class FaceTrackingMain {
    public static void main(String s[]) throws java.io.IOException
    {
    	FaceDetectorInterface detector =  new FaceCounter(); //new FaceTrackingDemo();
    	detector.run();
        System.exit(0);
    } 
}
