package br.com.augusto.mesanews.modules.auth.ui.signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.afterTextChanged
import br.com.augusto.mesanews.app.helper.toast
import br.com.augusto.mesanews.app.ui.activity.ToolbarActivity
import kotlinx.android.synthetic.main.activity_sigup.*
import kotlinx.android.synthetic.main.activity_sigup.loading
import kotlinx.android.synthetic.main.activity_sigup.password
import kotlinx.android.synthetic.main.activity_sigup.username
import org.koin.android.viewmodel.ext.android.viewModel

class SigupActivity: ToolbarActivity() {

    private val signupViewModel: SignupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigup)
        setTitle(R.string.sigup)

        signupViewModel.signupFormState.observe(this@SigupActivity, Observer {
            val loginState = it ?: return@Observer

            signup.isEnabled = loginState.isDataValid

            if (loginState.nameError != null) {
                name.error = getString(loginState.nameError)
            }
            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
            if (loginState.confirmationPasswordError != null) {
                password_confirmation.error = getString(loginState.confirmationPasswordError)
            }
        })

        signupViewModel.signupResult.observe(this, Observer {
            val signupResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (signupResult.error != null) {
                toast(signupResult.error)
            }
            if (signupResult.success != null && signupResult.success) {
                finish()
            }
        })

        name.afterTextChanged {
            dataChanged()
        }

        username.afterTextChanged {
            dataChanged()
        }

        password.afterTextChanged {
            dataChanged()
        }

        password_confirmation.afterTextChanged {
            dataChanged()
        }

        signup.setOnClickListener {
            loading.visibility = View.VISIBLE
            signupViewModel.signup(name.text.toString(), username.text.toString(), password.text.toString())
        }
    }

    private fun dataChanged() {
        signupViewModel.signupDataChanged(
            name.text.toString(),
            username.text.toString(),
            password.text.toString(),
            password_confirmation.text.toString()
        )
    }
}