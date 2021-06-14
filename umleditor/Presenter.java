package umleditor;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import umleditor.interactor.*;
import umleditor.utils.Pair;
import umleditor.utils.IDraw;

public class Presenter {

    private static final SidebarInteractor SIDEBAR_INTERACTOR = new SidebarInteractor();
    private static final MenuBarInteractor MENUBAR_INTERACTOR = new MenuBarInteractor();
    private static final CanvasInteractor CANVAS_INTERACTOR = new CanvasInteractor();

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

            CANVAS_INTERACTOR.setMode(btnID);
            
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

    public class Canvas extends Base{

        public void onPressed(Point pt){
            CANVAS_INTERACTOR.onPressed(pt);
        }

        public void onDragged(Point pt){
            CANVAS_INTERACTOR.onDragged(pt);
        }

        public void onReleased(Point pt){
            CANVAS_INTERACTOR.onReleased(pt);
        }

        public ArrayList<IDraw> getObjects(){
            ArrayList<IDraw> objects = new ArrayList<>();
            CANVAS_INTERACTOR.getAllObjects().forEach( e -> {
                objects.add(e.getDrawMethod());
            });
            return objects;
        }

    }
    
}
