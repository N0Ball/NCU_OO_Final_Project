package umleditor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import umleditor.Presenter;

public class Canvas extends JPanel {

    private final Presenter.Canvas presenter;
    
    public Canvas(Presenter presenter){


        this.presenter = presenter.new Canvas();

        // Inital Panel 
        this.setOpaque(false);
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseListener());

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        presenter.getObjects().forEach( e -> {
            e.draw(g2d);
        });

        super.paintComponent(g);

    }

    private class MouseListener extends MouseAdapter{

        public void mousePressed(MouseEvent e){
            presenter.onPressed(e.getPoint());
            repaint();
        }

        public void mouseDragged(MouseEvent e){
            presenter.onDragged(e.getPoint());
        }

        public void mouseReleased(MouseEvent e){
            presenter.onReleased(e.getPoint());
        }
    }

}
