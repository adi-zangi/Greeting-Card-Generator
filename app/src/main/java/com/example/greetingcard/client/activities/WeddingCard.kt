package com.example.greetingcard.client.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.R
import com.example.greetingcard.ui.theme.GreetingCardTheme

class WeddingCard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeddingCard(
                        intent = intent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WeddingCard(intent: Intent, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.party)
    val senderName = getSenderName(intent)
    val firstRecipientName = getFirstRecipientName(intent)
    val secondRecipientName = getSecondRecipientName(intent)
    val message = getWeddingMessage(firstRecipientName, secondRecipientName)
    val from = getWeddingSignature(senderName)

    Box(modifier) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.75F
        )
        WeddingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Composable
fun WeddingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            lineHeight = 52.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Composable
private fun getSenderName(i: Intent) : String {
    return i.getStringExtra(stringResource(R.string.key_wedding_card_sender)) ?: ""
}

@Composable
private fun getFirstRecipientName(i: Intent) : String {
    return i.getStringExtra(stringResource(R.string.key_wedding_card_first_recipient)) ?: ""
}

@Composable
private fun getSecondRecipientName(i: Intent) : String {
    return i.getStringExtra(stringResource(R.string.key_wedding_card_second_recipient)) ?: ""
}

@Composable
private fun getWeddingMessage(firstRecipientName: String,
                               secondRecipientName: String) : String {
    return String.format("%s %s %s %s%s",
        stringResource(R.string.text_happy_wedding),
        firstRecipientName,
        stringResource(R.string.text_and),
        secondRecipientName,
        stringResource(R.string.text_exclamation_point)
    )
}

@Composable
private fun getWeddingSignature(senderName: String) : String {
    return String.format("%s %s",
        stringResource(R.string.text_signature),
        senderName)
}