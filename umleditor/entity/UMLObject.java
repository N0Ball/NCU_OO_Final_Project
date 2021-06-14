package umleditor.entity;

import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import umleditor.Statics;
import umleditor.utils.IDraw;

public class UMLObject {

    private static final ArrayList<Base> objects = new ArrayList<>();
    
    public void createObject(int ObjectId){
        objects.add(getObject(ObjectId));
    }

    public ArrayList<Base> getAllObjects() { return objects; }

    public Base getObject(int ObjectId){

        switch (ObjectId) {
            case Statics.UMLOBJECT.CLASS:
                return new Class();
            case Statics.UMLOBJECT.USE_CASE:
                return new UseCase();
            // case Statics.UMLOBJECT.COMPOSITION_LINE:
            //     return new Class();
            // case Statics.UMLOBJECT.GENERALIZATION_LINE:
            //     return new Class();
            // case Statics.UMLOBJECT.COMPOSITION_LINE:
            //     return new Class();
            default:
                System.out.println("Warning: get null return at UMLObject.getObject");
                return null;
        }
    }

    abstract public class Base{

        protected Point location = new Point();
        protected int id;
        protected int type;
        protected String name;

        public int getType() { return type; }
        public String getName() { return name; }
        public Point getLocation(){ return location; }
        public IDraw getDrawMethod() {
            return (Graphics2D g) -> draw(g);
        }
        
        public void setLocation(int x,int y){
            location.x = x;
            location.y = y;
        }

        abstract public void draw(Graphics2D g);
    }

    abstract public class Shape extends Base{

        protected int height = 25;
        protected int width = 100;
        protected int type = Statics.UMLOBJECT_TYPE.SHAPE;
        protected final int TEXT_PADDING = 7;

        public void setName(String name) {
            this.name = name;
        }

    }

    private class Class extends Shape{

        private final int TITLE_LENGTH = 1;
        private final int FIELD_LENGTH = 1;
        private final int METHOD_LENGTH = 1;
        private final int TOTLE_LENGTH = TITLE_LENGTH + FIELD_LENGTH + METHOD_LENGTH;
        
        public Class(){
            this.id = Statics.UMLOBJECT.CLASS;
            this.name = "New Class";
            this.height = TOTLE_LENGTH * this.height;
        }

        @Override
        public void draw(Graphics2D g) {
            
            g.setColor(Color.BLACK);
            g.drawRect(this.location.x, this.location.y, this.width, this.height);
            g.drawLine(this.location.x, this.location.y + TITLE_LENGTH*this.height/TOTLE_LENGTH, this.location.x + this.width, this.location.y + TITLE_LENGTH*this.height/TOTLE_LENGTH);
            g.drawLine(this.location.x, this.location.y + (TITLE_LENGTH + FIELD_LENGTH)*this.height/TOTLE_LENGTH, this.location.x + this.width, this.location.y + (TITLE_LENGTH + FIELD_LENGTH)*this.height/TOTLE_LENGTH);

            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString(this.name, this.location.x + TEXT_PADDING, this.location.y + this.height / TOTLE_LENGTH / 2 + TEXT_PADDING);
        }
    }

    private class UseCase extends Shape{
        public UseCase(){
            this.id = Statics.UMLOBJECT.USE_CASE;
            this.name = "New Use Case";
            this.width *= 1.5;
            this.height *= 2;
        }

        @Override
        public void draw(Graphics2D g) {
            
            g.setColor(Color.BLACK);
            g.drawArc(this.location.x, this.location.y, this.width, this.height, 0, 360);

            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString(this.name, this.location.x + TEXT_PADDING, this.location.y + this.height / 2 + TEXT_PADDING);
        }
    }

    abstract public class Line extends Base{
        protected Shape startShape;
        protected Shape endShape;
        protected int type = Statics.UMLOBJECT_TYPE.LINE;
        protected String name = "Line";
    }
}
