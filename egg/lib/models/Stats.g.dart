// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Stats.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Stats _$StatsFromJson(Map<String, dynamic> json) {
  return Stats(
    hp: (json['hp'] as num)?.toDouble(),
    hpPerLevel: (json['hpperlevel'] as num)?.toDouble(),
    mp: (json['mp'] as num)?.toDouble(),
    mpPerLevel: (json['mpperlevel'] as num)?.toDouble(),
    moveSpeed: (json['movespeed'] as num)?.toDouble(),
    armor: (json['armor'] as num)?.toDouble(),
    armorPerLevel: (json['armorperlevel'] as num)?.toDouble(),
    spellBlock: (json['spellblock'] as num)?.toDouble(),
    spellBlockPerLevel: (json['spellblockperlevel'] as num)?.toDouble(),
    attackRange: (json['attackrange'] as num)?.toDouble(),
    hpRegen: (json['hpregen'] as num)?.toDouble(),
    hpRegenPerLevel: (json['hpregenperlevel'] as num)?.toDouble(),
    mpRegen: (json['mpregen'] as num)?.toDouble(),
    mpRegenPerLevel: (json['mpregenperlevel'] as num)?.toDouble(),
    crit: (json['crit'] as num)?.toDouble(),
    critPerLevel: (json['critperlevel'] as num)?.toDouble(),
    attackDamage: (json['attackdamage'] as num)?.toDouble(),
    attackDamagePerLevel: (json['attackdamageperlevel'] as num)?.toDouble(),
    attackSpeed: (json['attackspeed'] as num)?.toDouble(),
  );
}

Map<String, dynamic> _$StatsToJson(Stats instance) => <String, dynamic>{
      'hp': instance.hp,
      'hpperlevel': instance.hpPerLevel,
      'mp': instance.mp,
      'mpperlevel': instance.mpPerLevel,
      'movespeed': instance.moveSpeed,
      'armor': instance.armor,
      'armorperlevel': instance.armorPerLevel,
      'spellblock': instance.spellBlock,
      'spellblockperlevel': instance.spellBlockPerLevel,
      'attackrange': instance.attackRange,
      'hpregen': instance.hpRegen,
      'hpregenperlevel': instance.hpRegenPerLevel,
      'mpregen': instance.mpRegen,
      'mpregenperlevel': instance.mpRegenPerLevel,
      'crit': instance.crit,
      'critperlevel': instance.critPerLevel,
      'attackdamage': instance.attackDamage,
      'attackdamageperlevel': instance.attackDamagePerLevel,
      'attackspeed': instance.attackSpeed,
    };
