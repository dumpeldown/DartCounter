package de.dumpeldown.dartcounter.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DartSpieler {
    int hoechsterWurf;
    List<Integer> alleWuerfe;
    int pointsRemaining ;
    int pointsPlayed;

    public DartSpieler(){
        this.pointsRemaining = 501;
        this.pointsPlayed = 0;
    }

    public void addWuerfe(int eins, int zwei, int drei){
        if(alleWuerfe == null) alleWuerfe = new ArrayList<>();
        alleWuerfe.add(eins);
        alleWuerfe.add(zwei);
        alleWuerfe.add(drei);
    }

    public void deleteLastThreeWuerfe(){
        alleWuerfe.remove(alleWuerfe.size()-1);
        alleWuerfe.remove(alleWuerfe.size()-1);
        alleWuerfe.remove(alleWuerfe.size()-1);
    }

    public void updatePointsPlayed(int sumOfLastThree) {
        this.pointsPlayed += sumOfLastThree;
    }

    public void updatePointsRemaining(int sumOfLastThree) {
        this.pointsRemaining -= sumOfLastThree;
    }

    public void updateHoechsterWurf(int sumOfLastThree){
        if(sumOfLastThree > hoechsterWurf){
            hoechsterWurf = sumOfLastThree;
        }
    }

    public int getSumOfLastThree(){
        int sum = 0;
        for(int i :alleWuerfe.stream().skip(alleWuerfe.size()-3).collect(Collectors.toList())){
            sum += i;
        }
        return sum;
    }

    public int getHoechsterWurf() {
        return hoechsterWurf;
    }

    public List<Integer> getAlleWuerfe() {
        return alleWuerfe;
    }

    public int getPointsRemaining() {
        return pointsRemaining;
    }

    public int getPointsPlayed() {
        return pointsPlayed;
    }

    public void setPointsRemaining(int pointsRemaining) {
        this.pointsRemaining = pointsRemaining;
    }
}
