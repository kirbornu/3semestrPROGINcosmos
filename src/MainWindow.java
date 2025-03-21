import javax.swing.*;
import java.util.Random;

public class MainWindow extends JFrame {

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

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}

