package com.martinchooooooo.transportopendata

enum class GeoJsonType {
    FeatureCollection, Feature, Point, LineString, MultiPoint, Polygon, MultiLineString, MultiPolygon, GeometryCollection
}

data class FeatureCollectionEntity(
    val type: GeoJsonType,
    val rights: RightsEntity?,
    val features: List<FeatureEntity>
)

data class RightsEntity(
    val copyright: String?,
    val licence: String?
)

data class FeatureEntity(
    val type: GeoJsonType,
    val geometry: GeometryEntity,
    val properties: Map<String, Any>
)

data class GeometryEntity(
    val type: GeoJsonType,
    val coordinates: List<Double>
)