package com.example.greetingcard.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BirthdayCardViewModel : ViewModel() {
    private var _senderName by mutableStateOf("")
    private var _recipientName by mutableStateOf("")
    private var _isSenderValid by mutableStateOf(true)
    private var _isRecipientValid by mutableStateOf(true)

    fun setSender(name : String) {
        _senderName = name
        validateSender()
    }

    fun setRecipient(name : String) {
        _recipientName = name
        validateRecipient()
    }

    fun getSender() : String {
        return _senderName
    }

    fun getRecipient() : String {
        return _recipientName
    }

    fun isSenderValid() : Boolean {
        return _isSenderValid
    }

    fun isRecipientValid() : Boolean {
        return _isRecipientValid
    }

    fun isFormValid() : Boolean {
        validateSender()
        validateRecipient()
        return _isSenderValid && _isRecipientValid
    }

    fun clear() {
        _senderName = ""
        _recipientName = ""
        _isSenderValid = true
        _isRecipientValid = true
    }

    private fun validateSender() {
        _isSenderValid = _senderName.isNotEmpty()
    }

    private fun validateRecipient() {
        _isRecipientValid = _recipientName.isNotEmpty()
    }
}