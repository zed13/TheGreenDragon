import 'package:flutter/material.dart';

import 'models/ChampionShort.dart';
import 'repositories/ChampionRepository.dart';

class ChampionList extends StatefulWidget {
  final ChampionRepository championRepository;

  ChampionList(this.championRepository, {Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _ChampionListState(championRepository);
  }
}

class _ChampionListState extends State<ChampionList> {
  final ChampionRepository championRepository;

  List<ChampionShort> _items = [];

  _ChampionListState(this.championRepository);

  @override
  void initState() {
    super.initState();
    _prepareData();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.separated(
      itemCount: _items.length,
      itemBuilder: (context, position) => ChampionItem(_items[position]),
      separatorBuilder: (context, index) => Container(
        height: 0.5,
        color: Colors.grey[300],
      ),
    );
  }

  void _prepareData() async {
    championRepository.getChampions().then((value) {
      print("New list loaded. Size: ${value.length}");
      setState(() {
        _items = value;
      });
    }).catchError((error) {
      print("Error happened on loading: $error");
    });
  }
}

class ChampionItem extends StatelessWidget {
  final ChampionShort champion;

  ChampionItem(this.champion);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Image.asset(
              "assets/champion_icons/${champion.image.full}",
              width: 48,
              height: 48,
            ),
            Container(
              margin: EdgeInsets.only(left: 8),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(
                    champion.name,
                    style: Theme.of(context).textTheme.bodyText2,
                  ),
                  VerticalDivider(indent: 8),
                  Text(champion.title),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
