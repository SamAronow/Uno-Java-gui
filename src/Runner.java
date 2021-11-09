import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Runner extends JFrame implements ActionListener{
    private Menu menu;
    private Board game;
    public Runner() {
        this.setSize(750,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
         menu = new Menu();
         menu.setBounds(0,0,750,750);
        menu.getEnter().addActionListener(e-> {
            if (menu.validNames()) {
                menu.setVisible(false);
                this.remove(menu);
                game = new Board(this,menu.getNames(), menu.getNumPlayers());
                game.setBounds(0,0,900,750);
                this.add(game);
                this.setSize(900,750);
            }
        });
        this.add(menu);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        for (int i=0; i<game.getCurHand().getHand().size(); i++){
           if (e.getSource()==game.getCurHand().getHand().get(i).getButton()){
               if (game.getCurHand().getHand().get(i).compareCard(game.getLast())) {
                   if (game.getCurHand().getHand().get(i).getCardColor() != CardColor.ALL) {
                       game.waitNext(game.getCurHand().getHand().remove(i));
                   }
                   else{
                       game.wildAction(game.getCurHand().getHand().remove(i));
                   }
               }
           }
       }
        if (e.getSource()==game.getRestart()){
            this.setVisible(false);
            this.dispose();
            Runner r = new Runner();
        }
    }

    public static void main(String[] args){
        Runner r = new Runner();
    }
}
