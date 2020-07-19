import 'dart:convert';

import 'package:egg/models/Champion.dart';
import 'package:egg/models/ChampionShort.dart';
import 'package:egg/models/ChampionShortResponse.dart';
import 'package:flutter/services.dart' show AssetBundle;
import 'package:flutter/widgets.dart';

abstract class ChampionRepository {
  ChampionRepository();

  factory ChampionRepository.of(BuildContext context) =>
      FileChampionRepository.of(context);

  Future<Champion> getChampion(String id);

  Future<List<ChampionShort>> getChampions();
}

class FileChampionRepository extends ChampionRepository {
  final AssetBundle _assets;

  FileChampionRepository(this._assets) : super();

  FileChampionRepository.of(BuildContext context)
      : this(DefaultAssetBundle.of(context));

  @override
  Future<Champion> getChampion(String id) {
    // TODO: implement getChampion
    throw UnimplementedError();
  }

  @override
  Future<List<ChampionShort>> getChampions() async {
    var json = jsonDecode(
      await _assets.loadString('assets/data/champions.json'),
    );
    return ChampionShortResponse.fromJson(json).data.values.toList();
  }
}
