package com.kendis.cardview

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kendis.cardview.ui.theme.CardViewTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CryptoCardBackground()
                }
            }
        }
    }
}

@Composable
fun CryptoCardBackground(
    modifier: Modifier = Modifier,
    cardBackground: Color = MaterialTheme.colorScheme.surface,
    bubbleColor: Color = MaterialTheme.colorScheme.surface,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    cardSize: Dp = 150.dp
) {
    val radius = cardSize.value / 2f
    Box {
        Card(
            modifier = modifier
                .size(cardSize)
                .clip(RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(containerColor = cardBackground)
        ) {
            Canvas(modifier = Modifier.size(cardSize), onDraw = {
                drawRect(
                    color = backgroundColor,
                    topLeft = Offset(x = size.width - radius + (radius * 0.2f), y = 12f),
                    size = size / 2f
                )
                drawRect(
                    color = backgroundColor,
                    topLeft = Offset(x = cardSize.value * 1.3f, y = cardSize.value * -1f),
                    size = size / 2f,
                )
                // White big one
                drawCircle(
                    color = backgroundColor,
                    radius = cardSize.value / 1.5f,
                    center = Offset(
                        x = size.width - radius + (radius * 0.2f),
                        y = radius - (radius * 0.2f)
                    )
                )
                // Black top
                drawCircle(
                    color = cardBackground,
                    radius = radius * 0.8f,
                    center = Offset(
                        x = size.width / 2.14f,
                        y = radius - (radius * 0.2f)
                    )
                )
                // Black right
                drawCircle(
                    color = cardBackground,
                    radius = radius * 0.8f,
                    center = Offset(
                        x = size.width - radius + (radius * 0.2f),
                        y = radius + (radius * 1.93f)
                    )
                )
                drawRect(
                    color = backgroundColor,
                    topLeft = Offset(x = size.width - (cardSize.value / 2f) - 7.5f, y = 0f),
                    size = size / 5f
                )
                // Bubble
                drawCircle(
                    color = bubbleColor,
                    radius = radius * 0.8f,
                    center = Offset(
                        x = size.width - radius + (radius * 0.2f),
                        y = radius - (radius * 0.2f)
                    )
                )
            })
        }
    }
}

@Composable
private fun ChangeIcon(
    modifier: Modifier = Modifier,
    valueChange: Int = -18,
) {
    val tint: Color
    val contentDescription: String

    if (valueChange > 0) {
        tint = Color(0xFFFFFFFF)
        contentDescription = "Arrow Up"
    } else {
        tint = Color(0xFFa97d72)
        contentDescription = "Arrow Down"
    }

    Icon(
        modifier = (if (valueChange > 0) modifier.rotate(180f) else modifier).size(17.dp),
        painter = painterResource(id = R.drawable.ic_baseline_arrow_outward),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
private fun CryptoCardContent(
    data: CryptoCardData,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.size(150.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "${data.valueChange}%",
                    color = textColor,
                    style = MaterialTheme.typography.labelMedium
                )

                ChangeIcon(valueChange = data.valueChange)
            }

            Icon(
                painter = painterResource(id = data.icon),
                contentDescription = "Card Icon",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = data.name,
                color = textColor,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${data.value}",
                color = textColor,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = formatCurrentTotal(data.currentTotal),
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CryptoCard(
    modifier: Modifier = Modifier,
    data: CryptoCardData = CryptoCardData(
        name = "Bitcoin",
        icon = R.drawable.ic_sharp_adb_24,
        value = 3.689087f,
        valueChange = -18,
        currentTotal = 98160
    )
) {
    Box(modifier = modifier) {
        CryptoCardBackground()
        CryptoCardContent(data = data)
    }
}

private fun formatCurrentTotal(currentTotal: Long): String {
    val decimalFormat = DecimalFormat("$#,###")
    return decimalFormat.format(currentTotal)
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CryptoCardBackgroundPreview() {
    CardViewTheme {
        CryptoCardBackground()
    }
}

@Preview
@Composable
fun ChangeIconPreview() {
    CardViewTheme {
        ChangeIcon()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CryptoCardContentPreview() {
    CardViewTheme {
        CryptoCard()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CryptoCardContentNightModePreview() {
    CardViewTheme {
        CryptoCard()
    }
}
