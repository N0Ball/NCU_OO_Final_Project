package umleditor.view;

import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import umleditor.Presenter;
import umleditor.utils.IObserver;

/**
 * Main Frame of the program
 */
public class MainFrame extends JFrame implements IObserver{

    private final static int WIDTH = 1000;
    private final static int HEIGHT = 600;
    private final static String TITLE = "UML Editor";
    private final Presenter presenter;
    
    public MainFrame(Presenter presenter){

        this.presenter = presenter;
        presenter.setObserver(this);

        // Inital Frame

        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        
        // Add Component to Frame
        
        this.add(new Sidebar(this.presenter), BorderLayout.WEST);
        this.add(new Canvas(this.presenter), BorderLayout.CENTER);
        this.setJMenuBar(new MenuBar(this.presenter));
        
        this.setVisible(true);
    }

    public void update(){
        repaint();
    }
}
