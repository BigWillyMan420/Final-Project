
import java.awt.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;

import javax.swing.*;
public class Bird
{
    private Frame frame;
    private double pos;
    private double horizontalPos;
    private double vertical_speed = 0.0;
    private int pipes;
    private int points;
    private ArrayList<Pipe> pipeArray = new ArrayList<Pipe>();

    private final double GRAVITY = 9.8;
    private final double TERMINAL_VELOCITY = 50;
    private final double HORIZONTAL_POSITION = frame.getWidth() * 1/5;
    private final int X_MARGIN = 0;
    private final int TOP_Y_MARGIN = 0;
    private final int BOTTOM_Y_MARGIN = 0;
    
    public Bird()
    {
        frame = new Frame();
        pos = frame.getHeight()/2;
        horizontalPos = HORIZONTAL_POSITION;
        points = 0;
        System.out.println(pos);
        
    }
    
    public void fall()
    {
        this.vertical_speed = this.vertical_speed + GRAVITY;
        if(this.vertical_speed >= TERMINAL_VELOCITY)
        {
            this.vertical_speed = TERMINAL_VELOCITY;
        }
        this.pos = this.pos + vertical_speed; //down is positive in java
    }
    
    public void createPipe()
    {
    	if(pipeArray.get(points % 2).getXPos() < frame.getWidth() * 7/10) {
    		pipeArray.add(new Pipe());
    		
    	}
    	
    	if(pipeArray.get(pipeArray.size() - 1).getXPos() == 0) {
    		pipeArray.remove(pipeArray.size() - 1);
    		
    	}
    	
    	
    }
    
    public void passPipe() {
    	if(this.horizontalPos > pipeArray.get(points % 2).getXPos) {
    		this.points += 1;
    		
    		
    	}
    }
    
    public boolean endGame() {
    	
    	if((this.pos > TOP_Y_MARGIN || this.pos < BOTTOM_Y_MARGIN)
    			&& this.horizontalPos == pipeArray.get(points % 2).getXPos + X_MARGIN) {
    		
    		return true;
    	}
    	
    	return false;
    }
    
    public void spacebar() {
    	
    	this.pos += 50;
    	
    	
    }
    
}
