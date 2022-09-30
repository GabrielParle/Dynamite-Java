package training.dynamite;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MyBot implements Bot {

    public MyBot() {
        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match
        System.out.println("Started new match");
    }

    @Override
    public Move makeMove(Gamestate gamestate) {
        // Are you debugging?
        // Put a breakpoint in this method to see when we make a move
        //enum
        int randomMove = Randint(5);


       List<Boolean> winsLosses = gamestate.getRounds().stream().map((round) -> {
            return xWinsAgainstY(round.getP1(), round.getP2());
        }).collect(Collectors.toList());

       if(shouldChangeStrat(winsLosses)){
          int move = stratchanger(0);
          return internalMover(move);
       }

       return internalMover(randomMove);

    }

    public Move internalMover(int moveInt){
        switch (moveInt) {
            case 0:
                return Move.R;
            case 1:
                return Move.P;
            case 2:
                return Move.S;
            case 3:
                return Move.D;
            case 4:
                return Move.W;
            default:
                return Move.D;
        }
    }
    public int Randint(int upperLimit){
        Random rand = new Random();
        int upperBound = upperLimit;

       int move = rand.nextInt(upperBound);
       return move;
    }

    private static boolean xWinsAgainstY(Move x, Move y) {
        if (x != Move.R || y != Move.S && y != Move.W) {
            if (x != Move.P || y != Move.R && y != Move.W) {
                if (x == Move.S && (y == Move.P || y == Move.W)) {
                    return true;
                } else if (x != Move.D || y != Move.R && y != Move.P && y != Move.S) {
                    return x == Move.W && y == Move.D;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean shouldChangeStrat(List<Boolean> winsLosses){
        try {
            if (winsLosses.subList((winsLosses.size()-5), (winsLosses.size())).stream().allMatch(listItem -> listItem == false)) {
                return true;
            } else{
                return false;
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return false;
    }

    public int stratchanger(int stratChange){
        {
            switch (stratChange) {
                case 0:
                    return Randint(5);
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    public int defeatLastMove(Gamestate gamestate);
    {

    }




}
