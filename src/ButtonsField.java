import javax.swing.*;
import java.awt.*;

/**
 * Класс отвечает за отрисовку поля интерфейса, через который происходит взаимодействие пользоователя с симуляцией
 * @author kirbornu
 * @version 1.1
 */
public class ButtonsField extends JPanel {
    /**
     * Ширина поля интерфейса
     */
    static public final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    /**
     * Высота поля интерфейса
     */
    static public final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    GameCore gameCore;
    Checkbox multipleModeCheckbox;
    TextArea messageArea;
    Label zoomLabel;
    Button stopButton;
    Button startButton;

    public ButtonsField() {
        setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT));
        setBackground(new Color(0, 15, 60));
        addButtons();
    }

    /**
     * Этот метод - основная часть класса интерфейса.
     * Здесь происходит добавление всех кнопок на поле интерфейса
     */
    public void addButtons() {
        Button exitButton = new Button("Выход/Esc");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        Button createPlanetButton = new Button("Новая планета/N");
        createPlanetButton.addActionListener(e -> gameCore.createStar());
        add(createPlanetButton);

        multipleModeCheckbox = new Checkbox("Множественное размещение/M");
        multipleModeCheckbox.addItemListener(i -> gameCore.multipleMode = multipleModeCheckbox.getState());
        multipleModeCheckbox.setForeground(Color.white);
        multipleModeCheckbox.setFocusable(false);
        add(multipleModeCheckbox);

        messageArea = new TextArea();
        messageArea.setPreferredSize(new Dimension(300,250));
        messageArea.setFocusable(false);
        add(messageArea);

        Button zoomInButton = new Button("Увеличить масштаб/-");
        zoomInButton.addActionListener(e -> gameCore.zoomIn());
        add(zoomInButton);
        
        Button zoomOutButton = new Button("Уменьшить масштаб/+");
        zoomOutButton.addActionListener(e -> gameCore.zoomOut());
        add(zoomOutButton);

        zoomLabel = new Label("Масштаб = 1.0");
        zoomLabel.setForeground(Color.white);
        add(zoomLabel);

        Button shiftXIncreaseButton = new Button("Влево");
        shiftXIncreaseButton.addActionListener(e -> gameCore.shiftLeft());
        add(shiftXIncreaseButton);

        Button shiftXDecreaseButton = new Button("Вправо");
        shiftXDecreaseButton.addActionListener(e -> gameCore.shiftRight());
        add(shiftXDecreaseButton);

        Button shiftYIncreaseButton = new Button("Вверх");
        shiftYIncreaseButton.addActionListener(e -> gameCore.shiftUp());
        add(shiftYIncreaseButton);

        Button shiftYDecreaseButton = new Button("Вниз");
        shiftYDecreaseButton.addActionListener(e -> gameCore.shiftDown());
        add(shiftYDecreaseButton);

        Button resetZoomAndShiftButton = new Button("Сбросить");
        resetZoomAndShiftButton.addActionListener(e -> gameCore.resetZoomAndShift());
        add(resetZoomAndShiftButton);
        
        Label emptyLabel = new Label("\n\t      ");
        add(emptyLabel);
        
        stopButton = new Button("Пауза");
        stopButton.addActionListener(e -> gameCore.timerStop());
        add(stopButton);
        
        startButton = new Button("Пуск");
        startButton.addActionListener(e -> gameCore.timerStart());
        add(startButton);

        CheckboxGroup speedControl = new CheckboxGroup();

        Checkbox speed001 = new Checkbox("x0.01");
        speed001.addItemListener(i -> gameCore.timer.setDelay(1000));
        speed001.setCheckboxGroup(speedControl);
        speed001.setForeground(Color.white);
        add(speed001);

        Checkbox speed01 = new Checkbox("x0.1");
        speed01.addItemListener(i -> gameCore.timer.setDelay(100));
        speed01.setCheckboxGroup(speedControl);
        add(speed01);
        speed01.setForeground(Color.white);

        Checkbox speed1 = new Checkbox("x1");
        speed1.addItemListener(i -> gameCore.timer.setDelay(10));
        speed1.setCheckboxGroup(speedControl);
        add(speed1);
        speed1.setForeground(Color.white);
        speed1.setState(true);

        Checkbox speed2 = new Checkbox("x2");
        speed2.addItemListener(i -> gameCore.timer.setDelay(5));
        speed2.setCheckboxGroup(speedControl);
        add(speed2);
        speed2.setForeground(Color.white);

        Checkbox speed10 = new Checkbox("x10");
        speed10.addItemListener(i -> gameCore.timer.setDelay(1));
        speed10.setCheckboxGroup(speedControl);
        add(speed10);
        speed10.setForeground(Color.white);

        Button clearButton = new Button("Очистить");
        clearButton.addActionListener(e -> gameCore.stars.clear());
        add(clearButton);



    }

    public void setGameCore(GameCore gameCore) {
        this.gameCore = gameCore;
    }

    public void setMessageAreaText(String text) {
        messageArea.setText(text);
    }

    public void setZoomLabelText(String text){zoomLabel.setText(text);}
}


