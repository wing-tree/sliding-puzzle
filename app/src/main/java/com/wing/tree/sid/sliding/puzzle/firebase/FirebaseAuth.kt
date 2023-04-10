package com.wing.tree.sid.sliding.puzzle.firebase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseAuth {
    suspend fun signInAnonymously(): Result<String> = suspendCancellableCoroutine { cancellableContinuation ->
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        currentUser?.let {
            cancellableContinuation.resume(Result.success(it.uid))
        } ?: run {
            firebaseAuth
                .signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser?.uid?.let { uid ->
                            cancellableContinuation.resume(Result.success(uid))
                        } ?: run {
                            val s = "firebaseAuth.currentUser?.uid:${firebaseAuth.currentUser?.uid}"
                            val exception = NullPointerException(s)

                            cancellableContinuation.resume(Result.failure(exception))
                        }
                    } else {
                        val exception = task.exception ?: NullPointerException(
                            "task.exception:${task.exception}"
                        )

                        cancellableContinuation.resume(Result.failure(exception))
                    }
                }
        }
    }
}
