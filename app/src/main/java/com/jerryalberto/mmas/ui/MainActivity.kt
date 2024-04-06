package com.jerryalberto.mmas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navController: NavHostController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //test get currency
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        for (countryCode in isoCountryCodes) {
            val locale = Locale("", countryCode)
            val countryName = locale.getDisplayCountry(Locale.getDefault())
            val numberFormat = NumberFormat.getCurrencyInstance(locale)
            val currency = numberFormat.currency
            val symbol = currency?.symbol
            Timber.d("numberFormat.currency${numberFormat.currency} symbol: ${symbol} : countryName is ${countryName}")
        }

        setContent {
            MmasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavHost()
                }
            }
        }
    }
}


