@file:OptIn(ExperimentalAnimationApi::class)

package com.mkchtv.cleantemplate.playground.topic.animatedvisibility

import android.animation.TimeInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedVisibilityTopic() = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
) {
    Title("Box")
    VisibilityInBox()
    Title("Row")
    VisibilityInRow()
    Title("Row (First item animated only)")
    VisibilityInRowOneItemAnimated()
    Title("Row (Custom anim spec)")
    VisibilityInRowCustomSpec()
    Title("Column")
    VisibilityInColumn()
}

@Composable
private fun VisibilityInBox(
) = ContainerWithToggle { toggleIsOn ->
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
    }
}

@Composable
private fun VisibilityInRow(
) = ContainerWithToggle { toggleIsOn ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
    }
}

@Composable
private fun VisibilityInRowOneItemAnimated(
) = ContainerWithToggle { toggleIsOn ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
        GreenBox()
        GreenBox()
    }
}

@Composable
private fun VisibilityInRowCustomSpec(
) = ContainerWithToggle { toggleIsOn ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(
            visible = toggleIsOn,
            enter = expandHorizontally(
                tween(
                    durationMillis = 1000,
                    easing = AnticipateOvershootInterpolator().toEasing()
                )
            ) + fadeIn(
                tween(
                    durationMillis = 2000
                )
            ),
        ) {
            GreenBox()
        }
        GreenBox()
        GreenBox()
    }
}

@Composable
private fun VisibilityInColumn(
) = ContainerWithToggle { toggleIsOn ->
    Column(
        modifier = Modifier
            .height(240.dp)
            .width(80.dp)
            .border(1.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
        AnimatedVisibility(
            visible = toggleIsOn,
        ) {
            GreenBox()
        }
    }
}

@Composable
private fun Title(text: String) = Text(
    modifier = Modifier.padding(bottom = 8.dp),
    text = text,
)

@Composable
private fun ContainerWithToggle(
    content: @Composable (toggleIsOn: Boolean) -> Unit,
) {
    var toggle by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(70f)
                .padding(end = 8.dp),
        ) {
            content(toggleIsOn = toggle)
        }
        Button(
            modifier = Modifier.weight(30f),
            onClick = {
                toggle = toggle.not()
            }) {
            Text(text = "Toggle")
        }
    }
}

@Composable
private fun GreenBox() = Box(
    modifier = Modifier
        .size(60.dp)
        .padding(4.dp)
        .background(Color.Green),
)

private fun TimeInterpolator.toEasing() = Easing { x ->
    getInterpolation(x)
}