package com.wing.tree.sid.data.source

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import com.wing.tree.sid.core.constant.ONE
import com.wing.tree.sid.core.constant.THIRTY
import com.wing.tree.sid.core.constant.THREE
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.exception.RankingCriteriaNotMetException
import com.wing.tree.sid.core.extension.ifNull
import com.wing.tree.sid.core.extension.isZero
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.data.model.Ranking
import com.wing.tree.sid.data.model.RankingParameter
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class RankingDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : RankingDataSource {
    private val collectionReference = firebaseFirestore.collection(COLLECTION_PATH)
    private val queryCursorArray = arrayOfNulls<DocumentSnapshot>(PAGE_COUNT)

    private var count = ZERO
    private var last: DocumentSnapshot? = null

    override suspend fun checkCriteria(rankingParameter: RankingParameter): Result<Int> {
        count = ZERO
        last = null

        return suspendCancellableCoroutine { cancellableContinuation ->
            collectionReference
                .orderBy(Field.SIZE, Query.Direction.DESCENDING)
                .orderBy(Field.PLAY_TIME)
                .limit(LOWEST_RANK.long)
                .get(Source.SERVER)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result?.let { querySnapshot ->
                            if (querySnapshot.isEmpty.not()) {
                                count = querySnapshot.count()
                                last = querySnapshot.last()
                            }

                            querySnapshot.forEachIndexed { index, queryDocumentSnapshot ->
                                with(queryDocumentSnapshot.toObject<Ranking>()) {
                                    if (isHigher(rankingParameter)) {
                                        return@let Result.success(index)
                                    }
                                }
                            }

                            if (count < LOWEST_RANK) {
                                Result.success(count)
                            } else {
                                Result.failure(RankingCriteriaNotMetException())
                            }
                        } ?: Result.failure(NullPointerException("task.result:${task.result}"))

                        cancellableContinuation.resume(result)
                    } else {
                        val exception = task.exception.ifNull {
                            NullPointerException("task.exception:${task.exception}")
                        }

                        cancellableContinuation.resume(Result.failure(exception))
                    }
                }
        }
    }

    override suspend fun fetch(page: Int, pageSize: Int): Result<List<Ranking>> =
        suspendCancellableCoroutine { cancellableContinuation ->
            if (page.isZero) {
                collectionReference
                    .orderBy(Field.SIZE, Query.Direction.DESCENDING)
                    .orderBy(Field.PLAY_TIME)
                    .limit(pageSize.long)
                    .get()
                    .addOnCompleteListener { task ->
                        val result: Result<List<Ranking>> = if (task.isSuccessful) {
                            with(task.result) {
                                if (count() >= pageSize) {
                                    queryCursorArray[ONE] = last()
                                }

                                Result.success(mapNotNull(DocumentSnapshot::toObject))
                            }
                        } else {
                            val exception = task.exception.ifNull {
                                NullPointerException("task.exception:${task.exception}")
                            }

                            Result.failure(exception)
                        }

                        cancellableContinuation.resume(result)
                    }
            } else {
                val queryCursor = queryCursorArray[page] ?: run {
                    cancellableContinuation.resume(Result.success(emptyList()))

                    return@suspendCancellableCoroutine
                }

                collectionReference
                    .orderBy(Field.SIZE, Query.Direction.DESCENDING)
                    .orderBy(Field.PLAY_TIME)
                    .startAfter(queryCursor)
                    .limit(pageSize.long)
                    .get()
                    .addOnCompleteListener { task ->
                        val result: Result<List<Ranking>> = if (task.isSuccessful) {
                            with(task.result) {
                                if (count() >= pageSize) {
                                    if (page < PAGE_COUNT.dec()) {
                                        queryCursorArray[page.inc()] = last()
                                    }
                                }

                                Result.success(mapNotNull(DocumentSnapshot::toObject))
                            }
                        } else {
                            val exception = task.exception.ifNull {
                                NullPointerException("task.exception:${task.exception}")
                            }

                            Result.failure(exception)
                        }

                        cancellableContinuation.resume(result)
                    }
            }
    }

    override suspend fun register(ranking: Ranking): Result<Unit> =
        suspendCancellableCoroutine { cancellableContinuation ->
            val documentReference = collectionReference.document()

            firebaseFirestore.runTransaction { transaction ->
                if (count >= LOWEST_RANK) {
                    last?.let { transaction.delete(it.reference) }
                }

                transaction.set(documentReference, ranking)
            }.addOnSuccessListener {
                cancellableContinuation.resume(Result.success(Unit))
            }.addOnFailureListener {
                cancellableContinuation.resume(Result.failure(it))
            }
    }

    private object Field {
        const val PLAY_TIME = "playTime"
        const val SIZE = "size"
    }

    companion object {
        private const val COLLECTION_PATH = "ranking"
        private const val LOWEST_RANK = THIRTY
        private const val PAGE_COUNT = THREE
    }
}
