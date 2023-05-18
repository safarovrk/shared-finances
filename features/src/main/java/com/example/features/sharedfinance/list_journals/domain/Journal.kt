package com.example.features.sharedfinance.list_journals.domain

data class Journal(
    val journalName: String,
    val logins: List<Login>
) {
    data class Login(
        val login: String
    )

    fun getLoginsInString(): String {
        var result = ""
        logins.forEachIndexed { index, login ->
            result =
                if (index == 0) {
                    login.login
                } else if (index != logins.size - 1) {
                    "$result, ${login.login}"
                }
                else "$result, ${login.login}."
        }
        return result
    }
}
