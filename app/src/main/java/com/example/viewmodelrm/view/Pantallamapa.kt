package com.example.viewmodelrm.view

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import org.osmdroid.tileprovider.tilesource.XYTileSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.example.viewmodelrm.R
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import androidx.navigation.NavHostController

data class Tile(val x: Int, val y: Int, val zoomLevel: Int)

val GoogleSat = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png",
    arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com"
    )
) {
    fun getTileURLString(tile: Tile): String {
        return "${getBaseUrl()}/vt/lyrs=s&x=${tile.x}&y=${tile.y}&z=${tile.zoomLevel}"
    }
}

@Composable
fun Pantallamapa(navController: NavHostController, viewModel: MarcadorViewModel) {

    val grupoMarcador by viewModel.grupoMarcador.collectAsState(initial = emptyList())

    TileSourceFactory.addTileSource(GoogleSat)


    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.992986562609960,  -13.495383991854991)
        zoom = 15.0 // optional, default is 5.0
    }

    // define properties with remember with default value
    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    // setup mapProperties in side effect
    SideEffect {
        mapProperties = mapProperties
            //.copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties // add properties
    ){
        grupoMarcador.forEach { elementos ->
            var marcador = rememberMarkerState(
                geoPoint =  GeoPoint(elementos.marcador.coordenadaX, elementos.marcador.coordenadaY)
            )
            var icono by remember { mutableStateOf(R.drawable.restaurante) }
            var color = Color.Unspecified

            when (elementos.grupoMarcadores[0].idGrupo) {
                1 -> {
                    icono = R.drawable.restaurante
                    color = Color.White
                }
                2 -> {
                    icono = R.drawable.playa
                    color = Color.Cyan
                }
                3 -> {
                    icono = R.drawable.acuario
                    color = Color.Blue
                }
                4 -> {
                    icono = R.drawable.parque
                    color = Color.Magenta
                }
            }
            var titulo = elementos.marcador.titulo
            elementos.grupoMarcadores.forEach { elementogrupo ->
                var snipe = elementogrupo.typeGrupo

                Marker(
                    state = marcador,
                    title = titulo, // add title
                    snippet = snipe, // add snippet
                    icon = ContextCompat.getDrawable(LocalContext.current, icono)
                ){
                    Column(
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = color, shape = RoundedCornerShape(7.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // setup content of info window
                        Text(text = it.title, textAlign = TextAlign.Center)
                        Text(text = it.snippet, fontSize = 10.sp)
                    }
                }
            }
        }

    }
}
