import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

public class Card extends JLayeredPane{
    private Name name;
    private CardColor color;
    private CardColor wildColor;
    private JLabel pic;
    private JButton button;
    private ImageIcon front;
    private int val;

    public Card(Name name, CardColor color, ActionListener list){
        this.name=name;
        this.color=color;
        if (name!=Name.DRAW_FOUR && name!=Name.WILD) {
            val = name.ordinal() + (color.ordinal() * 13);
        }
        else if (name==Name.DRAW_FOUR){
            val = 52;
        }
        else{
            val=53;
        }
        wildColor= CardColor.ALL;
        this.setSize(100,150);
        front = setFront();
        pic = new JLabel (front);
        pic.setBounds(0,0,100,150);
        button = new JButton();
        button.setBounds(0,0,100,150);
        button.addActionListener(list);// adds actionListener to the main class
        this.add(pic);
        this.add(button);
    }

    private ImageIcon setFront(){
        if (color==CardColor.BLUE){
                switch (name) {
                    case ZERO:
                        return new ImageIcon("blue0.png");
                    case ONE:
                        return new ImageIcon("blue1.png");
                    case TWO:
                        return new ImageIcon("blue2.png");
                    case THREE:
                        return new ImageIcon("blue3.png");
                    case FOUR:
                        return new ImageIcon("blue4.png");
                    case FIVE:
                        return new ImageIcon("blue5.png");
                    case SIX:
                        return new ImageIcon("blue6.png");
                    case SEVEN:
                        return new ImageIcon("blue7.png");
                    case EIGHT:
                        return new ImageIcon("blue8.png");
                    case NINE:
                        return new ImageIcon("blue9.png");
                    case SKIP:
                        return new ImageIcon("blueSkip.png");
                    case REVERSE:
                        return new ImageIcon("blueRev.png");
                    case DRAW_TWO:
                        return new ImageIcon("blueDraw.png");
                }
        }
        if (color==CardColor.YELLOW){
            switch (name) {
                case ZERO:
                    return new ImageIcon("yel0.png");
                case ONE:
                    return new ImageIcon("yel1.png");
                case TWO:
                    return new ImageIcon("yel2.png");
                case THREE:
                    return new ImageIcon("yel3.png");
                case FOUR:
                    return new ImageIcon("yel4.png");
                case FIVE:
                    return new ImageIcon("yel5.png");
                case SIX:
                    return new ImageIcon("yel6.png");
                case SEVEN:
                    return new ImageIcon("yel7.png");
                case EIGHT:
                    return new ImageIcon("yel8.png");
                case NINE:
                    return new ImageIcon("yel9.png");
                case SKIP:
                    return new ImageIcon("yelSkip.png");
                case REVERSE:
                    return new ImageIcon("yelRev.png");
                case DRAW_TWO:
                    return new ImageIcon("yelDraw.png");
            }
        }
        if (color==CardColor.GREEN){
            switch (name) {
                case ZERO:
                    return new ImageIcon("green0.png");
                case ONE:
                    return new ImageIcon("green1.png");
                case TWO:
                    return new ImageIcon("green2.png");
                case THREE:
                    return new ImageIcon("green3.png");
                case FOUR:
                    return new ImageIcon("green4.png");
                case FIVE:
                    return new ImageIcon("green5.png");
                case SIX:
                    return new ImageIcon("green6.png");
                case SEVEN:
                    return new ImageIcon("green7.png");
                case EIGHT:
                    return new ImageIcon("green8.png");
                case NINE:
                    return new ImageIcon("green9.png");
                case SKIP:
                    return new ImageIcon("greenSkip.png");
                case REVERSE:
                    return new ImageIcon("greenRev.png");
                case DRAW_TWO:
                    return new ImageIcon("greenDraw.png");
            }
        }
        if (color==CardColor.RED){
            switch (name) {
                case ZERO:
                    return new ImageIcon("red0.png");
                case ONE:
                    return new ImageIcon("red1.png");
                case TWO:
                    return new ImageIcon("red2.png");
                case THREE:
                    return new ImageIcon("red3.png");
                case FOUR:
                    return new ImageIcon("red4.png");
                case FIVE:
                    return new ImageIcon("red5.png");
                case SIX:
                    return new ImageIcon("red6.png");
                case SEVEN:
                    return new ImageIcon("red7.png");
                case EIGHT:
                    return new ImageIcon("red8.png");
                case NINE:
                    return new ImageIcon("red9.png");
                case SKIP:
                    return new ImageIcon("redSkip.png");
                case REVERSE:
                    return new ImageIcon("redRev.png");
                case DRAW_TWO:
                    return new ImageIcon("redDraw.png");
            }
        }
        if (color==CardColor.ALL) {
            switch(name){
            case DRAW_FOUR:
                return new ImageIcon("wild4.png");
            case WILD:
                return new ImageIcon("wild.png");
        }
        }
        return new ImageIcon();
    }

    public CardColor getCardColor(){
        return color;
    }

    public Name getType() {
        return name;
    }

    public ImageIcon getFront(){
        return front;
    }

    public JButton getButton(){
        return button;
    }

    public int getVal(){
        return val;
    }

    public static ImageIcon getBack() {
        return new ImageIcon("back.png");
    }

    public void setWildColor(CardColor color){
        wildColor=color;
    }

    public boolean compareCard(Card other){
        if (color==CardColor.ALL){
            return true;
        }
        if (other.color==CardColor.ALL){
            if (color==other.wildColor){
                return true;
            }
        }
        else {
            if (color==other.color || name== other.name){
                return true;
            }
        }
        return false;
    }
}

