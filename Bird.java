
/**
 * Write a description of class Bird here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import javax.swing.*;
public class Bird
{
    private Frame frame;
    private double pos;
    private double vertical_speed = 0.0;

    private final double GRAVITY = 9.8;
    private final double TERMINAL_VELOCITY = 100;

    public Bird()
    {
        frame = new Frame();
        pos = frame.getHeight()/2;
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
}
