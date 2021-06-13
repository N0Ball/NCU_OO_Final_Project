package umleditor.entity;

import java.util.ArrayList;

import umleditor.Statics;

public class MenuBar {

    private static final ArrayList<Menu> menus = new ArrayList<>();

    public MenuBar(){

        // Init menubar
        menus.add(new File());
        menus.add(new Edit());

    }

    public ArrayList<Menu> getMenus() { return menus; }
    public Menu getMenu(int MenuId) { return menus.get(MenuId); }

    public MenuItem getMenuItem(int ItemID) { 
        for (Menu menu: menus){
            for (MenuItem item: menu.items){
                if (item.id == ItemID){
                    return item;
                }
            }
        }
        return null;
    }

    public class Menu{

        private String name;
        protected ArrayList<MenuItem> items = new ArrayList<>();

        public Menu(String name){
            this.name = name;
        }

        public String getName() { return name; }
        public ArrayList<MenuItem> getItems() { return items; }
    }

    private class File extends Menu{
        public File(){
            super("File");
            items.add(new Load());
            items.add(new Save());
            items.add(new Exit());
        }
    }

    private class Edit extends Menu{
        public Edit(){
            super("Edit");
            items.add(new Group());
            items.add(new Ungroup());
            items.add(new Rename());
        }
    }

    public class MenuItem{

        private String name;
        private int id;

        public MenuItem(int id, String name){
            this.id = id;
            this.name = name;
        }

        public String getName() { return name; }
        public int getId() { return id; }
    }

    private class Load extends MenuItem{

        public Load(){
            super(Statics.MENUITEMS.LOAD, "Load");
        }
    }

    private class Save extends MenuItem{

        public Save(){
            super(Statics.MENUITEMS.SAVE, "Save");
        }
    }

    private class Exit extends MenuItem{

        public Exit(){
            super(Statics.MENUITEMS.EXIT, "Exit");
        }
    }

    private class Group extends MenuItem{

        public Group(){
            super(Statics.MENUITEMS.GROUP, "Group");
        }
    }

    private class Ungroup extends MenuItem{

        public Ungroup(){
            super(Statics.MENUITEMS.UNGROUP, "Ungroup");
        }
    }

    private class Rename extends MenuItem{

        public Rename(){
            super(Statics.MENUITEMS.RENAME, "Rename");
        }
    }
    
}
