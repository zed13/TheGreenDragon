import 'package:json_annotation/json_annotation.dart';

import 'Skin.dart';
import 'Image.dart';
import 'Info.dart';
import 'Stats.dart';
import 'Spell.dart';
import 'Passive.dart';

part 'Champion.g.dart';

@JsonSerializable()
class Champion {
  @JsonKey(name: "id") final String id ;
  @JsonKey(name: "key") final String key ;
  @JsonKey(name: "name") final String name ;
  @JsonKey(name: "title") final String title ;
  @JsonKey(name: "image") final Image image ;
  @JsonKey(name: "skins") final List<Skin> skins ;
  @JsonKey(name: "lore") final String lore ;
  @JsonKey(name: "blurb") final String blurb ;
  @JsonKey(name: "allytips") final List<String> allTips ;
  @JsonKey(name: "enemytips") final List<String> enemyTips ;
  @JsonKey(name: "tags") final List<String> tags ;
  @JsonKey(name: "partype") final String partype ;
  @JsonKey(name: "info") final Info info ;
  @JsonKey(name: "stats") final Stats stats ;
  @JsonKey(name: "spells") final List<Spell> spells ;
  @JsonKey(name: "passive") final Passive passive ;

  Champion({
    this.id,
    this.key,
    this.name,
    this.title,
    this.image,
    this.skins,
    this.lore,
    this.blurb,
    this.allTips,
    this.enemyTips,
    this.tags,
    this.partype,
    this.info,
    this.stats,
    this.spells,
    this.passive,
  });

  factory Champion.fromJson(Map<String, dynamic> json) => _$ChampionFromJson(json);
  Map<String, dynamic> toJson() => _$ChampionToJson(this);
}
