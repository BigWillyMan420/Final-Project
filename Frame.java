
/**
 * Write a description of class Bird here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import javax.swing.*;
public class Frame
{
    private double height;
    private double width;

    public Frame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height * 2 / 3;
        width = screenSize.width * 2 / 3;
        
        JFrame frame = new JFrame();
        frame.setSize(new Dimension((int)width,(int)height));
        frame.setVisible(true);
    }

    public double getHeight()
    {
        return height;
    }
    
    public double getWidth()
    {
        return width;
    }
}
