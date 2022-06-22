package no.nav

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.common.TextFormat
import no.nav.rapid.Assessment
import no.nav.rapid.Config
import no.nav.rapid.Question
import no.nav.rapid.RapidServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

lateinit var logger: Logger

fun main() {
    val config = Config.fromEnv()
    logger = LoggerFactory.getLogger(config.appName)
    //val database = Database(DataSourceBuilder(System.getenv()).getDataSource())
    val app = QuizApplication(config.appName)
    RapidServer(config, ::ktorServer, app).startBlocking()
}

fun ktorServer(appName: String, isReady: () -> Boolean): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {

    /**
     * Konfigurasjon av Webserver (Ktor https://ktor.io/)
     */
    log = logger
    connector {
        port = 8080
    }
    module {
        install(ContentNegotiation) { jackson() }
        install(CallLogging) {
            filter { call ->
                !call.request.path().startsWith("/is")
            }
        }
        val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
        install(MicrometerMetrics) {
            registry = appMicrometerRegistry
        }
        val helloCounter = appMicrometerRegistry.counter("min-quizapp-hello")

        /**
         * Konfigurasjon av endepunkt
         */

        routing {

            get("/hello") {
                call.respondText("Hello")
                helloCounter.increment()
            }

            get("/secure") { call.respondText("Secure endpoint") }


            get("/") {
                call.respondText(
                    "<html><h1>$appName v2</h1><html>",
                    ContentType.Text.Html
                )
            }

            get("/metrics") {
                call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
                    appMicrometerRegistry.scrape(this)
                }
            }

            get("/isalive") {
                call.respondText("OK")
            }
            get("/isready") {
                if (isReady()) call.respondText("OK") else call.respond(HttpStatusCode.ServiceUnavailable)
            }

        }
    }

})

fun Logger.log(question: Question) {
    info("[Question] category: {}, question: {}, id: {}", question.category, question.question, question.id())
}

fun Logger.log(assessment: Assessment) {
    info("[Assessment] category: {}, questionId: {}, status: {}", assessment.category, assessment.questionId, assessment.status)
}



