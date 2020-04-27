import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Shield {
	int x, y, w, h, hits;
	
	ArrayList <Rectangle> shieldRects = new ArrayList<Rectangle>();
	ArrayList <Rectangle> shields = new ArrayList<Rectangle>();
	
	boolean hit;
	
	public Shield(int x, int y, int w, int h, int hits){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.hits = hits;
		
	}
	
	public ArrayList <Rectangle> getShields(){
    	return shieldRects;
    }
    
}