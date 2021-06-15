package umleditor.interactor;

import java.awt.Point;
import java.util.ArrayList;

import umleditor.Statics;
import umleditor.entity.UMLObject;
import umleditor.interactor.modes.*;

public class CanvasInteractor {

    private Mode currentMode;
    private UMLObject UMLOBJECT = new UMLObject();
    
    public CanvasInteractor(){}

    public UMLObject.Base getObject(int ObjectId){
        return UMLOBJECT.getObject(ObjectId);
    }

    public UMLObject.Base getObject(Object target){
        return UMLOBJECT.getObject(target);
    }

    public ArrayList<UMLObject.Base> getAllObjects(){
        return UMLOBJECT.getAllObjects();
    }

    public UMLObject.Base getCollideObject(Point pt){

        ArrayList<UMLObject.Base> objects = UMLOBJECT.getAllObjects();
        
        for (int i = objects.size() - 1 ; i >= 0; i--){
            if(isCollide(objects.get(i), pt)){
                return objects.get(i);
            }
        }
        return null;
    }

    public int getCollidePort(UMLObject.Base target, Point pt){

        Point normPt = new Point(
            pt.x - target.getLocation().x - target.getSize().x/2, 
            pt.y - target.getLocation().y - target.getSize().y/2
        );

        if( normPt.x + normPt.y > 0){
            if ( normPt.x - normPt.y > 0){
                return Statics.PORT.RIGHT;
            }
            return Statics.PORT.BOTTOM;
        }else{
            if ( normPt.x - normPt.y > 0){
                return Statics.PORT.TOP;
            }
            return Statics.PORT.LEFT;
        }

    }

    public ArrayList<UMLObject.Base> getCollideArea(Point topCorner, Point bottomCorner){
        ArrayList<UMLObject.Base> targets = new ArrayList<>();

        for (UMLObject.Base object: getAllObjects()){
            if (object.getLocation().x > topCorner.x && object.getLocation().x + object.getSize().x < bottomCorner.x){
                if (object.getLocation().y > topCorner.y && object.getLocation().y + object.getSize().y < bottomCorner.y){
                    targets.add(object);
                }
            }
        }

        return targets;
    }

    public void setMode(int btnId){

        switch (btnId) {

            case Statics.BUTTON.SELECT:
                System.out.println("Log:\t Set Mode to <SELECT>, Target to <SELECT_SQUARE>");
                currentMode = new SelectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.SELECT_SQUARE);
                break;

            case Statics.BUTTON.UML_CLASS:
                System.out.println("Log:\t Set Mode to <OBJECT>, Target to <CLASS>");
                currentMode = new ObjectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.CLASS);
                break;

            case Statics.BUTTON.USE_CASE:
                System.out.println("Log:\t Set Mode to <OBJECT>, Target to <USE_CASE>");
                currentMode = new ObjectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.USE_CASE);
                break;

            case Statics.BUTTON.ASSOCIATION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <ASSOCIATION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.ASSOCIATION_LINE);
                break;
                
            case Statics.BUTTON.GENERALIZATION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <GENERALIZATION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.GENERALIZATION_LINE);
                break;
                
            case Statics.BUTTON.COMPOSITION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <COMPOSITION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.COMPOSITION_LINE);
                break;

            default:
                System.out.println("Warning: Selected a unsupported Mode at Canvasinteractor.setMode !");
                break;
        }

    }

    public void select(Object target){
        for (UMLObject.Base object: getAllObjects()){
            if (object == target){
                object.select();
            }
        }
    }

    public void deselectAll(){
        getAllObjects().forEach(e -> {e.deselect();});
    }

    public UMLObject.Base createObject(){
        UMLObject.Base object = UMLOBJECT.createObject(currentMode.getTarget());
        return object;
    }

    public void deleteObject(Object target){
        try {
            UMLOBJECT.deleteObject(target);
        } catch (Exception e) {
            System.out.println("Error:\t at CanvasInteractor.deleteObject " + e);
        }
    }

    public void groupSelectObject(){
        ArrayList<UMLObject.Base> selected = getSelectedObject();

        if (selected.size() <= 1){
            System.out.println("Warning: Group Failed by selecting less then one Objects");
            return;
        }

        currentMode.setTarget(Statics.UMLOBJECT.COMPOSITE);
        deselectAll();
        System.out.println("Log:\t Set Mode to <SELECT>, Target to <COMPOSITE>");

        UMLObject.Base target = createObject();
        target.setCompositions(selected);
        setCorner(target, selected);

        currentMode.setTarget(Statics.UMLOBJECT.SELECT_SQUARE);
        System.out.println("Log:\t Set Mode to <SELECT>, Target to <SELECT_SQUARE>");
    }

    public void unGroupSelectObject(){

        ArrayList<UMLObject.Base> selected = getSelectedObject();

        if (selected.size() != 1){
            System.out.println("Warning: unGroup Failed by selecting more then one Objects");
            return ;
        }

        UMLObject.Base target = selected.get(0);
        
        if (target.getId() != Statics.UMLOBJECT.COMPOSITE){
            System.out.println("Warning: unGroup Failed by selecting a none composite object");
            return ;
        }

        target.getCompositions().forEach( e -> {
            e.setNormSelectBehavior();
        });

        deleteObject(target);
    }

    public void onPressed(Point pt){
        currentMode.onPressed(pt);
    }

    public void onDragged(Point pt){
        currentMode.onDragged(pt);
    }

    public void onReleased(Point pt){
        currentMode.onReleased(pt);
    }
    
    private boolean isCollide(UMLObject.Base target, Point pt){

        if (target.getType() == Statics.UMLOBJECT_TYPE.LINE){
            return false;
        }

        int X = target.getLocation().x;
        int Y = target.getLocation().y;
        int width = target.getSize().x;
        int height = target.getSize().y;

        if (pt.x > X && pt.x < X + width){
            if (pt.y > Y && pt.y < Y + height){
                return true;
            }
        }

        return false;
        
    }

    private ArrayList<UMLObject.Base> getSelectedObject(){

        ArrayList<UMLObject.Base> selected = new ArrayList<>();

        for (UMLObject.Base object: getAllObjects()){
            if (object.getSelected()){
                selected.add(object);
            }
        }

        return selected;
    }

    private void setCorner(UMLObject.Base comp, ArrayList<UMLObject.Base> targets){
        
        Point max = new Point(
            targets.get(0).getLocation().x,
            targets.get(0).getLocation().y
        );
        Point min = new Point(
            max.x,
            max.y
        );

        for (UMLObject.Base target: targets){

            target.setGroupSelectBehavior();

            int targetX = target.getLocation().x;
            int targetY = target.getLocation().y;
            Point size = target.getSize();

            if (min.x > targetX){
                min.x = targetX;
            }
            if (min.y > targetY){
                min.y = targetY;
            }
            if (max.x < targetX + size.x){
                max.x = targetX + size.x;
            }
            if (max.y < targetY + size.y){
                max.y = targetY + size.y;
            }
        }

        comp.setLocation(min.x, min.y);
        comp.setSize(max.x - min.x, max.y - min.y);
    }

}
