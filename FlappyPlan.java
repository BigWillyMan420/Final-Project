import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * Write a description of interface FlappyPlan here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface FlappyPlan
{
    void addPipe(boolean bool);//THANK YOU WILLIAM HU : ) although i had to figure out the numbers
    void jump();//THANK YOU WILLIAM HU : )
    
    void actionPerformed(ActionEvent e); //timer related issues: physics, pipe movement and bird DEATH
    
    void paintPipe(Graphics g, Rectangle pipe);
    void repaint(Graphics g);
}
