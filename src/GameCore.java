import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GameCore{
    SpaceField spaceField;
    ButtonsField buttonsField;
    Timer timer;
    public boolean isGameStop = false;
    public HashSet<Star> stars = new HashSet<Star>();
    public boolean waitingForInput = false;
    public boolean multipleMode = false;
    public double scale = 1.0;
    public double shiftX = 0.0;
    public double shiftY = 0.0;
    public int timerDelay = 10;
    public Star selectedStar;

    public GameCore (SpaceField sf, ButtonsField bf){
        spaceField = sf;
        buttonsField = bf;
        timer = new Timer(timerDelay, sf);
        timer.start();
        buttonsField.setGameCore(this);
        spaceField.setGameCore(this);
        timerStart();
    }

    public void createStar(){
        if (! waitingForInput){
            selectedStar = null;
            buttonsField.setMessageAreaText("Нажмите в любом месте пространства\n Там будет создана планета");
            waitingForInput = true;
        } else {
            waitingForInput = false;
            buttonsField.setMessageAreaText("");
        }
    }
    public void createStar(int x, int y){
        stars.add(new Star((double) x / scale - shiftX, (double) y / scale - shiftY, 0, 0, 200,
                new Color(new Random().nextInt(225) + 30, new Random().nextInt(225) + 30, new Random().nextInt(225) + 30),
                String.format("Планета%s", stars.size())));
        if (! multipleMode){
            waitingForInput = false;
            buttonsField.setMessageAreaText("");
        }
    }

    public void starsCollisionsUpdate(){
        stars.removeIf(star -> star.isAbsorbed);
        ArrayList<Star> fragments = new ArrayList<>();
        for (Star star : stars){
            if (star.isSplit){
                fragments.add(new Star(star.fragment[0], star.fragment[1], star.fragment[2], star.fragment[3], star.fragment[4], star.color,
                        String.format("Осколок%s", stars.size())));
                star.mass -= star.fragment[4];
                star.radius = Math.sqrt(star.mass / Math.PI);
                star.velocityX -= star.fragment[2];
                star.velocityY -= star.fragment[3];
                star.fragment = new double[4];
                star.isSplit = false;
            }
        }
        stars.addAll(fragments);
    }


    public void zoomIn(){
        scale -= 0.1;
        if(scale <= 0.1){
            scale = 0.1;
        }
        buttonsField.setZoomLabelText("Масштаб = " + String.valueOf(Math.round(scale * 10) / 10.0));
    }
    public void zoomOut(){
        scale += 0.1;
        buttonsField.setZoomLabelText("Масштаб = " + String.valueOf(Math.round(scale * 10) / 10.0));
    }

    public void shiftLeft(){
        shiftX += 80 / scale;
    }
    public void shiftRight(){
        shiftX -= 80 / scale;
    }
    public void shiftUp(){
        shiftY += 80 / scale;
    }
    public void shiftDown(){
        shiftY -= 80 / scale;
    }

    public void resetZoomAndShift(){
        scale = 1.0;
        buttonsField.setZoomLabelText("Масштаб = 1.0");
        shiftX = 0.0;
        shiftY = 0.0;
    }

    public void timerStart(){
        isGameStop = false;
        buttonsField.startButton.setEnabled(false);
        buttonsField.stopButton.setEnabled(true);
    }
    public void timerStop(){
        isGameStop = true;
        buttonsField.startButton.setEnabled(true);
        buttonsField.stopButton.setEnabled(false);
    }
}
