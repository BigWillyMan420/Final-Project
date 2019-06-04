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

public class FlappyBird implements ActionListener, MouseListener, KeyListener, FlappyPlan
{
    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> pipes;
    public int ticks, yVel, score;
    public boolean gameOver, started;
    public Random rand;

    public FlappyBird()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);
        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.setTitle("Flappy Bird by Mike Qu and William Hu");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        pipes = new ArrayList<Rectangle>();

        addPipe(true); //initialize 2 pipes
        addPipe(true);

        timer.start();
    }

    public void addPipe(boolean bool)
    {
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);
        //Rectangle(x,y,width,height) x and y refer to coordinates at top left corner
        if (bool) //initializing pipes at consistent location outside of screen when still alive
        {
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, HEIGHT - height - 120, width, height));//bottom pipe
            pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));//top pipe
        }
        else    //adding more pipes along the way by getting the latest randomly generated pipe
        {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, HEIGHT - height - 120, width, height)); //bottom pipe
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space)); //top pipe
        }
    }

    public void paintPipe(Graphics g, Rectangle pipe) //coloring pipes : )
    {
        g.setColor(Color.green.darker());
        g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        if(gameOver)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,WIDTH,HEIGHT);
        }
    }

    public void jump() //all movements associated with space key
    {
        if (!started)
        {
            started = true;
        }
        else if (!gameOver)
        {
            if (yVel > 0) //clear downward momentum and instantly change to go up
            {
                yVel = 0;
            }
            yVel -= 10; //jump 10 units up
        }
        if (gameOver)
        {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            pipes.clear();
            yVel = 0;
            score = 0;

            addPipe(true);
            addPipe(true);

            gameOver = false;
        }
    }

    
    public void actionPerformed(ActionEvent e) //key pressed
    {
        int speed = 10;
        ticks++;

        if (started)
        {
            //ADDITION/REMOVAL OF PIPES
            for (int i = 0; i < pipes.size(); i++)
            {
                Rectangle pipe = pipes.get(i);
                pipe.x -= speed; //moves all pipes across the frame towards the player
            }
            for (int i = 0; i < pipes.size(); i++)
            {
                Rectangle pipe = pipes.get(i);
                if (pipe.x + pipe.width < 0)//pipe gets deleted if it gets out of view
                {
                    pipes.remove(pipe);
                    if (pipe.y == 0) //if touches ground: game over and stop adding new pipes
                    {
                        addPipe(false);
                    }
                }
            }

            //PHYSICS
            if (yVel < 15) // downward acceleration = 1.0 unit / tick squared and 15 is the terminal velocity
            {
                yVel += 1; 
            }
            bird.y += yVel;

            //SCOREBOARD + RESULTS
            for (Rectangle pipe : pipes)
            {
                if (pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 && bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10)
                {
                    score++;    //bird passes pipe : D score + 1
                }
                if (pipe.intersects(bird))
                {
                    gameOver = true; //pipe touches bird bird dies : (
                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0) //if goes too high or too low
            {
                gameOver = true;
            }

        }

        renderer.repaint(); //refresh frame
    }

    public void repaint(Graphics g)
    {
        g.setColor(Color.cyan); //background
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange.darker());
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        if(!gameOver)
        {
            g.setColor(Color.red);
            g.fillRect(bird.x, bird.y, bird.width, bird.height);
        }
        if(gameOver)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,WIDTH,HEIGHT);
        }

        for (Rectangle column : pipes)
        {
            paintPipe(g, column);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 60));

        if (!started)
        {
            g.drawString("press space to start!", 75, HEIGHT / 2 - 50);
        }

        if (gameOver)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", 1, 100));
            g.drawString("YOU DIED", 100, HEIGHT / 2 - 50);

        }

        if (!gameOver && started)
        {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    public static void main(String[] args)
    {
        flappyBird = new FlappyBird();
    }

    
    public void mouseClicked(MouseEvent e)
    {
        jump();
    }

    
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            jump();
        }
    }

    
    public void mousePressed(MouseEvent e)
    {
    }

    
    public void mouseReleased(MouseEvent e)
    {
    }

    
    public void mouseEntered(MouseEvent e)
    {
    }

    
    public void mouseExited(MouseEvent e)
    {
    }

    
    public void keyTyped(KeyEvent e)
    {

    }

    
    public void keyPressed(KeyEvent e)
    {

    }

}