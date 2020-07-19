// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Champion.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Champion _$ChampionFromJson(Map<String, dynamic> json) {
  return Champion(
    id: json['id'] as String,
    key: json['key'] as String,
    name: json['name'] as String,
    title: json['title'] as String,
    image: json['image'] == null
        ? null
        : Image.fromJson(json['image'] as Map<String, dynamic>),
    skins: (json['skins'] as List)
        ?.map(
            (e) => e == null ? null : Skin.fromJson(e as Map<String, dynamic>))
        ?.toList(),
    lore: json['lore'] as String,
    blurb: json['blurb'] as String,
    allTips: (json['allytips'] as List)?.map((e) => e as String)?.toList(),
    enemyTips: (json['enemytips'] as List)?.map((e) => e as String)?.toList(),
    tags: (json['tags'] as List)?.map((e) => e as String)?.toList(),
    partype: json['partype'] as String,
    info: json['info'] == null
        ? null
        : Info.fromJson(json['info'] as Map<String, dynamic>),
    stats: json['stats'] == null
        ? null
        : Stats.fromJson(json['stats'] as Map<String, dynamic>),
    spells: (json['spells'] as List)
        ?.map(
            (e) => e == null ? null : Spell.fromJson(e as Map<String, dynamic>))
        ?.toList(),
    passive: json['passive'] == null
        ? null
        : Passive.fromJson(json['passive'] as Map<String, dynamic>),
  );
}

Map<String, dynamic> _$ChampionToJson(Champion instance) => <String, dynamic>{
      'id': instance.id,
      'key': instance.key,
      'name': instance.name,
      'title': instance.title,
      'image': instance.image,
      'skins': instance.skins,
      'lore': instance.lore,
      'blurb': instance.blurb,
      'allytips': instance.allTips,
      'enemytips': instance.enemyTips,
      'tags': instance.tags,
      'partype': instance.partype,
      'info': instance.info,
      'stats': instance.stats,
      'spells': instance.spells,
      'passive': instance.passive,
    };
