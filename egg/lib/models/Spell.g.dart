// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Spell.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Spell _$SpellFromJson(Map<String, dynamic> json) {
  return Spell(
    id: json['id'] as String,
    name: json['name'] as String,
    description: json['description'] as String,
    image: json['image'] == null
        ? null
        : Image.fromJson(json['image'] as Map<String, dynamic>),
  );
}

Map<String, dynamic> _$SpellToJson(Spell instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'image': instance.image,
    };
