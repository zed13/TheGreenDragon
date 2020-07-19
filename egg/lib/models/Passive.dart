import 'package:json_annotation/json_annotation.dart';
import 'Image.dart';

part 'Passive.g.dart';

@JsonSerializable()
class Passive {

  @JsonKey(name: "name") final String name;
  @JsonKey(name: "description") final String description;
  @JsonKey(name: "image") final Image image;


  Passive({this.name, this.description, this.image});

  factory Passive.fromJson(Map<String, dynamic> json) => _$PassiveFromJson(json);
  Map<String, dynamic> toJson() => _$PassiveToJson(this);
}