package com.victor.dogbreeds.ui.editProfile

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.R
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.ui.base.BaseActivity
import com.victor.dogbreeds.ui.home.HomeActivity
import com.victor.dogbreeds.ui.signIn.SignInActivity
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.text_field.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class EditProfileActivity : BaseActivity(), EditProfileContract.View, IValidators by Validators() {
    override val layoutResource: Int = R.layout.activity_edit_profile

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }

    private val docRef = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)
    private val presenter: EditProfileContract.Presenter by inject { parametersOf(this) }
    private lateinit var auth: FirebaseAuth

    override fun start() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        auth = FirebaseAuth.getInstance()
        presenter.start()

        getUserInfo()
    }

    override fun setEvents() {
        editProfileConfirmButton.setOnClickListener {
            presenter.updateUser(
                editProfileName.textFieldInput.text.toString(),
                editProfileEmail.textFieldInput.text.toString(),
                editProfilePassword.textFieldInput.text.toString(),
                editProfileBirthdate.textFieldInput.text.toString()
            )
        }

        editProfileSignOutButton.setOnClickListener {
            signOut()
        }

        editProfileName.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditFullname(textToValidate)
        })

        editProfileEmail.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditEmail(textToValidate)
        })

        editProfilePassword.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditPassword(textToValidate)
        })

        editProfileBirthdate.textFieldInput.addTextChangedListener(addValidator(this) { textToValidate ->
            presenter.onFinishEditBirthdate(textToValidate)
        })

        editProfileBirthdate.textFieldInput.addTextChangedListener(addDateMask(editProfileBirthdate))
    }

    override fun showFullnameInputError() {
        editProfileName.textFieldBox.error = getString(R.string.validators_messageLength)
    }

    override fun showFullnameNormalInput() {
        editProfileName.textFieldBox.error = ""
    }

    override fun showEmailInputError() {
        editProfileEmail.textFieldBox.error = getString(R.string.validators_emailError)
    }

    override fun showEmailNormalInput() {
        editProfileEmail.textFieldBox.error = ""
    }

    override fun showPasswordInputError() {
        editProfilePassword.textFieldBox.error = getString(R.string.validators_messageLength)
    }

    override fun showPasswordNormalInput() {
        editProfilePassword.textFieldBox.error = ""
    }

    override fun showBirthdateInputError() {
        editProfileBirthdate.textFieldBox.error =
            getString(R.string.validators_messageIncorrectDateFormat)
    }

    override fun showBirthdateNormalInput() {
        editProfileBirthdate.textFieldBox.error = ""
    }

    override fun openHomeActivity() {
        startActivity(HomeActivity.newInstance(this))
    }

    override fun displayCouldNotUpdateAccountToast() {
        Toast.makeText(this, getString(R.string.editProfile_errorUpdatingInfo), Toast.LENGTH_SHORT)
            .show()
    }

    override fun displayUpdatedSuccessfullyToast() {
        Toast.makeText(
            this,
            getString(R.string.editProfile_successUpdatingInfo),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun requestLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.editProfile_dialogTitle))
        builder.setMessage(getString(R.string.editProfile_emailChangesEffective))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            signOut()
        }
        builder.show()
    }

    override fun requestLogoutToChangeUserName() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.editProfile_dialogTitle))
        builder.setMessage(getString(R.string.editProfile_logoutFirst))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            signOut()
        }
        builder.show()
    }

    private fun getUserInfo() {
        auth.currentUser?.let { user ->
            user.email?.let { email ->
                docRef.whereEqualTo(FirestoreRefs.userEmail, email)

                docRef.get()
                    .addOnSuccessListener { query ->
                        if (query.isEmpty) {
                            showErrorToastMessageAndFinish()
                        } else {
                            if (query.documents.size > 1) showErrorToastMessageAndFinish()

                            fillFields(query.documents[0])
                        }
                    }.addOnFailureListener {
                        showErrorToastMessageAndFinish()
                    }
            }
        }
    }

    private fun showErrorToastMessageAndFinish() {
        Toast.makeText(
            this,
            getString(R.string.editProfile_errorRetrievingInfo),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun fillFields(document: DocumentSnapshot) {
        val fullname = document[FirestoreRefs.userFullname].toString()
        val email = document[FirestoreRefs.userEmail].toString()
        val birthdate = document[FirestoreRefs.userBirthdate].toString()

        presenter.registerUserInfo(document.id, fullname, email, birthdate)

        editProfileName.textFieldInput.setText(fullname)
        editProfileEmail.textFieldInput.setText(email)
        editProfileBirthdate.textFieldInput.setText(birthdate)
    }

    private fun signOut() {
        auth.signOut()
        startActivity(SignInActivity.newInstance(this))
    }
}