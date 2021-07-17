package jp.ac.ait.k19061;

import java.util.Random;

public class Warrior extends Hero implements IHeroJob{
    public Warrior(String name, int hp, int atk, int def, int agi) {
        super(name, hp, atk, def, agi);
    }

    @Override
    public String getJobName() {
        return "武闘家";
    }

    @Override
    public String getSpecialAttackName() {
        return "超集中";
    }

    @Override
    public AttackResult specialAttack(CharacterBase e) {
        // 武闘家の超集中は70%の確率でクリティカル、そうでなければ失敗になる

        AttackResult ret = new AttackResult();

        Random r = new Random();

        if (r.nextInt(100) < 30) {
            // 30%の確率で攻撃失敗
            ret.damage = 0;
            ret.isFailure = true;
            return ret; // 攻撃失敗はクリティカル判定やダメージを与える処理を通さない
        }

        // 攻撃成功の場合はつねにクリティカル
        ret.damage = this.calcAttackPoint(e) * 2;
        ret.isCritical = true;

        // ダメージを与える
        e.damage(ret.damage);

        // 与えたダメージ量を返す
        return ret;
    }

    @Override
    public String toString() {
        // デバッグ用に職業メッセージを追加しておきます。
        return super.toString() + ", " + this.getJobName() + "(" + this.getSpecialAttackName() + ")";
    }
}
