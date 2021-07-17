package jp.ac.ait.k19061;

import java.util.Random;

public class Hero extends CharacterBase {

    private Weapon weapon;

    // コンストラクタ(全ての初期値を引数で渡す)
    public Hero(String name, int hp, int atk, int def, int agi) {
        super(name, hp, atk, def, agi);

        weapon = new Weapon("木の棒", 10);
    }

    // 引数の相手に攻撃を行う
    @Override
    public AttackResult attack(CharacterBase e) {
        AttackResult ret = new AttackResult();

        // 引数の敵と自分自身で与えられるダメージを計算して取得
        int damage = calcAttackPoint(e);

        // 素早さの比較値を取得
        int agiDiff = this.getAgi() - e.getAgi();
        int successRate = 100;  // 攻撃成功率 (初期値を100％としておく)

        if (agiDiff < 0) {
            // 遅い場合
            if (Math.abs(agiDiff) >= 5) {
                successRate = 60;   // 差が5以上: 60%
            } else {
                successRate = 60;   // 差が5未満: 70%
            }
        } else if (agiDiff == 0) {
            // 等速
            successRate = 80;   // 同じ場合: 80%
        } else {
            // 速い
            if (Math.abs(agiDiff) < 3) {
                successRate = 60;   // 差が3未満: 90%
            }
        }

        Random r = new Random();

        // 上記成功判定で攻撃が成功する(それ以外は失敗)
        if (r.nextInt(101) > successRate) { // 失敗判定
            // return 0;   // ダメージ処理をしないで0を返す
            ret.isFailure = true; // 攻撃失敗フラグを設定
            return ret;
        }

        // 攻撃成功時 15% の確率でクリティカル
        if (r.nextInt(101) <= 15) {
            ret.isCritical = true;
            damage *= 2;
        }

        // "敵"にダメージを与える
        e.damage(damage);

        // 計算したダメージ量を返す
        ret.damage = damage;
        return ret;
    }
}
