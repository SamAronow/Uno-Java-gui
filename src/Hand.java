import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
public class Hand extends JLayeredPane{
    private ArrayList<Card> hand;
    public Hand(Deck deck){
        hand= new ArrayList();
        for (int i=0; i<7; i++){
            hand.add(deck.deal());
        }
        updatePane();
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    public void updatePane(){
        this.removeAll();
        int space=25;
        if (hand.size()>25){
            space=22;
        }
        int start=0;
        for (int i=hand.size()-1;i>=0;i--){
            JLayeredPane temp = hand.get(i);
            temp.setBounds(start,0,100,150);
            this.add(temp,0);
            start+=space;
        }
        this.setSize(space*hand.size()+100-space,150);
    }

    public void updatePaneBack(){
        this.removeAll();
        int start=0;
        int space=15;
        if (hand.size()>25){
            space=13;
        }
        for (int i=0; i<hand.size();i++){
            JLabel temp = new JLabel(Card.getBack());
            temp.setBounds(start,0,100,150);
            this.add(temp,0);
            start+=space;
        }
        this.setSize(space*hand.size()+100-space,150);
    }

    public void sortHand(){
        for (int gen = 1; gen<hand.size(); gen++){
            Card temp = hand.get(gen);
            int storedIndex=0;
            for (int i=gen; i>0; i--){
                if (temp.getVal()<hand.get(i-1).getVal()){
                    storedIndex=i;
                    break;
                }
                hand.set(i,hand.get(i-1)); //same as else cuz break
            }
            hand.set(storedIndex,temp);
        }
    }

}
