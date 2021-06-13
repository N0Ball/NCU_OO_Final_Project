package umleditor.view;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import umleditor.Presenter;

public class MenuBar extends JMenuBar {

    private final Presenter.Menu presenter;
    
    public MenuBar(Presenter presenter){
        this.presenter = presenter.new Menu();

        for (int i = 0; i < this.presenter.getMenuNumbers(); i++){
            this.add(new Menu(
                this.presenter.getMenuName(i),
                this.presenter.getMenuItems(i)
            ));
        }
    }

    private class Menu extends JMenu{

        public Menu(String name, ArrayList<Integer> itemsId){
            super(name);
            for (Integer itemId: itemsId){
                this.add(new MenuItem(
                    itemId,
                    presenter.getMenuItemName(itemId)
                ));
            }
        }
    }

    private class MenuItem extends JMenuItem{

        private final int id;

        public MenuItem(int id, String name){
            super(name);
            this.id = id;
            this.addActionListener(e -> {
                onPressed(this.id);
            });
        }
    }

    public void onPressed(int MenuItemId){
        this.presenter.onPressed(MenuItemId);
    }

}
