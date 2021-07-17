package jp.ac.ait.k19061;

import java.util.Random;

public abstract class CharacterBase {

    protected String name;    // 名前
    protected int hp;         // ヒットポイント(体力)
    protected int atk;        // 攻撃力
    protected int def;        // 防御力
    protected int agi;        // 素早さ(アジリティ)

    // コンストラクタ(全ての初期値を引数で渡す)
    public CharacterBase(String name, int hp, int atk, int def, int agi) {
        this.name = name; // 勇者の名前を引数のnameで初期化
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.agi = agi;
    }

    // 引数なしのコンストラクタは外部から封印
    private CharacterBase() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getAgi() {
        return agi;
    }

    protected int calcAttackPoint(CharacterBase e) {

        // this.getAtk() // 攻撃力
        // e.getDef() // 防御力

        double d = 0.0;

        if (this.getAtk() > e.getDef()) {
            // 勇者の攻撃力が大きい場合
            d = this.getAtk() * 1.4;
        } else if (this.getAtk() == e.getDef()) {
            // 同じ場合
            d = this.getAtk();
        } else {
            // それ以外(敵の防御力が大きい場合)
            d = this.getAtk() * 0.6;
        }

        // 小数点以下切り上げ
        int damage = (int) Math.ceil(d);

        // -3 〜 +3 の変化をランダムに与える
        int r = new Random().nextInt(7) - 3;

        // 計算後のダメージ量を返す
        return damage + r;
    }

    // 引数の相手に攻撃を行う
/*    public int attack(CharacterBase e) {
        // 引数の敵と自分自身で与えられるダメージを計算して取得
        int damage = calcAttackPoint(e);
        // "敵"にダメージを与える
        e.damage(damage);
        // 計算したダメージ量を返す
        return damage;
    }*/

    // 引数の相手に対して、攻撃を行う
    // 継承先のキャラクターによって処理が異なるので、抽象メソッド化
    public abstract AttackResult attack(CharacterBase e);

    // 自分自身がダメージを受ける(外部から呼び出される想定)
    public int damage(int d) {
        // 引数のdをhpから減算
        this.hp -= d;
        // HPが0を下回ったら0リセット
        if (this.hp < 0) {
            this.hp = 0;
        }
        return this.hp;
    }

    @Override
    public String toString() {
        return String.format("%s : HP %d, ATK %d, DEF %d, AGI %d", this.name, this.hp, this.atk, this.def, this.agi);
    }
}
