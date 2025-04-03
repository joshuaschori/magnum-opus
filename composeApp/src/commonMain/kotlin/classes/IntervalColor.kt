package classes

import androidx.compose.ui.graphics.Color
import settings
import ui.theme.defaultInterval0ColorBlue
import ui.theme.defaultInterval0ColorGreen
import ui.theme.defaultInterval0ColorRed
import ui.theme.defaultInterval10ColorBlue
import ui.theme.defaultInterval10ColorGreen
import ui.theme.defaultInterval10ColorRed
import ui.theme.defaultInterval11ColorBlue
import ui.theme.defaultInterval11ColorGreen
import ui.theme.defaultInterval11ColorRed
import ui.theme.defaultInterval1ColorBlue
import ui.theme.defaultInterval1ColorGreen
import ui.theme.defaultInterval1ColorRed
import ui.theme.defaultInterval2ColorBlue
import ui.theme.defaultInterval2ColorGreen
import ui.theme.defaultInterval2ColorRed
import ui.theme.defaultInterval3ColorBlue
import ui.theme.defaultInterval3ColorGreen
import ui.theme.defaultInterval3ColorRed
import ui.theme.defaultInterval4ColorBlue
import ui.theme.defaultInterval4ColorGreen
import ui.theme.defaultInterval4ColorRed
import ui.theme.defaultInterval5ColorBlue
import ui.theme.defaultInterval5ColorGreen
import ui.theme.defaultInterval5ColorRed
import ui.theme.defaultInterval6ColorBlue
import ui.theme.defaultInterval6ColorGreen
import ui.theme.defaultInterval6ColorRed
import ui.theme.defaultInterval7ColorBlue
import ui.theme.defaultInterval7ColorGreen
import ui.theme.defaultInterval7ColorRed
import ui.theme.defaultInterval8ColorBlue
import ui.theme.defaultInterval8ColorGreen
import ui.theme.defaultInterval8ColorRed
import ui.theme.defaultInterval9ColorBlue
import ui.theme.defaultInterval9ColorGreen
import ui.theme.defaultInterval9ColorRed
import ui.theme.defaultIntervalColorAlpha

class IntervalColor(intervalValue: Int) {
    val backgroundColor = when (intervalValue) {
        0 -> Color(
            red = settings.getInt("interval0ColorRed", defaultInterval0ColorRed),
            green = settings.getInt("interval0ColorGreen", defaultInterval0ColorGreen),
            blue = settings.getInt("interval0ColorBlue", defaultInterval0ColorBlue),
            alpha = settings.getInt("interval0ColorAlpha", defaultIntervalColorAlpha)
        )
        1 -> Color(
            red = settings.getInt("interval1ColorRed", defaultInterval1ColorRed),
            green = settings.getInt("interval1ColorGreen", defaultInterval1ColorGreen),
            blue = settings.getInt("interval1ColorBlue", defaultInterval1ColorBlue),
            alpha = settings.getInt("interval1ColorAlpha", defaultIntervalColorAlpha)
        )
        2 -> Color(
            red = settings.getInt("interval2ColorRed", defaultInterval2ColorRed),
            green = settings.getInt("interval2ColorGreen", defaultInterval2ColorGreen),
            blue = settings.getInt("interval2ColorBlue", defaultInterval2ColorBlue),
            alpha = settings.getInt("interval2ColorAlpha", defaultIntervalColorAlpha)
        )
        3 -> Color(
            red = settings.getInt("interval3ColorRed", defaultInterval3ColorRed),
            green = settings.getInt("interval3ColorGreen", defaultInterval3ColorGreen),
            blue = settings.getInt("interval3ColorBlue", defaultInterval3ColorBlue),
            alpha = settings.getInt("interval3ColorAlpha", defaultIntervalColorAlpha)
        )
        4 -> Color(
            red = settings.getInt("interval4ColorRed", defaultInterval4ColorRed),
            green = settings.getInt("interval4ColorGreen", defaultInterval4ColorGreen),
            blue = settings.getInt("interval4ColorBlue", defaultInterval4ColorBlue),
            alpha = settings.getInt("interval4ColorAlpha", defaultIntervalColorAlpha)
        )
        5 -> Color(
            red = settings.getInt("interval5ColorRed", defaultInterval5ColorRed),
            green = settings.getInt("interval5ColorGreen", defaultInterval5ColorGreen),
            blue = settings.getInt("interval5ColorBlue", defaultInterval5ColorBlue),
            alpha = settings.getInt("interval5ColorAlpha", defaultIntervalColorAlpha)
        )
        6 -> Color(
            red = settings.getInt("interval6ColorRed", defaultInterval6ColorRed),
            green = settings.getInt("interval6ColorGreen", defaultInterval6ColorGreen),
            blue = settings.getInt("interval6ColorBlue", defaultInterval6ColorBlue),
            alpha = settings.getInt("interval6ColorAlpha", defaultIntervalColorAlpha)
        )
        7 -> Color(
            red = settings.getInt("interval7ColorRed", defaultInterval7ColorRed),
            green = settings.getInt("interval7ColorGreen", defaultInterval7ColorGreen),
            blue = settings.getInt("interval7ColorBlue", defaultInterval7ColorBlue),
            alpha = settings.getInt("interval7ColorAlpha", defaultIntervalColorAlpha)
        )
        8 -> Color(
            red = settings.getInt("interval8ColorRed", defaultInterval8ColorRed),
            green = settings.getInt("interval8ColorGreen", defaultInterval8ColorGreen),
            blue = settings.getInt("interval8ColorBlue", defaultInterval8ColorBlue),
            alpha = settings.getInt("interval8ColorAlpha", defaultIntervalColorAlpha)
        )
        9 -> Color(
            red = settings.getInt("interval9ColorRed", defaultInterval9ColorRed),
            green = settings.getInt("interval9ColorGreen", defaultInterval9ColorGreen),
            blue = settings.getInt("interval9ColorBlue", defaultInterval9ColorBlue),
            alpha = settings.getInt("interval9ColorAlpha", defaultIntervalColorAlpha)
        )
        10 -> Color(
            red = settings.getInt("interval10ColorRed", defaultInterval10ColorRed),
            green = settings.getInt("interval10ColorGreen", defaultInterval10ColorGreen),
            blue = settings.getInt("interval10ColorBlue", defaultInterval10ColorBlue),
            alpha = settings.getInt("interval10ColorAlpha", defaultIntervalColorAlpha)
        )
        11 -> Color(
            red = settings.getInt("interval11ColorRed", defaultInterval11ColorRed),
            green = settings.getInt("interval11ColorGreen", defaultInterval11ColorGreen),
            blue = settings.getInt("interval11ColorBlue", defaultInterval11ColorBlue),
            alpha = settings.getInt("interval11ColorAlpha", defaultIntervalColorAlpha)
        )
        else -> Color.Black
    }

    val textColor = when (intervalValue) {
        0 -> if (
            (settings.getInt("interval0ColorRed", defaultInterval0ColorRed) * 0.299 +
                    settings.getInt("interval0ColorGreen", defaultInterval0ColorGreen) * 0.587 +
                    settings.getInt("interval0ColorBlue", defaultInterval0ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        1 -> if (
            (settings.getInt("interval1ColorRed", defaultInterval1ColorRed) * 0.299 + settings.getInt("interval1ColorGreen", defaultInterval1ColorGreen) * 0.587 + settings.getInt("interval1ColorBlue", defaultInterval1ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        2 -> if (
            (settings.getInt("interval2ColorRed", defaultInterval2ColorRed) * 0.299 + settings.getInt("interval2ColorGreen", defaultInterval2ColorGreen) * 0.587 + settings.getInt("interval2ColorBlue", defaultInterval2ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        3 -> if (
            (settings.getInt("interval3ColorRed", defaultInterval3ColorRed) * 0.299 + settings.getInt("interval3ColorGreen", defaultInterval3ColorGreen) * 0.587 + settings.getInt("interval3ColorBlue", defaultInterval3ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        4 -> if (
            (settings.getInt("interval4ColorRed", defaultInterval4ColorRed) * 0.299 + settings.getInt("interval4ColorGreen", defaultInterval4ColorGreen) * 0.587 + settings.getInt("interval4ColorBlue", defaultInterval4ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        5 -> if (
            (settings.getInt("interval5ColorRed", defaultInterval5ColorRed) * 0.299 + settings.getInt("interval5ColorGreen", defaultInterval5ColorGreen) * 0.587 + settings.getInt("interval5ColorBlue", defaultInterval5ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        6 -> if (
            (settings.getInt("interval6ColorRed", defaultInterval6ColorRed) * 0.299 + settings.getInt("interval6ColorGreen", defaultInterval6ColorGreen) * 0.587 + settings.getInt("interval6ColorBlue", defaultInterval6ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        7 -> if (
            (settings.getInt("interval7ColorRed", defaultInterval7ColorRed) * 0.299 + settings.getInt("interval7ColorGreen", defaultInterval7ColorGreen) * 0.587 + settings.getInt("interval7ColorBlue", defaultInterval7ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        8 -> if (
            (settings.getInt("interval8ColorRed", defaultInterval8ColorRed) * 0.299 + settings.getInt("interval8ColorGreen", defaultInterval8ColorGreen) * 0.587 + settings.getInt("interval8ColorBlue", defaultInterval8ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        9 -> if (
            (settings.getInt("interval9ColorRed", defaultInterval9ColorRed) * 0.299 + settings.getInt("interval9ColorGreen", defaultInterval9ColorGreen) * 0.587 + settings.getInt("interval9ColorBlue", defaultInterval9ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        10 -> if (
            (settings.getInt("interval10ColorRed", defaultInterval10ColorRed) * 0.299 + settings.getInt("interval10ColorGreen", defaultInterval10ColorGreen) * 0.587 + settings.getInt("interval10ColorBlue", defaultInterval10ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        11 -> if (
            (settings.getInt("interval11ColorRed", defaultInterval11ColorRed) * 0.299 + settings.getInt("interval11ColorGreen", defaultInterval11ColorGreen) * 0.587 + settings.getInt("interval11ColorBlue", defaultInterval11ColorBlue) * 0.114) > 186
        ) { Color.Black } else { Color.White }
        else -> Color.Black
    }
}