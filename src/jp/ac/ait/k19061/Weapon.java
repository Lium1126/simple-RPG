package jp.ac.ait.k19061;

public class Weapon {
    private String name;
    private double attackPoint;

    public Weapon(String name, double attackPoint) {
        this.name =name;
        this.attackPoint = attackPoint;
    }

    public String getName() {
        return name;
    }

    public double getAttackPoint() {
        return attackPoint;
    }
}
