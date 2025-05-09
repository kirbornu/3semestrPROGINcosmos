import javax.swing.*;
import java.util.Random;

/**
 * Этот класс является основным, предоставляет точку входа и создаёт экземпляры прочих ключевых классов.
 * Именно MainWindow() отвечает за отрисовку полей окна и за их взаимодействие с ядром игры.
 * @since 2023-08-02
 * Дата создания этого класса считается отправной точкой всего проекта cosmos
 * @author kirbornu
 * @version 1.1
 */
public class MainWindow extends JFrame {
    /**
     * В этом методе создается и настраивается главное окно, а также создаются экземпляры ключевых классов и настраивается их взаимодействие
     */
    public MainWindow() {
        setTitle("Симуляция небесной механики - cosmos");
        setIconImage(new ImageIcon("cosmos_icon.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        SpaceField sf = new SpaceField();
        add(sf);
        ButtonsField bf = new ButtonsField();
        add(bf);
        pack();
        setVisible(true);

        GameCore gameCore = new GameCore(sf, bf);
        sf.setButtonsField(bf);
    }

    /**
     * Точка входа в программу
     * @param args
     */
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}

