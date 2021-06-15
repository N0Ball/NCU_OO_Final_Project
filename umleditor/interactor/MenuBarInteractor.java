package umleditor.interactor;

import java.util.ArrayList;

import umleditor.entity.MenuBar;

public class MenuBarInteractor {

    private static final MenuBar MENU_BAR = new MenuBar();
    
    public ArrayList<MenuBar.Menu> getAllMenu(){
        return MENU_BAR.getMenus();
    }

    public MenuBar.Menu getMenu(int MenuId){
        return MENU_BAR.getMenu(MenuId);
    }

    public MenuBar.MenuItem getMenuItem(int MenuItemId){
        return MENU_BAR.getMenuItem(MenuItemId);
    }

}
