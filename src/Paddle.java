import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuKeyEvent;

// class that generates the paddles

public class Paddle extends Rectangle {

	int id; // 1 for p1 and 2 for p2
	int yVelocity;
	int speed = 10;

	Paddle(int x, int y, int PADDLE_WIDHT, int PADDLE_HEIGHT, int id) {
		super(x, y, PADDLE_WIDHT, PADDLE_HEIGHT);
		this.id = id;

	}

	public void keyPressed(KeyEvent e) {
		switch (id) {
		case 1:
			if (e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(-speed);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(speed);
				move();
			}
			break;
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(-speed);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(speed);
				move();
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (id) {
		case 1:
			if (e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(0);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(0);
				move();
			}
			break;
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(0);
				move();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(0);
				move();
			}
			break;
		}
	}

	public void setYDirection(int yDir) {
		yVelocity = yDir;
	}

	public void move() {
		y += yVelocity;
	}

	public void draw(Graphics g) {
		if (id == 1)
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
