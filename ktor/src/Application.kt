package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// Inspired by
//  - https://github.com/raharrison/kotlin-ktor-exposed-starter
//  - https://github.com/Kotlin/kotlin-fullstack-sample

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(CallLogging)

    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(Comments)
        addLogger(StdOutSqlLogger)

        val id = Comments.insert {
            it[content] = "howdy"
        } get Comments.id
        log.info("Inserted comment with id=$id")
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/comment") {
            call.respond(getComments())
        }

        get("/comment/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "A numeric id must be specified"
            )
            val comment = getComment(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            log.info(comment.toString())
            call.respond(comment)
        }

        post("/comment") {
            val addedComment = addComment(call.receive())
            call.respond(HttpStatusCode.Created, addedComment)
        }
    }
}

object Comments : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val content = varchar("content", 1024).index()
}


data class Comment(
    val id: Int? = null,
    val content: String
)

suspend fun getComments() = withContext(Dispatchers.IO) {
    transaction {
        Comments.selectAll().mapNotNull { toComment(it) }
    }
}

suspend fun getComment(id: Int): Comment? = withContext(Dispatchers.IO) {
    transaction {
        Comments.select { Comments.id eq id }.mapNotNull { toComment(it) }.singleOrNull()
    }
}

suspend fun addComment(comment: Comment): Comment {
    var id: Int? = null
    withContext(Dispatchers.IO) {
        transaction {
            id = Comments.insert { it[content] = comment.content } get Comments.id
        }
    }
    return getComment(id!!)!!
}

fun toComment(row: ResultRow): Comment {
    return Comment(
        id = row[Comments.id],
        content = row[Comments.content]
    )
}
