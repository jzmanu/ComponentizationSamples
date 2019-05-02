package com.manu.componentizationsamples.bean;

/**
 * Powered by jzman.
 * Created on 2018/9/26 0026.
 */
public class ScoreBean{
    private int score;
    private int rank;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ScoreBean{" +
                "score=" + score +
                ", rank=" + rank +
                '}';
    }

}
