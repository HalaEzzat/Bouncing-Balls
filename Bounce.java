	import java.util.*;
	import java.awt.*;
	import java.applet.Applet;
	import java.util.Random;

 class Ball {

    private float x;
    private float y;
    private float diametar;
    Color color;
    private double xSpeed;
    private double ySpeed;

    public Ball(float x, float y, float diameter, Color color) {
        this.x = x;
        this.y = y;

        this.diametar = diameter;
        this.color = color;
    }

   public Ball(){}

    public void move(float maxX,float maxY) {
        setX((float) (getX() + getxSpeed()));

        if (getX() - getDiametar() / 2 < 0) {
            setX(getDiametar() / 2);
            setxSpeed(-getxSpeed());
        } else if (getX() + getDiametar() / 2 > maxX) {
            setxSpeed(-getxSpeed());
        }

        setY((float) (getY() + getySpeed()));

        if (getY() - getDiametar() / 2 < 0) {
            setySpeed(-getySpeed());
        } else if (getY() + getDiametar() / 2 > maxY) {
            setY(maxY - getDiametar() / 2);
            setySpeed(-getySpeed());
        }
    }

   
	 public Color getColor()
	 {
		 return color;
	 }
    public double getxSpeed() {
        return xSpeed;
    }

   
    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

   
    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public float getX() {
        return x;
    }

   
    public void setX(float x) {
        this.x = x;
    }

   
    public float getY() {
        return y;
    }

    
    public void setY(float y) {
        this.y = y;
    }

    public float getDiametar() {
        return diametar;
    }

    
    public void setDiametar(float diameter) {
        this.diametar = diameter;
    }
}



public class Bounce extends Applet implements Runnable {

    Ball b1;
    boolean going = true;
	Ball b;	

    Thread animator;
    Ball b2;
    double deltaX;
    double deltaY;
    double distance;
	boolean flag;
	ArrayList<Ball> balls;
	Random rand ;
    public void init() 
	{
		balls=new ArrayList<Ball>();  
		flag=false;
        b1 = new Ball(50, 80, 30, Color.red);
        b1.setX(50);
        b1.setY(80);
        b1.setxSpeed(4);
        b1.setySpeed(2);

        b2 = new Ball(50, 80, 30, Color.red);
        b2.setX(150);
        b2.setY(80);
        b2.setxSpeed(4);
        b2.setySpeed(2);

        

        animator = new Thread(this);
        animator.start();

    }

    
	@Override
    public void paint(Graphics g) {

     
	    g.fillOval((int)b1.getX()-(int)b1.getDiametar(),(int) b1.getY()-(int)b1.getDiametar(),(int) b1.getDiametar(), (int)b1.getDiametar());
        g.setColor(Color.red);
	    g.fillOval((int)b2.getX()-(int)b2.getDiametar(),(int) b2.getY()-(int)b2.getDiametar(),(int) b2.getDiametar(), (int)b2.getDiametar());
        for(Ball myb:balls)
		{ 
			g.setColor(myb.getColor());
			g.fillOval((int)myb.getX()-(int)myb.getDiametar(),(int) myb.getY()-(int)myb.getDiametar(),(int) myb.getDiametar(), (int)myb.getDiametar());
		}
        

    }

    @Override
    public void run() 
	{
        while (going) 
		{
            b1.move(getWidth(),getHeight());
            b2.move(getWidth(),getHeight());
			flag=checkCollision();
			if(flag)
			{
				rand=new Random();
				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float bl = rand.nextFloat();

				b = new Ball(50, 80, 30, new Color(r, g, bl));
				b.setX(150);
				b.setY(80);
				b.setxSpeed(4);
				b.setySpeed(2);
				balls.add(b);  
			}
			for(Ball myb:balls)
			{  
				myb.move(getWidth(),getHeight());  
			}  

            repaint();

            try 
			{
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
        }
    }

    public boolean checkCollision() {

        deltaX = Math.abs(b1.getX() - b2.getX());
        deltaY = Math.abs(b1.getY() - b2.getY());
        distance = deltaX * deltaX + deltaY * deltaY;
        
        if (distance < (b1.getDiametar() / 2 + b2.getDiametar() / 2) * (b1.getDiametar() / 2 + b2.getDiametar() / 2)) {

            double newxSpeed1 = (b1.getxSpeed() * (4 - 7) + (2 * 7 * b2.getxSpeed())) / 11;

            double newxSpeed2 = (b2.getxSpeed() * (7 - 4) + (2 * 4 * b1.getxSpeed())) / 11;

            double newySpeed1 = (b1.getySpeed() * (4 - 7) + (2 * 7 * b2.getySpeed())) / 11;

            double newySpeed2 = (b2.getySpeed() * (7 - 4) + (2 * 4 * b1.getySpeed())) / 11;

            b2.setxSpeed(newxSpeed2);
            b2.setySpeed(newySpeed2);
            b1.setxSpeed(newxSpeed1);
            b1.setySpeed(newySpeed1);

			
            return true;
        }
		else{
			return false;
		}
    }

}
