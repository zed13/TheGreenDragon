import 'package:json_annotation/json_annotation.dart';

import 'Image.dart';

part 'Spell.g.dart';

@JsonSerializable()
class Spell {

  @JsonKey(name: "id") final String id;
  @JsonKey(name: "name") final String name;
  @JsonKey(name: "description") final String description;
  @JsonKey(name: "image") final Image image;

  Spell({this.id, this.name, this.description, this.image});

  factory Spell.fromJson(Map<String, dynamic> json) => _$SpellFromJson(json);
  Map<String, dynamic> toJson() => _$SpellToJson(this);
}