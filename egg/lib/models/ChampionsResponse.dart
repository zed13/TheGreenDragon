import 'package:json_annotation/json_annotation.dart';

import 'Champion.dart';

part 'ChampionsResponse.g.dart';

@JsonSerializable()
class ChampionsResponse {
  @JsonKey(name: "type") final String type ;
  @JsonKey(name: "format") final String format ;
  @JsonKey(name: "version") final String version ;
  @JsonKey(name: "data") final  Map<String, Champion> data;

  ChampionsResponse({this.type, this.format, this.version, this.data});

  factory ChampionsResponse.fromJson(Map<String, dynamic> json) => _$ChampionsResponseFromJson(json);
  Map<String, dynamic> toJson() => _$ChampionsResponseToJson(this);
}