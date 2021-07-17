package jp.ac.ait.k19061;

import java.util.Random;

public class Assassin extends Hero implements IHeroJob{
    public Assassin(String name, int hp, int atk, int def, int agi) {
        super(name, hp, atk, def, agi);
    }

    @Override
    public String getJobName() {
        return "暗殺者";
    }

    @Override
    public String getSpecialAttackName() {
        return "一撃必殺";
    }

    @Override
    public AttackResult specialAttack(CharacterBase e) {
        // 暗殺者の一撃必殺は、5%の確率で相手を確実に倒す

        AttackResult ret = new AttackResult();

        Random random = new Random();

        if (random.nextInt(100) < 95) {
            ret.damage = 0;
            ret.isFailure = true;
            return ret; // 攻撃失敗はクリティカル判定やダメージを与える処理を通さない
        }

        ret.damage = e.hp;

        e.damage(ret.damage);

        return ret;
    }
}
