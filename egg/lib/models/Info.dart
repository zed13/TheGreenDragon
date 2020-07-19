import 'package:json_annotation/json_annotation.dart';

part 'Info.g.dart';

@JsonSerializable()
class Info {

  @JsonKey(name: "attack") final int attack;
  @JsonKey(name: "defense") final int defense;
  @JsonKey(name: "magic") final int magic;
  @JsonKey(name: "difficulty") final int difficulty;

  Info({this.attack, this.defense, this.magic, this.difficulty,});

  factory Info.fromJson(Map<String, dynamic> json) => _$InfoFromJson(json);
  Map<String, dynamic> toJson() => _$InfoToJson(this);
}