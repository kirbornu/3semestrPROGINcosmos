import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

public class SpaceField extends JPanel implements ActionListener, MouseListener, KeyListener {
    static public final double FIELD_WIDTH = (double) (Toolkit.getDefaultToolkit().getScreenSize().width * 3) / 4;
    static public final double FIELD_HEIGHT = (double) Toolkit.getDefaultToolkit().getScreenSize().height;
    GameCore gameCore;
    ButtonsField buttonsField;

    public SpaceField() {
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension((int) FIELD_WIDTH, (int) FIELD_HEIGHT));
        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawCoordinateGrid(g2, gameCore.scale, gameCore.shiftX, gameCore.shiftY);
        drawStars(g2, gameCore.scale, gameCore.shiftX, gameCore.shiftY);
    }

    public void drawCoordinateGrid(Graphics2D g2, double scale, double shiftX, double shiftY){
        g2.setColor(Color.DARK_GRAY);
        g2.draw(new Line2D.Double((FIELD_WIDTH / 2 + shiftX) * scale, 0,
                                  (FIELD_WIDTH / 2 + shiftX) * scale, FIELD_HEIGHT));
        g2.draw(new Line2D.Double(0, (FIELD_HEIGHT / 2 + shiftY) * scale,
                                    FIELD_WIDTH, (FIELD_HEIGHT / 2 + shiftY) * scale));
    }

    public void drawStars(Graphics2D g2, double scale, double shiftX, double shiftY){
        for (Star star : gameCore.stars) {
            g2.setColor(star.color);
            g2.fill(new Ellipse2D.Double(
                    (star.x - star.radius + shiftX) * scale,
                    (star.y - star.radius + shiftY) * scale,
                    star.radius * 2 * scale,
                    star.radius * 2 * scale));
            if(gameCore.selectedStar == star){
                g2.draw(new Rectangle2D.Double(
                        (star.x - star.radius * 1.2 + shiftX) * scale,
                        (star.y - star.radius * 1.2 + shiftY) * scale,
                        star.radius * 2.4 * scale,
                        star.radius * 2.4 * scale
                ));
                buttonsField.setMessageAreaText(String.format("""
                        Название: %s\s
                        Координата X: %s\s
                        Координата Y: %s\s
                        Скорость X: %s\s
                        Скорость Y: %s\s
                        Масса: %s\s
                        """, star.name,
                        Math.round(star.x * 10) / 10.0,
                        Math.round(star.y * 10) / 10.0,
                        Math.round(star.velocityX * 10) / 10.0,
                        Math.round(star.velocityY * 10) / 10.0,
                        Math.round(star.mass * 10) / 10.0));
            }
        }
    }

    public void setGameCore(GameCore gameCore) {this.gameCore = gameCore;}
    public void setButtonsField(ButtonsField buttonsField) {this.buttonsField = buttonsField;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((! gameCore.isGameStop) && gameCore.stars.size() > 1){
            for (Star star : gameCore.stars){
                for (Star anStar : gameCore.stars){
                    if (star != anStar){
                        star.gravityUpdate(anStar);
                    }
                }
            }
            for (Star star : gameCore.stars){
                star.move();
            }
            for (Star star : gameCore.stars){
                for (Star anStar : gameCore.stars){
                    if (star != anStar){
                        star.collisionUpdate(anStar);
                    }
                }
            }
            gameCore.starsCollisionsUpdate();
        }
        repaint();
        requestFocus();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameCore.waitingForInput) {
            gameCore.createStar(e.getX(), e.getY());
        } else {
            for (Star star: gameCore.stars){
                if(new Ellipse2D.Double(
                        (star.x - star.radius + gameCore.shiftX) * gameCore.scale,
                        (star.y - star.radius + gameCore.shiftY) * gameCore.scale,
                        star.radius * 2 * gameCore.scale,
                        star.radius * 2 * gameCore.scale).contains(e.getX(), e.getY())){
                    gameCore.selectedStar = star;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            case KeyEvent.VK_N -> gameCore.createStar();
            case KeyEvent.VK_SPACE -> {
                if (gameCore.isGameStop){
                    gameCore.timerStart();
                } else {
                    gameCore.timerStop();
                }
            }
            case KeyEvent.VK_UP, KeyEvent.VK_KP_UP -> gameCore.shiftUp();
            case KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN -> gameCore.shiftDown();
            case KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT -> gameCore.shiftRight();
            case KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT -> gameCore.shiftLeft();
            case 109, KeyEvent.VK_MINUS -> gameCore.zoomIn();
            case 107, KeyEvent.VK_EQUALS -> gameCore.zoomOut();
            case KeyEvent.VK_M -> {
                gameCore.buttonsField.multipleModeCheckbox.setState(true);
                gameCore.multipleMode = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
