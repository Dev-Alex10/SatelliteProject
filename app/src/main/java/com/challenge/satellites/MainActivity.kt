package com.challenge.satellites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.challenge.satellites.ui.theme.SatellitesTheme
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SatellitesTheme {
                val api = remember {
                    val retrofit = Retrofit.Builder().baseUrl("https://tle.ivanstanojevic.me/api/")
                        .addConverterFactory(GsonConverterFactory.create()).build()

                    retrofit.create(TleApi::class.java)
                }
                var errorMessage by remember {
                    mutableStateOf("Loading...")
                }

                var satellites by remember {
                    mutableStateOf<List<SatelliteCollection.Member>>(
                        emptyList()
                    )
                }

                LaunchedEffect(Unit) {
                    try {
                        withTimeout(30_000) {
                            satellites = api.getCollection().body()?.member.orEmpty()
                        }
                    } catch (exception: TimeoutCancellationException) {
                        errorMessage = exception.message ?: "Timeout"
                        satellites = emptyList()
                    } catch (exception: Exception) {
                        errorMessage = "Network error: ${exception.message}"
                        satellites = emptyList()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (satellites.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = errorMessage, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    } else {
                        LazyColumn(Modifier.padding(innerPadding)) {
                            itemsIndexed(satellites) { index, satellite ->
                                Text(
                                    text = "${index + 1} ${satellite.name}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                                Text(
                                    text = "${satellite.line1}\n${satellite.line2}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState())
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}