import 'package:egg/models/Image.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Item.g.dart';

@JsonSerializable()
class Item {

  @JsonKey(name: "id")final String id;
  @JsonKey(name: "name")final String name;
  @JsonKey(name: "plaintext")final String plainText;
  @JsonKey(name: "image")final Image image;

  Item({this.id, this.name, this.plainText, this.image});

  factory Item.fromJson(Map<String, dynamic> json) => _$ItemFromJson(json);
  Map<String, dynamic> toJson() => _$ItemToJson(this);
}