import 'package:json_annotation/json_annotation.dart';

part 'Stats.g.dart';

@JsonSerializable()
class Stats {
  @JsonKey(name: "hp")
  final double hp;
  @JsonKey(name: "hpperlevel")
  final double hpPerLevel;
  @JsonKey(name: "mp")
  final double mp;
  @JsonKey(name: "mpperlevel")
  final double mpPerLevel;
  @JsonKey(name: "movespeed")
  final double moveSpeed;
  @JsonKey(name: "armor")
  final double armor;
  @JsonKey(name: "armorperlevel")
  final double armorPerLevel;
  @JsonKey(name: "spellblock")
  final double spellBlock;
  @JsonKey(name: "spellblockperlevel")
  final double spellBlockPerLevel;
  @JsonKey(name: "attackrange")
  final double attackRange;
  @JsonKey(name: "hpregen")
  final double hpRegen;
  @JsonKey(name: "hpregenperlevel")
  final double hpRegenPerLevel;
  @JsonKey(name: "mpregen")
  final double mpRegen;
  @JsonKey(name: "mpregenperlevel")
  final double mpRegenPerLevel;
  @JsonKey(name: "crit")
  final double crit;
  @JsonKey(name: "critperlevel")
  final double critPerLevel;
  @JsonKey(name: "attackdamage")
  final double attackDamage;
  @JsonKey(name: "attackdamageperlevel")
  final double attackDamagePerLevel;
  @JsonKey(name: "attackspeed")
  final double attackSpeed;

  Stats({
    this.hp,
    this.hpPerLevel,
    this.mp,
    this.mpPerLevel,
    this.moveSpeed,
    this.armor,
    this.armorPerLevel,
    this.spellBlock,
    this.spellBlockPerLevel,
    this.attackRange,
    this.hpRegen,
    this.hpRegenPerLevel,
    this.mpRegen,
    this.mpRegenPerLevel,
    this.crit,
    this.critPerLevel,
    this.attackDamage,
    this.attackDamagePerLevel,
    this.attackSpeed,
  });

  factory Stats.fromJson(Map<String, dynamic> json) => _$StatsFromJson(json);
  Map<String, dynamic> toJson() => _$StatsToJson(this);
}
