package View;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Card layout for switching view
    private CardLayout cardLayout;

    public MainFrame() {
        super("Java Swing MVC");
        cardLayout = new CardLayout();
        Form form = new Form();
        // sets our layout as a card layout
        setLayout(cardLayout);

        // initialize user controller
        new Controller(form);

        // adds view to card layout with unique constraints
        add(form, "form");


        // frame width & height
        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
