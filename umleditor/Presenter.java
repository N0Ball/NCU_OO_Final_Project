package umleditor;

import java.awt.Color;
import java.util.ArrayList;

import umleditor.interactor.*;

public class Presenter {

    private static final SidebarInteractor SIDEBAR_INTERACTOR = new SidebarInteractor();
    private static final MenuBarInteractor MENUBAR_INTERACTOR = new MenuBarInteractor();

    public Presenter(){}

    public class Base{
        public Base(){}
    }

    public class Sidebar extends Base{

        public int getBtnNumbers() { return SIDEBAR_INTERACTOR.getAllBtn().size(); }

        public String getBtnName(int btnID){
            return SIDEBAR_INTERACTOR.getBtn(btnID).getName();
        }

        public String getBtnIPath(int btnID){
            return SIDEBAR_INTERACTOR.getBtn(btnID).getIconPath();
        }

        public Color getBtnColor(int btnID){

            if (SIDEBAR_INTERACTOR.getBtn(btnID).getSelect()) return Color.DARK_GRAY;

            return Color.WHITE;
        }

        public void onPressed(int btnID){
            SIDEBAR_INTERACTOR.pressedBtn(btnID);
        }

    }

    public class Menu extends Base{

        public int getMenuNumbers() { 
            return MENUBAR_INTERACTOR.getAllMenu().size(); 
        }

        public ArrayList<Integer> getMenuItems(int MenuId){
            ArrayList<Integer> items = new ArrayList<>();
            MENUBAR_INTERACTOR.getMenu(MenuId).getItems().forEach(e -> {
                items.add(e.getId());
            });
            return items;
        }

        public String getMenuName(int MenuId){
            return MENUBAR_INTERACTOR.getMenu(MenuId).getName();
        }

        public String getMenuItemName(int MenuId){
            return MENUBAR_INTERACTOR.getMenuItem(MenuId).getName();
        }

        public void onPressed(int MenuItemId){
            System.out.println(
                MENUBAR_INTERACTOR.getMenuItem(MenuItemId).getName() + 
                " is clicked!"
            );
        }

    }
    
}
