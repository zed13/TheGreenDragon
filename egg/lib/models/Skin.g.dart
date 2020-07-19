// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Skin.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Skin _$SkinFromJson(Map<String, dynamic> json) {
  return Skin(
    id: json['id'] as String,
    num: json['num'] as int,
    name: json['name'] as String,
    chromas: json['chromas'] as bool,
  );
}

Map<String, dynamic> _$SkinToJson(Skin instance) => <String, dynamic>{
      'id': instance.id,
      'num': instance.num,
      'name': instance.name,
      'chromas': instance.chromas,
    };
