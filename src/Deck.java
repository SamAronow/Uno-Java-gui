import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.util.Collections;
public class Deck {
    private ArrayList<Card> deck = new ArrayList();
    public Deck(ActionListener list){
        makeDeck(list);
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    public Card deal(){
        return deck.remove(0);
    }


    private void makeDeck(ActionListener list){
        deck.clear();
        for(CardColor c : CardColor.values()) {
            for(Name n : Name.values()) {
                if (n.ordinal()<13 && c.ordinal()<4) {
                    deck.add(new Card(n, c, list));
                    if (n.ordinal() != 0) {
                        deck.add(new Card(n, c, list));
                    }
                }
            }
        }
        for (int i=0; i<4; i++){
            deck.add(new Card(Name.WILD, CardColor.ALL, list));
            deck.add(new Card(Name.DRAW_FOUR, CardColor.ALL, list));
        }
        Collections.shuffle(deck);
    }

    private void findAndRemove(Card target){
        for (int i=0; i<deck.size();i++){
            if (deck.get(i).getCardColor()==target.getCardColor() && deck.get(i).getType()==target.getType()){
                deck.remove(i);
                i--;
                break;
            }
        }
    }
    public void emptyDeck(ActionListener list, Hand[] hands, Card last){
        makeDeck(list);
        for (int i=0; i<hands.length; i++) {
            for (int c =0; c<hands[i].getHand().size(); c++){
                findAndRemove(hands[i].getHand().get(c));
            }
        }
        findAndRemove(last);
    }




}
