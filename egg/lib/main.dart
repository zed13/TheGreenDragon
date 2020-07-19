import 'package:egg/ItemList.dart';
import 'package:egg/repositories/ChampionRepository.dart';
import 'package:egg/repositories/ItemRepository.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

import 'ChampionList.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(
          championRepository: ChampionRepository.of(context),
          itemRepository: ItemRepository.of(context),
          title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  final ChampionRepository championRepository;
  final ItemRepository itemRepository;

  MyHomePage(
      {this.championRepository, Key key, this.title, this.itemRepository})
      : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState(
        championRepository,
        itemRepository,
      );
}

class GlobalKeys {
  static final GlobalKey CHAMPION_LIST = GlobalKey();
  static final GlobalKey ITEM_LIST = GlobalKey();
}

class _MyHomePageState extends State<MyHomePage> {
  final ChampionRepository championRepository;
  final ItemRepository itemRepository;

  int _pageIndex = 1;

  _MyHomePageState(this.championRepository, this.itemRepository);


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: IndexedStack(
          children: <Widget>[
            ChampionList(championRepository),
            ItemList(itemRepository),
          ],
          index: _pageIndex,
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _pageIndex,
        onTap: (index) {
          setState(() {
            _pageIndex = index;
          });
        },
        selectedItemColor: Colors.amber,
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: SvgPicture.asset(
              "assets/icons/account-cowboy-hat.svg",
            ),
            title: Text('Champions'),
          ),
          BottomNavigationBarItem(
              icon: SvgPicture.asset("assets/icons/hammer.svg"),
              title: Text('Items')),
        ],
      ),
    );
  }

  Widget _getPage(int index) {
    if (index == 1) {
      return ItemList(
        itemRepository,
        key: GlobalKeys.ITEM_LIST,
      );
    } else {
      return ChampionList(championRepository, key: GlobalKeys.CHAMPION_LIST);
    }
  }
}
