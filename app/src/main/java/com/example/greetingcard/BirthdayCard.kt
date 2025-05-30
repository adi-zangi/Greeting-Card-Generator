package com.example.greetingcard

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.greetingcard.ui.theme.BirthdayCardTheme

/*
    Displays a birthday card with the sender and recipient from the
    Birthday Form
 */

class BirthdayCard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BirthdayCard(
                        intent = intent
                    )
                }
            }
        }
    }
}

@Composable
fun BirthdayCard(intent: Intent, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.party)
    val senderName = getSenderName(intent)
    val recipientName = getRecipientName(intent)
    val message = getBirthdayMessage(recipientName)
    val from = getBirthdaySignature(senderName)

    Box(modifier) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.75F
        )
        BirthdayText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Composable
fun BirthdayText(message: String, from: String, modifier: Modifier = Modifier) {
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
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Composable
fun getSenderName(i: Intent) : String {
    return i.getStringExtra(stringResource(R.string.card_sender_key)) ?: ""
}

@Composable
fun getRecipientName(i: Intent) : String {
    return i.getStringExtra(stringResource(R.string.card_recipient_key)) ?: ""
}

@Composable
fun getBirthdayMessage(recipientName: String) : String {
    return String.format("%s %s%s",
        stringResource(R.string.happy_birthday_text),
        recipientName,
        stringResource(R.string.exclamation_point))
}

@Composable
fun getBirthdaySignature(senderName: String) : String {
    return String.format("%s %s",
        stringResource(R.string.signature_text),
        senderName)
}