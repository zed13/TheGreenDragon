import 'package:egg/models/Item.dart';
import 'package:egg/repositories/ItemRepository.dart';
import 'package:flutter/material.dart';

class ItemList extends StatefulWidget {
  final ItemRepository _itemRepository;

  const ItemList(this._itemRepository, {Key key}) : super(key: key);

  @override
  _ItemListState createState() => _ItemListState(_itemRepository);
}

class _ItemListState extends State<ItemList> {
  final ItemRepository _itemRepository;

  List<Item> _items = [];

  _ItemListState(this._itemRepository);

  @override
  void initState() {
    super.initState();
    _prepareData();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.separated(
      itemBuilder: (context, index) => _Item(_items[index]),
      separatorBuilder: (context, _) => Container(
        height: 0.5,
        color: Colors.grey[300],
      ),
      itemCount: _items.length,
    );
  }

  void _prepareData() {
    _itemRepository
        .getItems()
        .then((value) => {
              setState(() {
                _items = value;
              })
            })
        .catchError((error) => print("Error $error happened on items load"));
  }
}

class _Item extends StatelessWidget {
  final Item _item;

  _Item(this._item);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Image.asset(
            'assets/items_icons/${_item.image.full}',
            width: 48,
            height: 48,
          ),
          Container(
            margin: EdgeInsets.only(left: 8),
            child: Text(
              _item.name,
              style: Theme.of(context).textTheme.subtitle1,
            ),
          )
        ],
      ),
    );
  }
}
