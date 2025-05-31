package com.example.greetingcard.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WeddingCardViewModel : ViewModel() {
    private var _senderName by mutableStateOf("")
    private var _firstRecipientName by mutableStateOf("")
    private var _secondRecipientName by mutableStateOf("")
    private var _isSenderValid by mutableStateOf(true)
    private var _isFirstRecipientValid by mutableStateOf(true)
    private var _isSecondRecipientValid by mutableStateOf(true)

    fun setSender(name : String) {
        _senderName = name
        validateSender()
    }

    fun setFirstRecipient(name : String) {
        _firstRecipientName = name
        validateFirstRecipient()
    }

    fun setSecondRecipient(name : String) {
        _secondRecipientName = name
        validateSecondRecipient()
    }

    fun getSender() : String {
        return _senderName
    }

    fun getFirstRecipient() : String {
        return _firstRecipientName
    }

    fun getSecondRecipient() : String {
        return _secondRecipientName
    }

    fun isSenderValid() : Boolean {
        return _isSenderValid
    }

    fun isFirstRecipientValid() : Boolean {
        return _isFirstRecipientValid
    }

    fun isSecondRecipientValid() : Boolean {
        return _isSecondRecipientValid
    }

    fun isFormValid() : Boolean {
        validateSender()
        validateFirstRecipient()
        validateSecondRecipient()
        return _isSenderValid &&
                _isFirstRecipientValid && _isSecondRecipientValid
    }

    fun clear() {
        _senderName = ""
        _firstRecipientName = ""
        _secondRecipientName = ""
        _isFirstRecipientValid = true
        _isSecondRecipientValid = true
        _isSenderValid = true
    }

    private fun validateSender() {
        _isSenderValid = _senderName.isNotEmpty()
    }

    private fun validateFirstRecipient() {
        _isFirstRecipientValid = _firstRecipientName.isNotEmpty()
    }

    private fun validateSecondRecipient() {
        _isSecondRecipientValid = _secondRecipientName.isNotEmpty()
    }
}