// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ChampionShort.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChampionShort _$ChampionShortFromJson(Map<String, dynamic> json) {
  return ChampionShort(
    id: json['id'] as String,
    key: json['key'] as String,
    name: json['name'] as String,
    title: json['title'] as String,
    blurb: json['blurb'] as String,
    info: json['info'] == null
        ? null
        : Info.fromJson(json['info'] as Map<String, dynamic>),
    image: json['image'] == null
        ? null
        : Image.fromJson(json['image'] as Map<String, dynamic>),
    tags: (json['tags'] as List)?.map((e) => e as String)?.toList(),
    partype: json['partype'] as String,
    stats: json['stats'] == null
        ? null
        : Stats.fromJson(json['stats'] as Map<String, dynamic>),
  );
}

Map<String, dynamic> _$ChampionShortToJson(ChampionShort instance) =>
    <String, dynamic>{
      'id': instance.id,
      'key': instance.key,
      'name': instance.name,
      'title': instance.title,
      'blurb': instance.blurb,
      'info': instance.info,
      'image': instance.image,
      'tags': instance.tags,
      'partype': instance.partype,
      'stats': instance.stats,
    };
