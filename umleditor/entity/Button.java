package umleditor.entity;

import java.util.ArrayList;

import umleditor.Statics;

public class Button {

    private final ArrayList<Button.Base> buttons = new ArrayList<>();

    public Button(){
        buttons.add(Statics.BUTTON.SELECT, new Select());
        buttons.add(Statics.BUTTON.ASSOCIATION_LINE, new AssociationLine());
        buttons.add(Statics.BUTTON.GENERALIZATION_LINE, new GenralizationLine());
        buttons.add(Statics.BUTTON.COMPOSITION_LINE, new CompositionLine());
        buttons.add(Statics.BUTTON.UML_CLASS, new UMLClass());
        buttons.add(Statics.BUTTON.USE_CASE, new UseCase());
    }

    public Button.Base getBtn(int BtnID){ return buttons.get(BtnID); }

    public ArrayList<Button.Base> getAll() { return buttons; }

    public class Base{
        private final String name;
        private final String iconPath;
        private final int id;
        private boolean isSelect;

        public Base(int id, String name, String iPath){
            this.id = id;
            this.name = name;
            this.iconPath = iPath;
            this.isSelect = false;
        }

        public String getName() { return name; }
        public String getIconPath() { return iconPath; }
        public int getId() { return id; }
        public boolean getSelect() { return isSelect; }

        public void select() { isSelect = true; }
        public void deselect() { isSelect = false; }
    }
    
    public class Select extends Base{
       
        public Select(){
            super(Statics.BUTTON.SELECT, "SELECT", "./img/select.png");
        }
    }
    
    public class AssociationLine extends Base{
       
        public AssociationLine(){
            super(Statics.BUTTON.ASSOCIATION_LINE, "ASSOCIATION_LINE", "./img/association_line.png");
        }
    }
    
    public class GenralizationLine extends Base{
       
        public GenralizationLine(){
            super(Statics.BUTTON.GENERALIZATION_LINE, "GENERALIZATION_LINE", "./img/generalization_line.png");
        }
    }
    
    public class CompositionLine extends Base{
       
        public CompositionLine(){
            super(Statics.BUTTON.COMPOSITION_LINE, "COMPOSITION_LINE", "./img/composition_line.png");
        }
    }
    
    public class UMLClass extends Base{
       
        public UMLClass(){
            super(Statics.BUTTON.UML_CLASS, "UML_CLASS", "./img/classes.png");
        }
    }
    
    public class UseCase extends Base{
       
        public UseCase (){
            super(Statics.BUTTON.USE_CASE, "USE_CASE", "./img/use_case.png");
        }
    }

}
