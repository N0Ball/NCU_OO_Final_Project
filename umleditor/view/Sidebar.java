package umleditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import umleditor.Presenter;

public class Sidebar extends JPanel {

    private final static int WIDTH = 100;
    private final static int HEIGHT = 240;
    private final static int BTN_SIZE = 50;

    private final Presenter.Sidebar presenter;
    
    public Sidebar(Presenter presenter){

        this.presenter = presenter.new Sidebar();

        // Init Panel
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Init Buttons

        for (int i = 0; i < this.presenter.getBtnNumbers(); i++){
            this.add(new Button(
                i,
                this.presenter.getBtnIPath(i)
            ));
        }

    }

    private class Button extends JButton {

        public final int id;
        public final Image icon;
        
        public Button(int BtnID, String iconPath){
            this.id = BtnID;
            this.setPreferredSize(new Dimension(BTN_SIZE, BTN_SIZE));
            this.icon = new ImageIcon(iconPath).getImage().getScaledInstance(BTN_SIZE, BTN_SIZE,Image.SCALE_DEFAULT);
            this.setBackground(null);
            this.setOpaque(false);
            this.addActionListener( e -> {
                onPressed(this);
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.clearRect(0, 0, BTN_SIZE, BTN_SIZE);

            g2d.setColor(presenter.getBtnColor(this.id));
            g2d.fillRect(0, 0, BTN_SIZE, BTN_SIZE);

            g2d.drawImage(this.icon, 0, 0, null);
            super.paintComponent(g);
        }

    }

    public void onPressed(Button btn) {
        this.presenter.onPressed(btn.id);
        this.repaint();
    }
    
}
