// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ChampionsResponse.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChampionsResponse _$ChampionsResponseFromJson(Map<String, dynamic> json) {
  return ChampionsResponse(
    type: json['type'] as String,
    format: json['format'] as String,
    version: json['version'] as String,
    data: (json['data'] as Map<String, dynamic>)?.map(
      (k, e) => MapEntry(
          k, e == null ? null : Champion.fromJson(e as Map<String, dynamic>)),
    ),
  );
}

Map<String, dynamic> _$ChampionsResponseToJson(ChampionsResponse instance) =>
    <String, dynamic>{
      'type': instance.type,
      'format': instance.format,
      'version': instance.version,
      'data': instance.data,
    };
