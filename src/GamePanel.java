import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// class to generate the Main Panel that will display the game

public class GamePanel extends JPanel implements Runnable {
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int) (GAME_WIDTH * (5. / 9));

	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle p1;
	Paddle p2;
	Ball ball;
	Score score;

	GamePanel() {

		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newBall() {
		// random = new Random();
		ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER,
				BALL_DIAMETER);
	}

	public void newPaddles() {
		p1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		p2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT,
				2);
	}

	@Override
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}

	public void draw(Graphics g) {
		p1.draw(g);
		p2.draw(g);
		ball.draw(g);
		score.draw(g);
	}

	public void move() {
		p1.move();
		p2.move();
		ball.move();
	}

	public void collision() {
		// limit pddles in the frame / panel
		if (p1.y <= 0)
			p1.y = 0;
		if (p1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			p1.y = GAME_HEIGHT - PADDLE_HEIGHT;

		if (p2.y <= 0)
			p2.y = 0;
		if (p2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			p2.y = GAME_HEIGHT - PADDLE_HEIGHT;

		// bounce the ball off the window top and bottom edges

		if (ball.y <= 0)
			ball.setYDirection(-ball.yVelocity);
		if (ball.y >= GAME_HEIGHT - BALL_DIAMETER)
			ball.setYDirection(-ball.yVelocity);

		// check if the ball bounces off the paddle

		if (ball.intersects(p1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // increases the speed of the ball
			if (ball.yVelocity > 0)
				ball.yVelocity++; // increases the speed of the ball if the ball goes down
			else
				ball.yVelocity--; // increases the speed of the ball if the ball goes up
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if (ball.intersects(p2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // increases the speed of the ball
			if (ball.yVelocity > 0)
				ball.yVelocity++; // increases the speed of the ball if the ball goes down
			else
				ball.yVelocity--; // increases the speed of the ball if the ball goes up
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		
		// chack if the ball has went past the left / right edge ====>>> a point for p1 / p2
		
		if(ball.x <= 0) {
			score.scoreP2++;
			newPaddles();
			newBall();
			System.out.println(score.scoreP1 + " " + score.scoreP2);
		}
		
		if(ball.x >= GAME_WIDTH - BALL_DIAMETER) {
			score.scoreP1++;
			newPaddles();
			newBall();
			System.out.println(score.scoreP1 + " " + score.scoreP2);
		}
		
	}

	@Override
	public void run() {
		// basic game loop

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				collision();
				repaint();
				delta--;
			}
		}
	}

	// action listener
	public class AL extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			p1.keyPressed(e);
			p2.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			p1.keyReleased(e);
			p2.keyReleased(e);
		}
	}

}
