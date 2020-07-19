import 'package:json_annotation/json_annotation.dart';

import 'ChampionShort.dart';

part 'ChampionShortResponse.g.dart';

@JsonSerializable()
class ChampionShortResponse {

  @JsonKey(name: "type") final String type;
  @JsonKey(name: "format") final String format;
  @JsonKey(name: "version") final String version;
  @JsonKey(name: "data") final Map<String, ChampionShort> data;

  ChampionShortResponse({this.type, this.format, this.version, this.data});

  factory ChampionShortResponse.fromJson(Map<String, dynamic> json) => _$ChampionShortResponseFromJson(json);
  Map<String, dynamic> toJson() => _$ChampionShortResponseToJson(this);
}