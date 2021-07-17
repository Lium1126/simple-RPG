package jp.ac.ait.k19061;

public interface IHeroJob {
    // 職業名を返す
    String getJobName();

    // その職業の特殊行動名を返す
    String getSpecialAttackName();

    // その職業の特殊行動を行う
    AttackResult specialAttack(CharacterBase e);
}
