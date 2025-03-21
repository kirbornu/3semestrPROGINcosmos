import java.awt.*;
import java.util.Random;

public class Star {
    double x;
    double y;
    double velocityX;
    double velocityY;
    double radius;
    double mass;
    Color color;
    String name;
    boolean isAbsorbed = false;
    boolean isSplit = false;
    boolean isFixed = false;
    double[] fragment;

    public Star(double x, double y, double velocityX, double velocityY, double mass, Color color, String name) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mass = mass;
        this.radius = Math.sqrt(mass / Math.PI);
        this.color = color;
        this.name = name;
    }

    public void gravityUpdate(Star anStar){
        double distanceX = x - anStar.x;
        double distanceY = y - anStar.y;
        if (distanceX == 0 && distanceY == 0){
            distanceX = (Math.random() + 0.01) * (Math.random() - 0.5) * 2;
            distanceY = (Math.random() + 0.01) * (Math.random() - 0.5) * 2;
        }
        double hypotenuse = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if (hypotenuse < (radius + anStar.radius)){
            double force = (mass * anStar.mass)/Math.pow(radius + anStar.radius, 2);
            velocityX += force * (distanceX/hypotenuse) / mass * 0.1;
            velocityY += force * (distanceY/hypotenuse) / mass * 0.1;
        } else {
            double force = (mass * anStar.mass)/Math.pow(hypotenuse, 2);
            velocityX -= force * (distanceX/hypotenuse) / mass;
            velocityY -= force * (distanceY/hypotenuse) / mass;
        }
    }

    public void move(){
        if (velocityX > 50){
            velocityX = 50;
        }
        if (velocityY > 50){
            velocityY = 50;
        }
        if (velocityX < -50){
            velocityX = -50;
        }
        if (velocityY < -50){
            velocityY = -50;
        }
        x += velocityX;
        y += velocityY;
    }

    public void collisionUpdate(Star anStar){
        double distanceX = x - anStar.x;
        double distanceY = y - anStar.y;
        double hypotenuse = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if (distanceX == 0 && distanceY == 0){
            distanceX = (Math.random() + 0.01) * (Math.random() - 0.5);
            distanceY = (Math.random() + 0.01) * (Math.random() - 0.5);
        }
        if (hypotenuse < radius + anStar.radius){
            if (Math.max(mass, anStar.mass) / Math.min(mass, anStar.mass) < 10 && mass + anStar.mass > 100 && radius < hypotenuse){
                double vector = Math.toDegrees(Math.atan2(velocityX, velocityY));
                double anStarVector = Math.toDegrees(Math.atan2(anStar.velocityX, anStar.velocityY));
                double[][] degrees;
                if (vector < anStarVector){
                    degrees = new double[][] {{vector+110, vector+250}, {anStarVector+110, anStarVector+250}};
                } else {
                    degrees = new double[][] {{anStarVector+110, anStarVector+250}, {vector+110, vector+250}};
                }

                double force = Math.sqrt(Math.pow(velocityX + anStar.velocityX, 2) + Math.pow(velocityY + anStar.velocityY, 2)) + 0.001;
//                for (double m = hypotenuse/2; m > 2*(radius + anStar.radius); m = 1.1 * m){
                    double fragmentVelocityX;
                    double fragmentVelocityY;
                    if (degrees[0][1] >= degrees[1][0]){
                        fragmentVelocityX = force * Math.cos(new Random().nextDouble(degrees[1][1], degrees[0][0]+360));
                        fragmentVelocityY = force * Math.sin(new Random().nextDouble(degrees[1][1], degrees[0][0]+360));
                    } else {
                        int i = new Random().nextInt(0, 1);
                        int j = 1 - i;
                        fragmentVelocityX = force * Math.cos(new Random().nextDouble(degrees[i][1], degrees[j][0]));
                        fragmentVelocityY = force * Math.sin(new Random().nextDouble(degrees[i][1], degrees[j][0]));
                    }
                    isSplit = true;
                    fragment = new double[] {x + (radius * -distanceX / hypotenuse) + radius * fragmentVelocityX/force * 2,
                            y + (radius * -distanceY / hypotenuse) + radius * fragmentVelocityY/force * 2, fragmentVelocityX, fragmentVelocityY,
                            mass * Math.abs((radius + anStar.radius - hypotenuse) / (radius + anStar.radius))};
//                }

            } else {
                if (mass >= anStar.mass){
                    velocityX += anStar.velocityX / (mass / anStar.mass);
                    velocityY += anStar.velocityY / (mass / anStar.mass);
                    mass += anStar.mass;
                    radius = Math.sqrt(mass / Math.PI);
                    anStar.isAbsorbed = true;
                }
//                else {
//                    anStar.velocityX += velocityX / (anStar.mass / mass);
//                    anStar.velocityY += velocityY / (anStar.mass / mass);
//                    anStar.mass += mass;
//                    anStar.radius = Math.sqrt(anStar.mass / Math.PI);
//                    isAbsorbed = true;
//                }
            }
        }
    }
}
