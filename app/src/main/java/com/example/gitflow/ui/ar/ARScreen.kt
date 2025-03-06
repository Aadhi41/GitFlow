package com.example.gitflow.ui.ar

import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.contentcapture.ContentCaptureManager.Companion.isEnabled
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import kotlin.math.*

@Composable
fun ARScreen(navController: NavController) {
    val category = navController.previousBackStackEntry?.savedStateHandle?.get<String>("category")

    val modelFile = when (category) {
        "Sofa" -> "models/sofa.glb"
        "Dining Table" -> "models/dining_table.glb"
        "Chairs" -> "models/chair.glb"
        "Wardrobe" -> "models/wardrobe.glb"
        else -> null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (modelFile != null) {
            ShowARModel(modelFile)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No AR model available", fontSize = 18.sp, color = Color.Gray)
            }
        }
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .align(Alignment.TopStart)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun ShowARModel(modelFile: String) {
    var scaleFactor by remember { mutableStateOf(0.3f) }
    var rotation by remember { mutableStateOf(Rotation(y = 0f)) }
    var position by remember { mutableStateOf(Position(0f, -0.3f, -3.5f)) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val arSceneView = ArSceneView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                setupARScene(arSceneView, modelFile, scaleFactor, rotation, position)
                arSceneView
            },
            modifier = Modifier.fillMaxSize(),
            update = { arSceneView ->
                setupARScene(arSceneView, modelFile, scaleFactor, rotation, position)
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun setupARScene(arSceneView: ArSceneView, modelFile: String, scaleFactor: Float, rotation: Rotation, position: Position) {
    try {
        val modelNode = ArModelNode(
            engine = arSceneView.engine,
            modelGlbFileLocation = modelFile,
            autoAnimate = true
        ).apply {
            scale = Scale(scaleFactor, scaleFactor, scaleFactor)
            this.position = position
            this.rotation = rotation
            isEnabled = true
        }

        arSceneView.addChild(modelNode)

        // Attach touch listener
        arSceneView.setOnTouchListener { _, event ->
            handleTouch(event, modelNode)
            arSceneView.performClick()
            true
        }

    } catch (e: Exception) {
        Log.e("ARScreen", "Error loading model: ${e.message}")
    }
}


fun handleTouch(event: MotionEvent, modelNode: ArModelNode) {
    when (event.actionMasked) {
        MotionEvent.ACTION_MOVE -> {
            if (event.pointerCount == 1) {
                // One-finger drag to move the object
                val newX = event.x / 1000f - 0.5f
                val newY = -(event.y / 1000f - 0.5f)
                modelNode.position = Position(newX, newY, modelNode.position.z)
            } else if (event.pointerCount == 2) {
                // Two-finger pinch to zoom and rotate
                val distance = calculateDistance(event)
                val newScaleFactor = max(0.1f, min(0.5f, distance / 1000f)) // Limit scaling
                modelNode.scale = Scale(newScaleFactor, newScaleFactor, newScaleFactor)

                // Rotation using two-finger twist
                val angle = calculateRotation(event)
                modelNode.rotation = Rotation(y = angle)
            }
        }
    }
}

// Calculate the distance between two fingers for zooming
fun calculateDistance(event: MotionEvent): Float {
    val dx = event.getX(0) - event.getX(1)
    val dy = event.getY(0) - event.getY(1)
    return sqrt(dx * dx + dy * dy)
}

// Calculate rotation angle based on two-finger gesture
fun calculateRotation(event: MotionEvent): Float {
    val dx = event.getX(0) - event.getX(1)
    val dy = event.getY(0) - event.getY(1)
    return atan2(dy, dx) * (180 / Math.PI).toFloat()
}