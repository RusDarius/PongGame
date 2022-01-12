import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// class that generates the ball

public class Ball extends Rectangle{
	
	Random rand;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 2;
	
	Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		rand = new Random();
		int randomXDir = rand.nextInt(2);
		if(randomXDir == 0) 
			randomXDir--;
		setXDirection(randomXDir * initialSpeed);
		
		int randomYDir = rand.nextInt(2);
		if(randomYDir == 0) 
			randomYDir--;
		setYDirection(randomYDir * initialSpeed);
	}
	
	public void setXDirection(int randXDir) {
		xVelocity = randXDir;
	}
	
	public void setYDirection(int randYDir) {
		yVelocity = randYDir;
	}
	
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
}
