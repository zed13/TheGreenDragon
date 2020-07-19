import 'package:dio/dio.dart';
import 'package:egg/models/ChampionShortResponse.dart';
import 'package:egg/models/ChampionsResponse.dart';
import 'package:retrofit/retrofit.dart';

part 'DataDragonApi.g.dart';

@RestApi(baseUrl: "localhost:8080")
abstract class DataDragonApi {
  static const String defaultVersion = "10.9.1";
  static const String defaultLocale = "en_US";

  factory DataDragonApi(Dio dio, {String baseUrl}) = _DataDragonApi;

  @GET("{version}/data/{locale}/champion.json")
  Future<ChampionShortResponse> getChampionList({
    @Path("version") String version,
    @Path("locale") String locale,
  });

  @GET("{version}/data/{locale}/champion/{championId}.json")
  Future<ChampionsResponse> getChampion({
    @Path("version") String version,
    @Path("locale") String locale,
    @Path("championId") String championId,
  });
}
