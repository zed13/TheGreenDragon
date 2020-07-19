import 'package:json_annotation/json_annotation.dart';

import 'Info.dart';
import 'Image.dart';
import 'Stats.dart';

part 'ChampionShort.g.dart';

@JsonSerializable()
class ChampionShort {

  @JsonKey(name: "id") final String id;
  @JsonKey(name: "key") final String key;
  @JsonKey(name: "name") final String name;
  @JsonKey(name: "title") final String title;
  @JsonKey(name: "blurb") final String blurb;
  @JsonKey(name: "info") final Info info;
  @JsonKey(name: "image") final Image image;
  @JsonKey(name: "tags") final List<String> tags;
  @JsonKey(name: "partype") final String partype;
  @JsonKey(name: "stats") final Stats stats;


  ChampionShort({this.id, this.key, this.name, this.title, this.blurb, this.info,
      this.image, this.tags, this.partype, this.stats});

  factory ChampionShort.fromJson(Map<String, dynamic> json) => _$ChampionShortFromJson(json);
  Map<String, dynamic> toJson() => _$ChampionShortToJson(this);
}