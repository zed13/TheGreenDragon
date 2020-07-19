import 'package:json_annotation/json_annotation.dart';

part 'Skin.g.dart';

@JsonSerializable()
class Skin {

  @JsonKey(name: "id") final String id;
  @JsonKey(name: "num") final int num;
  @JsonKey(name: "name") final String name;
  @JsonKey(name: "chromas") final bool chromas;


  Skin({this.id, this.num, this.name, this.chromas,});

  factory Skin.fromJson(Map<String, dynamic> json) => _$SkinFromJson(json);
  Map<String, dynamic> toJson() => _$SkinToJson(this);
}