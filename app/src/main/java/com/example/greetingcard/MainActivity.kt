package com.example.greetingcard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.ui.theme.BirthdayCardTheme

/*
    Displays a form that allows to fill a sender and recipient for a
    birthday card and generate the birthday card
 */

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayCardTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(stringResource(R.string.app_name))
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        BirthdayForm(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BirthdayForm(modifier: Modifier = Modifier) {
        var senderName by remember { mutableStateOf("") }
        var recipientName by remember { mutableStateOf("") }
        var isSenderValid by remember { mutableStateOf(true) }
        var isRecipientValid by remember { mutableStateOf(true) }

        fun validateSender() {
            isSenderValid = senderName.isNotEmpty()
        }
        fun validateRecipient() {
            isRecipientValid = recipientName.isNotEmpty()
        }

        Column (
            verticalArrangement = Arrangement
                .spacedBy(16.dp, Alignment.CenterVertically),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.birthday_form_title),
                fontSize = 28.sp,
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = recipientName,
                onValueChange = {
                    recipientName = it
                    validateRecipient()
                },
                label = { Text(stringResource(R.string.card_recipient_prompt)) },
                isError = !isRecipientValid,
                supportingText = { if (!isRecipientValid) {
                    Text(
                        stringResource(R.string.error_field_empty)
                    )
                } },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = senderName,
                onValueChange = {
                    senderName = it
                    validateSender()
                },
                label = { Text(stringResource(R.string.card_sender_prompt)) },
                isError = !isSenderValid,
                supportingText = { if (!isSenderValid) {
                    Text(
                        stringResource(R.string.error_field_empty)
                    )
                } },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    validateSender()
                    validateRecipient()
                    if (isSenderValid && isRecipientValid) {
                        showBirthdayCard(senderName, recipientName)
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(alignment = Alignment.End)
            ) {
                Text(
                    text = "Create",
                    fontSize = 20.sp
                )
            }
        }
    }

    private fun showBirthdayCard(senderName: String, recipientName: String) {
        val intent = Intent(this, BirthdayCard::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(getString(R.string.card_sender_key), senderName)
            .putExtra(getString(R.string.card_recipient_key), recipientName)
        startActivity(intent)
    }

    @Preview(showBackground = true)
    @Composable
    fun BirthdayFormPreview() {
        BirthdayCardTheme {
            BirthdayForm()
        }
    }
}