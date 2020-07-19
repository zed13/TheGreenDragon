// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ChampionShortResponse.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChampionShortResponse _$ChampionShortResponseFromJson(
    Map<String, dynamic> json) {
  return ChampionShortResponse(
    type: json['type'] as String,
    format: json['format'] as String,
    version: json['version'] as String,
    data: (json['data'] as Map<String, dynamic>)?.map(
      (k, e) => MapEntry(k,
          e == null ? null : ChampionShort.fromJson(e as Map<String, dynamic>)),
    ),
  );
}

Map<String, dynamic> _$ChampionShortResponseToJson(
        ChampionShortResponse instance) =>
    <String, dynamic>{
      'type': instance.type,
      'format': instance.format,
      'version': instance.version,
      'data': instance.data,
    };
