// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Passive.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Passive _$PassiveFromJson(Map<String, dynamic> json) {
  return Passive(
    name: json['name'] as String,
    description: json['description'] as String,
    image: json['image'] == null
        ? null
        : Image.fromJson(json['image'] as Map<String, dynamic>),
  );
}

Map<String, dynamic> _$PassiveToJson(Passive instance) => <String, dynamic>{
      'name': instance.name,
      'description': instance.description,
      'image': instance.image,
    };
