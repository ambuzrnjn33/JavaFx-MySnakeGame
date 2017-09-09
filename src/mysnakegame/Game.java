/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysnakegame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author AMBUZ RANJAN
 */
public class Game extends JLabel {
    
    public Food mHead=new Food();
    public Timer mTimer=null;
    public Eat mEat=new Eat();
    public Random mRandom=null;
    public ArrayList<Food> Liste=new ArrayList<Food>();
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
        
        
        Graphics2D g2=(Graphics2D)g;
        Rectangle2D rect=new Rectangle2D.Double(0,0,getWidth(),getHeight());
        g2.setColor(Color.black);
        
        g2.setStroke(new BasicStroke(10));
        
        g2.draw(rect);
           g2.setColor(Color.red);
                g2.setFont(new Font("times new roman",Font.BOLD,30));
		g2.drawString("Game Over",255,300);
	System.out.println("Game over");
		g2.drawString("Press ENTER to Restart",180,400);
        
        
        if(Collission()){
                
                g2.setColor(Color.red);
                g2.setFont(new Font("times new roman",Font.BOLD,30));
		g2.drawString("Game Over",255,300);
	System.out.println("Game over");
		g2.drawString("Press ENTER to Restart",180,400);
            }
        
    }
    
    Game()
    {
        mRandom=new Random(System.currentTimeMillis());
        addKeyListener(new SnakeControl());
        setFocusable(true);
        mTimer=new Timer(100,new TimerControl());
        mTimer.start();
        
        Liste.add(mHead);
        for(int i=0;i<8;i++)
        {
            AddQueue();
        }
        add(mEat);
        add(mHead);
    }
    public void AddQueue()
    {
        Food F=Liste.get(Liste.size()-1).CreateBox();
        Liste.add(F);
        add(F);
    }
    public void Add()
    {
        int Width=getWidth()-30-mEat.x;
        int Height=getHeight()-30-mEat.x;
        int PosX=10+Math.abs(mRandom.nextInt())%Width;
        int PosY=10+Math.abs(mRandom.nextInt())%Height;
        
        PosX=PosX-PosX%20;
        PosY=PosY-PosY%20;
        
        for(int i=0;i<Liste.size();i++)
        {
            if((PosX==mHead.getX())&&(PosY==Liste.get(i).getY()))
            {
                Add();
                return;
            }
        }
        
        mEat.setPosition(PosX, PosY);
    }
    public void All()
    {
        for(int i=Liste.size()-1;i>0;i--)
        {
            Food Before=Liste.get(i-1);
            Food After=Liste.get(i);
            
            Liste.get(i).Movement();
            
            After.mMovement=Before.mMovement;
        }
        mHead.Movement();
    }
    
    public boolean Collission()
    {
        int Pen=10;
        int p=getWidth();
        int q=getHeight();
        if(mHead.getX()+mHead.x<=Pen)
            return true;
        if(mHead.getX()+mHead.x>=p-Pen)
            return true;
        if(mHead.getY()+mHead.x<=Pen)
            return true;
        if(mHead.getY()+mHead.x>=q-Pen)
            return true;
        
        for(int i=1;i<Liste.size();i++)
        {
            int X=Liste.get(i).getX();
            int Y=Liste.get(i).getY();
            if((X==mHead.getX())&&(Y==mHead.getY()))
                    return true;
        }
        
        if((mEat.getX()==mHead.getX())&&(mEat.getY()==mHead.getY()))
        {
            AddQueue();
            Add();
        }
        
        return false;
    }
            
    class SnakeControl implements KeyListener
    {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_LEFT)
                    {
                      if(mHead.mMovement!=Movement.RIGHT)
                      mHead.mMovement=Movement.LEFT;
                    }
        
            if(e.getKeyCode()==KeyEvent.VK_RIGHT)
                    {
                      if(mHead.mMovement!=Movement.LEFT)
                      mHead.mMovement=Movement.RIGHT;
                    }
            
            if(e.getKeyCode()==KeyEvent.VK_UP)
                    {
                      if(mHead.mMovement!=Movement.DOWN)
                      mHead.mMovement=Movement.UP;
                    }
            
            if(e.getKeyCode()==KeyEvent.VK_DOWN)
                    {
                      if(mHead.mMovement!=Movement.UP)
                      mHead.mMovement=Movement.DOWN;
                    }
            
            if(e.getKeyCode()==KeyEvent.VK_A)
                    {
                      if(mHead.mMovement!=Movement.RIGHT)
                      mHead.mMovement=Movement.LEFT;
                    }
            if(e.getKeyCode()==KeyEvent.VK_D)
                    {
                      if(mHead.mMovement!=Movement.LEFT)
                      mHead.mMovement=Movement.RIGHT;
                    }
            
            if(e.getKeyCode()==KeyEvent.VK_W)
                    {
                      if(mHead.mMovement!=Movement.DOWN)
                      mHead.mMovement=Movement.UP;
                    }
            
            if(e.getKeyCode()==KeyEvent.VK_S)
                    {
                      if(mHead.mMovement!=Movement.UP)
                      mHead.mMovement=Movement.DOWN;
                    }
            
        }

        public void keyReleased(KeyEvent e) {
        }
        
    }
    
    class TimerControl implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            
            All();
            if(Collission()){
                mTimer.stop();   
            }
        }
        
    }
    }
