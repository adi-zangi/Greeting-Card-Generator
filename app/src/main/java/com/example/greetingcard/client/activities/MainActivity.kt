package com.example.greetingcard.client.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.R
import com.example.greetingcard.data.view_models.BirthdayCardViewModel
import com.example.greetingcard.data.view_models.WeddingCardViewModel
import com.example.greetingcard.ui.theme.GreetingCardTheme

/*
    Displays a form that allows to select a type of greeting card, fill in
    values for the card, and then generate the greeting card
 */

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(stringResource(R.string.app_name))
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        GreetingCardForm(
                            modifier = Modifier
                                .padding(
                                    horizontal = 32.dp,
                                    vertical = 48.dp
                                )
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun GreetingCardForm(modifier: Modifier = Modifier) {
        val options = listOf(
            stringResource(R.string.option_birthday),
            stringResource(R.string.option_wedding)
        )
        val selectPrompt = stringResource(R.string.option_none_selected)

        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(selectPrompt) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.title_greeting_card_form),
                fontSize = 28.sp,
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            SelectedGreetingCardForm(
                modifier = Modifier.padding(vertical = 16.dp),
                selectedOptionText = selectedOptionText)
        }
    }

    @Composable
    fun SelectedGreetingCardForm(modifier: Modifier = Modifier,
                                 selectedOptionText : String) {
        clearData()
        when(selectedOptionText) {
            stringResource(R.string.option_birthday) -> BirthdayForm(modifier)
            stringResource(R.string.option_wedding) -> WeddingForm(modifier)
        }
    }

    @Composable
    fun BirthdayForm(modifier: Modifier = Modifier) {
        val viewModel: BirthdayCardViewModel by viewModels()

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(16.dp, Alignment.CenterVertically),
            modifier = modifier
        ) {
            OutlinedTextField(
                value = viewModel.getRecipient(),
                onValueChange = {
                    viewModel.setRecipient(it)
                },
                label = { Text(stringResource(R.string.text_card_recipient_field)) },
                isError = !viewModel.isRecipientValid(),
                supportingText = { if (!viewModel.isRecipientValid()) {
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
                value = viewModel.getSender(),
                onValueChange = {
                    viewModel.setSender(it)
                },
                label = { Text(stringResource(R.string.text_card_sender_field)) },
                isError = !viewModel.isSenderValid(),
                supportingText = { if (!viewModel.isSenderValid()) {
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
                    if (viewModel.isFormValid()) {
                        showBirthdayCard(viewModel.getSender().trim(),
                            viewModel.getRecipient().trim())
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(alignment = Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.button_create),
                    fontSize = 20.sp
                )
            }
        }
    }

    @Composable
    fun WeddingForm(modifier: Modifier = Modifier) {
        val viewModel: WeddingCardViewModel by viewModels()

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(16.dp, Alignment.CenterVertically),
            modifier = modifier
        ) {
            Text(
                text = "What are the names of the wedding couple?"
            )
            OutlinedTextField(
                value = viewModel.getFirstRecipient(),
                onValueChange = {
                    viewModel.setFirstRecipient(it)
                },
                isError = !viewModel.isFirstRecipientValid(),
                supportingText = { if (!viewModel.isFirstRecipientValid()) {
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
                value = viewModel.getSecondRecipient(),
                onValueChange = {
                    viewModel.setSecondRecipient(it)
                },
                isError = !viewModel.isSecondRecipientValid(),
                supportingText = { if (!viewModel.isSecondRecipientValid()) {
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
                value = viewModel.getSender(),
                onValueChange = {
                    viewModel.setSender(it)
                },
                label = { Text(stringResource(R.string.text_card_sender_field)) },
                isError = !viewModel.isSenderValid(),
                supportingText = { if (!viewModel.isSenderValid()) {
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
                    viewModel.isFormValid()
                    if (viewModel.isFormValid()) {
                        showWeddingCard(viewModel.getSender().trim(),
                            viewModel.getFirstRecipient().trim(),
                            viewModel.getSecondRecipient().trim())
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(alignment = Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.button_create),
                    fontSize = 20.sp
                )
            }
        }
    }

    private fun showBirthdayCard(senderName: String, recipientName: String) {
        val intent = Intent(this, BirthdayCard::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(getString(R.string.key_birthday_card_sender), senderName)
            .putExtra(getString(R.string.key_birthday_card_recipient), recipientName)
        startActivity(intent)
    }

    private fun showWeddingCard(senderName: String,
                                firstRecipientName: String,
                                secondRecipientName : String) {
        val intent = Intent(this, WeddingCard::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(getString(R.string.key_wedding_card_sender), senderName)
            .putExtra(getString(R.string.key_wedding_card_first_recipient), firstRecipientName)
            .putExtra(getString(R.string.key_wedding_card_second_recipient), secondRecipientName)
        startActivity(intent)
    }

    private fun clearData() {
        val birthdayViewModel: BirthdayCardViewModel by viewModels()
        val weddingViewModel: WeddingCardViewModel by viewModels()
        birthdayViewModel.clear()
        weddingViewModel.clear()
    }
}