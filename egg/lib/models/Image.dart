import 'package:json_annotation/json_annotation.dart';

part 'Image.g.dart';

@JsonSerializable()
class Image {

  @JsonKey(name: "full")  final String full;
  @JsonKey(name: "sprite")  final String sprite;
  @JsonKey(name: "group")  final String group;
  @JsonKey(name: "x")  final int x;
  @JsonKey(name: "y")  final int y;
  @JsonKey(name: "w")  final int w;
  @JsonKey(name: "h")  final int h;


  Image({this.full, this.sprite, this.group, this.x, this.y, this.w, this.h});

  factory Image.fromJson(Map<String, dynamic> json) => _$ImageFromJson(json);
  Map<String, dynamic> toJson() => _$ImageToJson(this);
}

