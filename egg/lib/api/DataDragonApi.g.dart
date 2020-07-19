// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'DataDragonApi.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _DataDragonApi implements DataDragonApi {
  _DataDragonApi(this._dio, {this.baseUrl}) {
    ArgumentError.checkNotNull(_dio, '_dio');
    this.baseUrl ??= 'localhost:8080';
  }

  final Dio _dio;

  String baseUrl;

  @override
  getChampionList({version, locale}) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    queryParameters.removeWhere((k, v) => v == null);
    final _data = <String, dynamic>{};
    final Response<Map<String, dynamic>> _result = await _dio.request(
        '$version/data/$locale/champion.json',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = ChampionShortResponse.fromJson(_result.data);
    return value;
  }

  @override
  getChampion({version, locale, championId}) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    queryParameters.removeWhere((k, v) => v == null);
    final _data = <String, dynamic>{};
    final Response<Map<String, dynamic>> _result = await _dio.request(
        '$version/data/$locale/champion/$championId.json',
        queryParameters: queryParameters,
        options: RequestOptions(
            method: 'GET',
            headers: <String, dynamic>{},
            extra: _extra,
            baseUrl: baseUrl),
        data: _data);
    final value = ChampionsResponse.fromJson(_result.data);
    return value;
  }
}
