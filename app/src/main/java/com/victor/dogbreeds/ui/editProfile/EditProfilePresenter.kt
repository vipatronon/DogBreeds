package com.victor.dogbreeds.ui.editProfile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.victor.dogbreeds.business.FirestoreRefs
import com.victor.dogbreeds.util.Validators
import com.victor.dogbreeds.util.base.IValidators

class EditProfilePresenter(
    private val view: EditProfileContract.View
) : EditProfileContract.Presenter,
    IValidators by Validators() {

    private var validFullname = false
    private var validEmail = false
    private var validPassword = false
    private var validBirthdate = false

    private lateinit var userId: String
    private lateinit var fullname: String
    private lateinit var email: String
    private lateinit var birthdate: String
    private lateinit var auth: FirebaseAuth

    private val users = FirebaseFirestore.getInstance().collection(FirestoreRefs.usersCollection)
    private lateinit var docRef: DocumentReference

    override fun start() {
        auth = FirebaseAuth.getInstance()
    }

    override fun onFinishEditFullname(textToValidate: String) {
        validFullname = validateTextLength(textToValidate, 5)

        if (validFullname) {
            view.showFullnameNormalInput()
        } else {
            view.showFullnameInputError()
        }
    }

    override fun onFinishEditEmail(textToValidate: String) {
        validEmail = validateTextEmail(textToValidate)

        if (validEmail) {
            view.showEmailNormalInput()
        } else {
            view.showEmailInputError()
        }
    }

    override fun onFinishEditPassword(textToValidate: String) {
        validPassword = validateTextLength(textToValidate, 5)

        if (validPassword) {
            view.showPasswordNormalInput()
        } else {
            view.showPasswordInputError()
        }
    }

    override fun onFinishEditBirthdate(textToValidate: String) {
        validBirthdate = validateTextBirthDate(textToValidate)

        if (validBirthdate) {
            view.showBirthdateNormalInput()
        } else {
            view.showBirthdateInputError()
        }
    }

    override fun updateUser(fullName: String, email: String, password: String, birthdate: String) {

        updateFullname(fullName) {
            updatebirthdate(birthdate) {
                updatePassword(password) {
                    if (this.email != email) {
                        docRef.update(FirestoreRefs.userEmail, email)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    auth.currentUser?.updateEmail(email)
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                view.requestLogout()
                                            }
                                        }
                                        ?.addOnFailureListener {
                                            view.requestLogoutToChangeUserName()
                                        }
                                }
                            }

                    } else {
                        view.displayUpdatedSuccessfullyToast()
                        view.openHomeActivity()
                    }
                }
            }
        }
    }

    override fun registerUserInfo(
        userId: String,
        fullname: String,
        email: String,
        birthdate: String
    ) {
        this.userId = userId
        this.fullname = fullname
        this.email = email
        this.birthdate = birthdate
        docRef = users.document(userId)
    }

    private fun updatePassword(password: String, callback: () -> Unit) {
        if (password.isNotEmpty()) {
            auth.currentUser?.updatePassword(password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) callback()
                }
        } else {
            callback()
        }
    }

    private fun updateFullname(fullname: String, callback: () -> Unit) {
        if (this.fullname != fullname) {
            docRef.update(FirestoreRefs.userFullname, fullname)
                .addOnSuccessListener {
                    callback()
                }
        } else {
            callback()
        }
    }

    private fun updatebirthdate(birthdate: String, callback: () -> Unit) {
        if (this.birthdate != birthdate) {
            docRef.update(FirestoreRefs.userBirthdate, birthdate)
                .addOnSuccessListener {
                    callback()
                }
        } else {
            callback()
        }
    }
}