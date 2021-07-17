package jp.ac.ait.k19061;

import java.util.Scanner;

public class SimpleRPG {

    public static void main(String[] args) {

        // 画面からの入力待受用
        Scanner sc = new Scanner(System.in);

        System.out.println("SimpleRPG");
        System.out.println("---------------------------------------------");

        // 画面から勇者の名前を入力
        System.out.println("勇者の名前を入力してください。");
        String hero_name = sc.nextLine();   // 勇者の名前を入力

        // ユーザに職業を選択させる
        System.out.println("職業を選んでください(0: タンク, 1:武闘家, 2:暗殺者 , 3: おまかせ)");
        String input = sc.nextLine();
        int jobId = Integer.parseInt(input);

        // 勇者を生成
        Hero h = CharacterFactory.createHero(hero_name, jobId);
        IHeroJob job = (IHeroJob)h;

        // 割り当てられた職業を表示する
        System.out.println(h.getName() + "さんの職業は" + job.getJobName() + "となりました");

        System.out.println(hero_name + "さんのステータスを割り振ります。");

        // 勇者のステータスを表示しましょう
        System.out.println("---------------------------------------------");
        printStatus(h);
        System.out.println("---------------------------------------------");
        System.out.println("勇者" + h.getName() + "はなんやかんやで世界を救う旅に出ました。");
        System.out.println("目の前に影が現れました。");

        // 敵生成
        Enemy e = CharacterFactory.createEnemy();

        // ステータスを表示
        System.out.println("---------------------------------------------");
        printStatus(e);
        System.out.println("---------------------------------------------");
        System.out.println("襲いかかってきた" + e.getName() + "を倒しましょう。");

        // 勇者の素早さと敵の素早さを比較
        if (h.getAgi() >= e.getAgi()) {

            // 勇者が早い:

            // 無限ループの開始
            while (true) {
                // 勇者の行動
                if (!heroAction(h, e)) break;

                // 敵の行動
                if (!enemyAction(h, e)) break;
            // 無限ループの終端
            }
        //
        } else {
            // 敵が早い:
            // 無限ループの開始
            while (true) {

                // 敵の行動
                if (!enemyAction(h, e)) break;

                // 勇者の行動
                if (!heroAction(h, e)) break;
            } // 無限ループの終端
        }
        // 終了
    }

    // 仕様にはないけど、ステータス表示はメソッド化したほうが楽なのでprivateで作成
    private static void printStatus(Hero h) {
        System.out.println("勇者: " + h.getName() + " HP:" + h.getHp() + " ATK:" + h.getAtk() + " DEF:" + h.getDef() + " AGI:" + h.getAgi());
    }
    private static void printStatus(Enemy e) {
        System.out.println("敵　: " + e.getName() + " HP:" + e.getHp() + " ATK:" + e.getAtk() + " DEF:" + e.getDef() + " AGI:" + e.getAgi());
    }
    private static void printStatus(Hero h, Enemy e) {
        System.out.println("---------------------------------------------");
        printStatus(h);
        printStatus(e);
        System.out.println("---------------------------------------------");
    }

    // ゲーム終了時にfalseを返す
    private static boolean heroAction(Hero h, Enemy e) {

        // 職業データへのダウンキャストを行い、特殊行動へのアクセスを可能にする
        IHeroJob job = (IHeroJob)h;

        Scanner sc = new Scanner(System.in);

        // 勇者の行動決定メッセージ
        System.out.println("勇者の行動を選択してください。(1: 攻撃, 2: " + job.getSpecialAttackName() + ", それ以外: 逃亡)");
        String hero_action = sc.nextLine();

        // "1"が選択されたか
        if (hero_action.equals("1")) {
            // 敵に攻撃
            AttackResult damage_to_e = h.attack(e);
            if (damage_to_e.isFailure) {
                System.out.println(h.getName() + "の攻撃: 失敗！");
            } else if (damage_to_e.isCritical) {
                System.out.println(h.getName() + "の攻撃: クリティカルヒット！！ " + e.getName() + "に" + damage_to_e.damage + "のダメージ");
            } else {
                System.out.println(h.getName() + "の攻撃: " + e.getName() + "に" + damage_to_e.damage + "のダメージ");
            }

            printStatus(h, e);

            // 敵のHPが 0か
            if (e.getHp() <= 0) {
                // ゲームクリアによりプログラムを終了
                System.out.println(e.getName() + "は倒れました。勇者" + h.getName() + "の旅はまだまだ続きますが、一旦ここまで。");
                return false;
            }
        } else if(hero_action.equals("2")) {
            // 特殊行動が選択されている
            AttackResult damage_to_e = job.specialAttack(e);
            if (damage_to_e.isFailure) {
                System.out.println(h.getName() + "の" + job.getSpecialAttackName() + ": 失敗！");
            } else if (damage_to_e.isCritical) {
                System.out.println(h.getName() + "の" + job.getSpecialAttackName() + ": クリティカルヒット！！ " + e.getName() + "に" + damage_to_e.damage + "のダメージ");
            } else {
                System.out.println(h.getName() + "の" + job.getSpecialAttackName() + ": " + e.getName() + "に" + damage_to_e.damage + "のダメージ");
            }

            printStatus(h, e);

            // 敵のHPが 0か
            if (e.getHp() <= 0) {
                // ゲームクリアによりプログラムを終了
                System.out.println(e.getName() + "は倒れました。勇者" + h.getName() + "の旅はまだまだ続きますが、一旦ここまで。");
                return false;
            }
        } else {
            // それ以外
            // 逃亡によりプログラムを終了
            System.out.println("勇者" + h.getName() + "は" + e.getName() + "から逃げ出しました。");
            System.out.println("逃げ出した" + h.getName() + "は勇者としての旅をあきらめました。ゲームオーバーです。");
            return false;
        }

        return true;
    }

    // ゲーム終了時にfalseを返す
    private static boolean enemyAction(Hero h, Enemy e) {

        AttackResult damage_to_h = e.attack(h);
        if (damage_to_h.isCritical) {
            System.out.println(e.getName() + "の攻撃: クリティカルヒット！！ 勇者" + h.getName() + "は" + damage_to_h.damage + "のダメージ");
        } else {
            System.out.println(e.getName() + "の攻撃: 勇者" + h.getName() + "は" + damage_to_h.damage + "のダメージ");
        }

        printStatus(h, e);

        // 勇者のHPが 0 か
        if (h.getHp() <= 0) {
            // ゲームオーバーによりプログラム終了
            System.out.println("勇者" + h.getName() + "は" + e.getName() + "の苛烈な攻撃により命を落としました……。");
            System.out.println("ゲームオーバー");
            return false;
        }

        return true;
    }
}
