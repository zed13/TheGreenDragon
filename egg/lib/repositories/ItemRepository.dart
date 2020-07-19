import 'dart:convert';

import 'package:egg/models/Item.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

abstract class ItemRepository {
  ItemRepository();

  factory ItemRepository.of(BuildContext context) =>
      FileItemRepository.of(context);

  Future<List<Item>> getItems();
}

class FileItemRepository extends ItemRepository {
  final AssetBundle _assets;

  FileItemRepository(this._assets);

  FileItemRepository.of(BuildContext context)
      : this(DefaultAssetBundle.of(context));

  Map<String, dynamic> _data;

  @override
  Future<List<Item>> getItems() async {
    if (_data == null) {
      _data = jsonDecode(
          await _assets.loadString('assets/data/items.json'))['data'];
    }
    print('_data.length=${_data.length}');
    return _data.values.map((e) => Item.fromJson(e)).toList();
  }
}
