package jp.ac.ait.k19061;

import java.util.Random;

public class Enemy extends CharacterBase {
    // コンストラクタ(全ての初期値を引数で渡す)
    public Enemy(String name, int hp, int atk, int def, int agi) {
        super(name, hp, atk, def, agi);
    }

    // 引数の相手に攻撃を行う
    @Override
    public AttackResult attack(CharacterBase e) {
        AttackResult ret = new AttackResult();

        // 引数の敵と自分自身で与えられるダメージを計算して取得
        int damage = calcAttackPoint(e);

        Random r = new Random();

        // 攻撃成功時 20% の確率でクリティカル
        // TODO: ここを20％の確率でクリティカルヒットするように変更
        if (r.nextInt(101) <= 20) {
            ret.isCritical = true;
            damage *= 2;
        }

        // "敵(勇者)"にダメージを与える
        e.damage(damage);

        // 計算したダメージ量を返す
        ret.damage = damage;
        return ret;
    }
}
