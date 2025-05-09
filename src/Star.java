import java.awt.*;
import java.util.Random;

/**
 * Класс, представляющий звезду в симуляции.
 * Звезда имеет координаты, скорость, массу, радиус, цвет и имя.
 * Звёзды могут взаимодействовать друг с другом через гравитацию и столкновения.
 * @author kirbornu
 * @version 1.1
 */
public class Star {
    /** Координата X звезды в пространстве симуляции. */
    double x;
    /** Координата Y звезды в пространстве симуляции. */
    double y;
    /** Скорость звезды по оси X, измеряется в единицах расстояния за тик симуляции. */
    double velocityX;
    /** Скорость звезды по оси Y, измеряется в единицах расстояния за тик симуляции. */
    double velocityY;
    /** Радиус звезды, вычисляемый на основе её массы. */
    double radius;
    /** Масса звезды, влияющая на гравитационное взаимодействие и столкновения. */
    double mass;
    /** Цвет звезды, используемый для её отображения в симуляции. */
    Color color;
    /** Имя звезды, используемое для идентификации. */
    String name;
    /** Флаг, указывающий, была ли звезда поглощена другой звездой в результате столкновения. */
    boolean isAbsorbed = false;
    /** Флаг, указывающий, произошёл ли распад звезды на фрагменты при столкновении. */
    boolean isSplit = false;
    /** Флаг, указывающий, зафиксирована ли звезда (неподвижна) в симуляции. */
    boolean isFixed = false;
    /** Массив для хранения информации о фрагменте при распаде звезды: [x, y, velocityX, velocityY, масса]. */
    double[] fragment;

    /**
     * Конструктор для создания новой звезды с заданными параметрами.
     * @param x Координата X начального положения звезды.
     * @param y Координата Y начального положения звезды.
     * @param velocityX Начальная скорость по оси X.
     * @param velocityY Начальная скорость по оси Y.
     * @param mass Масса звезды, определяющая её размер и гравитационное влияние.
     * @param color Цвет звезды для визуализации.
     * @param name Имя звезды для идентификации.
     */
    public Star(double x, double y, double velocityX, double velocityY, double mass, Color color, String name) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mass = mass;
        this.radius = Math.sqrt(mass / Math.PI); // Радиус пропорционален корню из массы
        this.color = color;
        this.name = name;
    }

    /**
     * Обновляет скорость звезды под действием гравитации от другой звезды.
     * Вычисляет силу притяжения или отталкивания в зависимости от расстояния.
     * @param anStar Другая звезда, с которой происходит гравитационное взаимодействие.
     */
    public void gravityUpdate(Star anStar){
        double distanceX = x - anStar.x; // Разница координат по X
        double distanceY = y - anStar.y; // Разница координат по Y
        if (distanceX == 0 && distanceY == 0){
            // Предотвращение деления на ноль при совпадении позиций
            distanceX = (Math.random() + 0.01) * (Math.random() - 0.5) * 2;
            distanceY = (Math.random() + 0.01) * (Math.random() - 0.5) * 2;
        }
        double hypotenuse = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)); // Расстояние между звёздами
        if (hypotenuse < (radius + anStar.radius)){
            // Звёзды пересекаются: отталкивание
            double force = (mass * anStar.mass) / Math.pow(radius + anStar.radius, 2); // Сила отталкивания
            velocityX += force * (distanceX / hypotenuse) / mass * 0.1; // Обновление скорости по X
            velocityY += force * (distanceY / hypotenuse) / mass * 0.1; // Обновление скорости по Y
        } else {
            // Гравитационное притяжение на расстоянии
            double force = (mass * anStar.mass) / Math.pow(hypotenuse, 2); // Сила притяжения
            velocityX -= force * (distanceX / hypotenuse) / mass; // Уменьшение скорости по X
            velocityY -= force * (distanceY / hypotenuse) / mass; // Уменьшение скорости по Y
        }
    }

    /**
     * Обновляет положение звезды на основе текущей скорости.
     * Ограничивает максимальную скорость значением 50 по каждой оси.
     */
    public void move(){
        // Ограничение скорости для предотвращения чрезмерного ускорения
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
        x += velocityX; // Перемещение по X
        y += velocityY; // Перемещение по Y
    }

    /**
     * Обрабатывает столкновение с другой звездой.
     * Может привести к распаду звезды на фрагменты или поглощению одной звезды другой.
     * @param anStar Другая звезда, с которой происходит столкновение.
     */
    public void collisionUpdate(Star anStar){
        double distanceX = x - anStar.x; // Разница координат по X
        double distanceY = y - anStar.y; // Разница координат по Y
        double hypotenuse = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)); // Расстояние между звёздами
        if (distanceX == 0 && distanceY == 0){
            // Предотвращение деления на ноль при совпадении позиций
            distanceX = (Math.random() + 0.01) * (Math.random() - 0.5);
            distanceY = (Math.random() + 0.01) * (Math.random() - 0.5);
        }
        if (hypotenuse < radius + anStar.radius){
            // Проверка пересечения звёзд
            if (Math.max(mass, anStar.mass) / Math.min(mass, anStar.mass) < 10 && mass + anStar.mass > 100 && radius < hypotenuse){
                // Условие распада: массы схожи, общая масса > 100, радиус меньше расстояния
                double vector = Math.toDegrees(Math.atan2(velocityX, velocityY)); // Угол движения текущей звезды
                double anStarVector = Math.toDegrees(Math.atan2(anStar.velocityX, anStar.velocityY)); // Угол движения другой звезды
                double[][] degrees; // Диапазоны углов для фрагментов
                if (vector < anStarVector){
                    degrees = new double[][] {{vector+110, vector+250}, {anStarVector+110, anStarVector+250}};
                } else {
                    degrees = new double[][] {{anStarVector+110, anStarVector+250}, {vector+110, vector+250}};
                }

                double force = Math.sqrt(Math.pow(velocityX + anStar.velocityX, 2) + Math.pow(velocityY + anStar.velocityY, 2)) + 0.001; // Суммарная сила
                double fragmentVelocityX; // Скорость фрагмента по X
                double fragmentVelocityY; // Скорость фрагмента по Y
                if (degrees[0][1] >= degrees[1][0]){
                    // Случайный угол в пересекающемся диапазоне
                    fragmentVelocityX = force * Math.cos(Math.toRadians(new Random().nextDouble(degrees[1][1], degrees[0][0]+360)));
                    fragmentVelocityY = force * Math.sin(Math.toRadians(new Random().nextDouble(degrees[1][1], degrees[0][0]+360)));
                } else {
                    // Выбор случайного диапазона углов
                    int i = new Random().nextInt(0, 1);
                    int j = 1 - i;
                    fragmentVelocityX = force * Math.cos(Math.toRadians(new Random().nextDouble(degrees[i][1], degrees[j][0])));
                    fragmentVelocityY = force * Math.sin(Math.toRadians(new Random().nextDouble(degrees[i][1], degrees[j][0])));
                }
                isSplit = true; // Установка флага распада
                fragment = new double[] {x + (radius * -distanceX / hypotenuse) + radius * fragmentVelocityX/force * 2,
                        y + (radius * -distanceY / hypotenuse) + radius * fragmentVelocityY/force * 2, fragmentVelocityX, fragmentVelocityY,
                        mass * Math.abs((radius + anStar.radius - hypotenuse) / (radius + anStar.radius))}; // Создание фрагмента
            } else {
                // Поглощение одной звездой другой
                if (mass >= anStar.mass){
                    velocityX += anStar.velocityX / (mass / anStar.mass); // Обновление скорости по X
                    velocityY += anStar.velocityY / (mass / anStar.mass); // Обновление скорости по Y
                    mass += anStar.mass; // Увеличение массы
                    radius = Math.sqrt(mass / Math.PI); // Пересчёт радиуса
                    anStar.isAbsorbed = true; // Установка флага поглощения для другой звезды
                }
            }
        }
    }
}