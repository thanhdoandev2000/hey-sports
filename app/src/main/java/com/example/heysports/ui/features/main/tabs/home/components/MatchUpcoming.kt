package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.Thunderstorm
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.StringRes
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.domain.models.MatchWeather
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MatchUpcoming(
    match: MatchUpcomingDto? = null,
    weather: MatchWeather? = null,
    isLoading: Boolean = false,
    isWeatherLoading: Boolean = false,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    onMarkAttendance: (String) -> Unit = {},
    onOpenMaps: (String) -> Unit = {}
) {
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier.size(size_24dp),
                shimmer = shimmer
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = HighlightBackground,
                            shape = CardShape
                        )
                ) {
                    JPIcon(
                        icon = Icons.Outlined.AccessTime,
                        tint = PrimaryGreen,
                        modifier = Modifier.padding(size_4dp)
                    )
                }
            }
            JPSpacer(width = size_4dp)
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(size_16dp),
                shimmer = shimmer
            ) {
                JPText(
                    text = stringResource(R.string.homeNextMatches),
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = size_15sp
                )
            }
        }
        JPSpacer(height = size_8dp)
        ShimmerBox(
            isLoading = isLoading || isWeatherLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_100dp),
            shimmer = shimmer
        ) {
            Column(
                modifier = Modifier
                    .background(PrimaryGreen, CardShape)
                    .wrapContentSize()
                    .padding(size_8dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                JPText(
                    text = stringResource(R.string.homeMatchStart),
                    color = Color.White.copy(0.6f),
                    fontSize = size_12sp,
                    fontWeight = FontWeight.SemiBold
                )
                JPSpacer(height = size_4dp)
                CountdownTimer(matchTime = match?.matchTime)
            }
        }
        JPSpacer(height = size_10dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_40dp),
            shimmer = shimmer
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size_10dp))
                    .background(HighlightBackground)
                    .padding(size_10dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherSummary(weather)
            }
        }

        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_16dp),
            shimmer = shimmer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPIcon(icon = Icons.Filled.LocationOn, tint = Color.Gray)
                JPSpacer(width = size_4dp)
                JPText(text = match?.pitchAddress, color = Color.DarkGray)
            }
        }
        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_16dp),
            shimmer = shimmer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPIcon(icon = Icons.Filled.Group, tint = Color.Gray)
                JPSpacer(width = size_4dp)
                JPText(
                    text = stringResource(
                        R.string.commonMatches,
                        match?.hostTeamName.getValue(),
                        match?.guestTeamName.getValue()
                    ),
                    color = Color.DarkGray
                )
            }
        }
        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_50dp),
            shimmer = shimmer
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPButton(
                    label = R.string.homeOpenMaps,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(size_line, PrimaryGreen),
                    bgColor = Color.White,
                    textColor = PrimaryGreen,
                    height = size_42dp
                ) { onOpenMaps(match?.id.toString()) }
                JPSpacer(width = size_16dp)
                JPButton(
                    label = R.string.homeAttendance,
                    modifier = Modifier.weight(1f),
                    height = size_42dp
                ) { onMarkAttendance(match?.id.toString()) }
            }
        }
    }
}

@Composable
private fun RowScope.WeatherSummary(weather: MatchWeather?) {
    val presentation = weather?.weatherCode?.let(::weatherPresentation)
    Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_8dp)
    ) {
        JPIcon(
            icon = presentation?.icon ?: Icons.Outlined.CloudOff,
            tint = PrimaryGreen,
            size = size_20dp
        )
        JPText(
            text = if (weather == null) {
                stringResource(R.string.weatherUnavailable)
            } else {
                stringResource(
                    R.string.weatherTemperatureAndCondition,
                    weather.temperatureCelsius,
                    stringResource(presentation?.label ?: R.string.weatherUnavailable)
                )
            },
            color = PrimaryGreen,
            fontSize = size_12sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
    JPText(
        text = when {
            weather == null -> stringResource(R.string.weatherUpdatingLater)
            weather.isSuitableForFootball() -> stringResource(R.string.weatherSuitable)
            weather.precipitationProbability != null ->
                stringResource(
                    R.string.weatherRainChance,
                    weather.precipitationProbability
                )

            else -> stringResource(R.string.weatherUseCaution)
        },
        color = TextSecondary,
        fontSize = size_11sp,
        maxLines = 1
    )
}

private data class WeatherPresentation(
    val icon: ImageVector,
    @param:StringRes val label: Int
)

private fun weatherPresentation(code: Int): WeatherPresentation {
    return when (code) {
        0 -> WeatherPresentation(Icons.Outlined.WbSunny, R.string.weatherClear)
        1, 2 -> WeatherPresentation(Icons.Outlined.Cloud, R.string.weatherPartlyCloudy)
        3 -> WeatherPresentation(Icons.Outlined.Cloud, R.string.weatherCloudy)
        45, 48 -> WeatherPresentation(Icons.Outlined.Dehaze, R.string.weatherFoggy)
        in 51..67, in 80..82 ->
            WeatherPresentation(Icons.Outlined.Grain, R.string.weatherRainy)

        in 71..77, 85, 86 ->
            WeatherPresentation(Icons.Outlined.AcUnit, R.string.weatherSnowy)

        in 95..99 ->
            WeatherPresentation(Icons.Outlined.Thunderstorm, R.string.weatherStormy)

        else -> WeatherPresentation(Icons.Outlined.Cloud, R.string.weatherCloudy)
    }
}

private fun MatchWeather.isSuitableForFootball(): Boolean {
    val rainChance = precipitationProbability ?: 0
    val severeWeather = weatherCode in 61..99
    return temperatureCelsius in 15..34 && rainChance < 40 && !severeWeather
}

@Composable
@Preview
@AppPreview
private fun MatchUpcomingPreview() {
    MatchUpcoming()
}
