package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.server.testing.*
import io.restassured.builder.RequestSpecBuilder
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
//import io.restassured.module.kotlin.extensions.
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import java.awt.AWTEventMulticaster.add
import kotlin.test.*


class DotaTests {
    private fun getRequestSpec(): RequestSpecification {
        return RequestSpecBuilder().setBaseUri("https://api.opendota.com").build()
    }

    @Test
    fun getMatchByHeroId() {
        val response =
            Given {
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                get("/api/heroes/54/matches")
            } Then {
                statusCode(200)
                // body("message", equalTo("Test"))
//                body("find { matche -> matche.match_id == '7675688775' }.league_name", equalTo("Dota 2 Space League"))
                //body("match_id.any { it == '7675688775' }", true)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)
        if (response.contains("match_id:7675688775, start_time:1712532555")) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun falseTestGetMatchByHeroId() {
        val response =
            Given {
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                //hero name instead of heroId
                get("/api/heroes/heroName/matches")
            } Then {
                statusCode(500)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)
    }

    @Test
    fun postParseRequest() {
        val response =
            Given {
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                post("/api/request/158303")
            } Then {
                statusCode(200)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)

        if (response.contains("[job:[jobId:")) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun falseTestPostParseRequest() {
        val response =
            Given {
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                //No matchId in path
                post("/api/request")
            } Then {
                statusCode(404)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)
    }

    @Test
    fun postRequestPostingData() {
        val response =
            Given {
                body("{\"matchId\":\"158303\"}")
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                post("/api/request/158303")
            } Then {
                statusCode(200)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)

        if (response.contains("[job:[jobId:")) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun falseTestPostRequestPostingData() {
        val response =
            Given {
                body("{\"matchId\":\"158303\"}")
                spec(getRequestSpec())
                queryParam("deleted", false)
            } When {
                //wrong path
                post("/api/request/wrongPath")
            } Then {
                statusCode(400)
            } Extract {
                body().jsonPath().getString("")
            }
        println(response)

        assertEquals("[error:invalid id]",response)

    }

}




//class ApplicationTest {
//    private fun getRequestSpec(): RequestSpecification {
////        return RequestSpecBuilder().setBaseUri("https://api.opendota.com/").build()
//        return RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build()
//    }
//
//    @Test
//    fun getMatchById() {
//        val response =
//            Given {
//                spec(getRequestSpec())
//                queryParam("deleted", false)
//            } When {
////                get("/api/heroes/54/matches")
//                get("/users")
//            } Then {
//                statusCode(200)
//                body("find { user -> user.username == 'Antonette' }.company.name", equalTo("Deckow-Crist"))
//            } Extract {
//                body().jsonPath().getString("")
//            }
//        println(response)
//    }
//}