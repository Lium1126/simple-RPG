package jp.ac.ait.k19061;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Random;

public class Tank extends Hero implements IHeroJob {
    public Tank(String name, int hp, int atk, int def, int agi) {
        super(name, hp, atk, def, agi);
    }

    @Override
    public String getJobName() {
        return "タンク";
    }

    @Override
    public String getSpecialAttackName() {
        return "重量級攻撃";
    }

    @Override
    public AttackResult specialAttack(CharacterBase e) {
        // タンクの重量級攻撃は10%の確率で通常の4倍のダメージを与える(それ以外は攻撃失敗となる)

        AttackResult ret = new AttackResult();

        Random r = new Random();

        if (r.nextInt(100) < 90) {
            // 90%の確率で攻撃失敗
            ret.damage = 0;
            ret.isFailure = true;
            return ret; // 攻撃失敗はクリティカル判定やダメージを与える処理を通さない
        }

        ret.damage = this.calcAttackPoint(e) * 4;

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
