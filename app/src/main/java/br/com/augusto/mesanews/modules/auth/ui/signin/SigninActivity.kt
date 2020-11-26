package br.com.augusto.mesanews.modules.auth.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.afterTextChanged
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.modules.auth.ui.signup.SigupActivity
import br.com.augusto.mesanews.modules.news.ui.activity.NewsActivity
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.viewmodel.ext.android.viewModel

class SigninActivity : AppCompatActivity() {

    private val signinViewModel: SigninViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signin)

        signinViewModel.signinFormState.observe(this@SigninActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        signinViewModel.signinResult.observe(this@SigninActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                toast(loginResult.error)
            }
            if (loginResult.success != null && loginResult.success) {
                val intent = Intent(applicationContext, NewsActivity::class.java)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        username.afterTextChanged {
            signinViewModel.signinDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                signinViewModel.signinDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signinViewModel.signin(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                signinViewModel.signin(username.text.toString(), password.text.toString())
            }
        }

        btn_realizar_cadastro.setOnClickListener {
            val intent = Intent(applicationContext, SigupActivity::class.java)
            startActivity(intent)
        }
    }
}